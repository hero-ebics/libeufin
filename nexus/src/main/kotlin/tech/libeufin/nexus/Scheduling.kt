/*
 * This file is part of LibEuFin.
 * Copyright (C) 2020 Taler Systems S.A.
 *
 * LibEuFin is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation; either version 3, or
 * (at your option) any later version.
 *
 * LibEuFin is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Affero General
 * Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public
 * License along with LibEuFin; see the file COPYING.  If not, see
 * <http://www.gnu.org/licenses/>
 */

package tech.libeufin.nexus

import com.cronutils.model.definition.CronDefinitionBuilder
import com.cronutils.model.time.ExecutionTime
import com.cronutils.parser.CronParser
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.client.HttpClient
import kotlinx.coroutines.*
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.time.delay
import org.jetbrains.exposed.sql.transactions.transaction
import tech.libeufin.nexus.bankaccount.fetchBankAccountTransactions
import tech.libeufin.nexus.bankaccount.submitAllPaymentInitiations
import tech.libeufin.nexus.server.FetchSpecJson
import java.lang.IllegalArgumentException
import java.time.Duration
import java.time.Instant
import java.time.ZonedDateTime
import kotlin.coroutines.coroutineContext
import kotlin.system.exitProcess

private data class TaskSchedule(
    val taskId: Long,
    val name: String,
    val type: String,
    val resourceType: String,
    val resourceId: String,
    val params: String
)

private suspend fun runTask(client: HttpClient, sched: TaskSchedule) {
    logger.info("running task $sched")
    try {
        when (sched.resourceType) {
            "bank-account" -> {
                when (sched.type) {
                    /**
                     * Downloads and ingests the payment records from the bank.
                     */
                    "fetch" -> {
                        @Suppress("BlockingMethodInNonBlockingContext")
                        val fetchSpec = jacksonObjectMapper().readValue(sched.params, FetchSpecJson::class.java)
                        val outcome = fetchBankAccountTransactions(client, fetchSpec, sched.resourceId)
                        if (outcome.errors != null && outcome.errors!!.isNotEmpty()) {
                            /**
                             * Communication with the bank had at least one error.  All of
                             * them get logged when this 'outcome.errors' list was defined,
                             * so not logged twice here.  Failing to bring the problem(s) up.
                             */
                            exitProcess(1)
                        }
                    }
                    /**
                     * Submits the payment preparations that are found in the database.
                     */
                    "submit" -> {
                        submitAllPaymentInitiations(client, sched.resourceId)
                    }
                    else -> {
                        logger.error("task type ${sched.type} not supported")
                    }
                }
            }
            else -> logger.error("task on resource ${sched.resourceType} not supported")
        }
    } catch (e: Exception) {
        logger.error("Exception during task $sched", e)
    } catch (so: StackOverflowError) {
        logger.error(so.stackTraceToString())
        exitProcess(1)
    }
}

object NexusCron {
    val parser = run {
        val cronDefinition =
            CronDefinitionBuilder.defineCron()
                .withSeconds().and()
                .withMinutes().and()
                .withHours().and()
                .withDayOfMonth().optional().and()
                .withMonth().optional().and()
                .withDayOfWeek().optional()
                .and().instance()
        CronParser(cronDefinition)
    }
}

// Fails whenever a unmanaged Throwable reaches the root coroutine.
val fallback = CoroutineExceptionHandler { _, err ->
    logger.error(err.stackTraceToString())
    exitProcess(1)
}
suspend fun startOperationScheduler(httpClient: HttpClient) {
    while (true) {
        // First, assign next execution time stamps to all tasks that need them
        transaction {
            NexusScheduledTaskEntity.find {
                NexusScheduledTasksTable.nextScheduledExecutionSec.isNull()
            }.forEach {
                val cron = try {
                    NexusCron.parser.parse(it.taskCronspec)
                } catch (e: IllegalArgumentException) {
                    logger.error("invalid cronspec in schedule ${it.resourceType}/${it.resourceId}/${it.taskName}")
                    return@forEach
                }
                val zonedNow = ZonedDateTime.now()
                val et = ExecutionTime.forCron(cron)
                val next = et.nextExecution(zonedNow)
                logger.info("scheduling task ${it.taskName} at $next (now is $zonedNow)")
                it.nextScheduledExecutionSec = next.get().toEpochSecond()
            }
        }
        val nowSec = Instant.now().epochSecond
        // Second, find tasks that are due
        val dueTasks = transaction {
            NexusScheduledTaskEntity.find {
                NexusScheduledTasksTable.nextScheduledExecutionSec lessEq nowSec
            }.map {
                TaskSchedule(it.id.value, it.taskName, it.taskType, it.resourceType, it.resourceId, it.taskParams)
            }
        } // Execute those due tasks
        dueTasks.forEach {
            runTask(httpClient, it)
            transaction {
                val t = NexusScheduledTaskEntity.findById(it.taskId)
                if (t != null) {
                    // Reset next scheduled execution
                    t.nextScheduledExecutionSec = null
                    t.prevScheduledExecutionSec = nowSec
                }
            }
        }
        // Wait a bit
        delay(Duration.ofSeconds(1))
    }
}