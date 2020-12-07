package tech.libeufin.sandbox

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.or
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import tech.libeufin.util.RawPayment
import tech.libeufin.util.importDateFromMillis
import tech.libeufin.util.logger
import tech.libeufin.util.toDashedDate

fun historyForAccount(iban: String): List<RawPayment> {
    val history = mutableListOf<RawPayment>()
    transaction {
        logger.debug("Querying transactions involving: ${iban}")
        BankAccountTransactionsTable.select {
            BankAccountTransactionsTable.creditorIban eq iban or
                    (BankAccountTransactionsTable.debitorIban eq iban)
            /**
            FIXME: add the following condition too:
            and (BankAccountTransactionsTable.date.between(start.millis, end.millis))
             */
            /**
            FIXME: add the following condition too:
            and (BankAccountTransactionsTable.date.between(start.millis, end.millis))
             */

        }.forEach {
            history.add(
                RawPayment(
                    subject = it[BankAccountTransactionsTable.subject],
                    creditorIban = it[BankAccountTransactionsTable.creditorIban],
                    creditorBic = it[BankAccountTransactionsTable.creditorBic],
                    creditorName = it[BankAccountTransactionsTable.creditorName],
                    debitorIban = it[BankAccountTransactionsTable.debitorIban],
                    debitorBic = it[BankAccountTransactionsTable.debitorBic],
                    debitorName = it[BankAccountTransactionsTable.debitorName],
                    date = importDateFromMillis(it[BankAccountTransactionsTable.date]).toDashedDate(),
                    amount = it[BankAccountTransactionsTable.amount],
                    currency = it[BankAccountTransactionsTable.currency],
                    // The line below produces a value too long (>35 chars),
                    // and it makes the document invalid!
                    // uid = "${it[pmtInfId]}-${it[msgId]}"
                    uid = "${it[BankAccountTransactionsTable.pmtInfId]}",
                    direction = it[BankAccountTransactionsTable.direction]
                )
            )
        }

    }
    return history
}