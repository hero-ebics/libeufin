package tech.libeufin.sandbox

import io.ktor.server.application.*
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import tech.libeufin.sandbox.CashoutOperationsTable.uuid
import tech.libeufin.util.*
import java.math.BigDecimal
import java.math.MathContext
import java.util.*

// CIRCUIT API TYPES
data class CircuitCashoutRequest(
    val subject: String?,
    val amount_debit: String, // As specified by the user via the SPA.
    val amount_credit: String, // What actually to transfer after the rates.
    /**
     * The String type here allows more flexibility with regard to
     * the supported TAN methods.  This way, supported TAN methods
     * can be specified via the configuration or when starting the
     * bank.  OTOH, catching unsupported TAN methods only via the
     * 'enum' type would require to change the source code upon every
     * change in the TAN policy.
     */
    val tan_channel: String?
)

// Configuration response:
data class ConfigResp(
    val name: String = "circuit",
    val version: String = SANDBOX_VERSION,
    val ratios_and_fees: RatioAndFees
)

// After fixing #7527, the values held by this
// type must be read from the configuration.
data class RatioAndFees(
    val buy_at_ratio: Float = 1F,
    val sell_at_ratio: Float = 0.95F,
    val buy_in_fee: Float = 0F,
    val sell_out_fee: Float = 0F
)
val ratiosAndFees = RatioAndFees()

// User registration request
data class CircuitAccountRequest(
    val username: String,
    val password: String,
    val contact_data: CircuitContactData,
    val name: String,
    val cashout_address: String, // payto
    val internal_iban: String? // Shall be "= null" ?
)
// User contact data to send the TAN.
data class CircuitContactData(
    val email: String?,
    val phone: String?
)

data class CircuitAccountReconfiguration(
    val contact_data: CircuitContactData,
    val cashout_address: String
)

data class AccountPasswordChange(
    val new_password: String
)

/**
 * That doesn't belong to the Access API because it
 * contains the cash-out address and the contact data.
 */
data class CircuitAccountInfo(
    val username: String,
    val iban: String,
    val contact_data: CircuitContactData,
    val name: String,
    val cashout_address: String
)

data class CashoutConfirmation(val tan: String)

// Validate phone number
fun checkPhoneNumber(phoneNumber: String): Boolean {
    // From Taler TypeScript
    // /^\+[0-9 ]*$/;
    val regex = "^\\+[1-9][0-9]+$"
    val R = Regex(regex)
    return R.matches(phoneNumber)
}

// Validate e-mail address
fun checkEmailAddress(emailAddress: String): Boolean {
    // From Taler TypeScript:
    // /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    val regex = "^[a-z0-9\\.]+@[a-z0-9\\.]+\\.[a-z]{2,3}$"
    val R = Regex(regex)
    return R.matches(emailAddress)
}

fun throwIfInstitutionalName(resourceName: String) {
    if (resourceName == "bank" || resourceName == "admin") {
        val msg = "Can't operate on institutional resource '$resourceName'"
        logger.info(msg)
        throw forbidden(msg)
    }
}

fun generateCashoutSubject(
    amountCredit: AmountWithCurrency,
    amountDebit: AmountWithCurrency
): String {
    return "Cash-out of ${amountDebit.currency}:${amountDebit.amount.toPlainString()}" +
            " to ${amountCredit.currency}:${amountCredit.amount.toPlainString()}"
}

/**
 * NOTE: future versions take the supported TAN method from
 * the configuration, or options passed when starting the bank.
 */
enum class SupportedTanChannels { SMS, EMAIL }
fun isTanChannelSupported(tanMethod: String): Boolean {
    return listOf(SupportedTanChannels.SMS.name, SupportedTanChannels.EMAIL.name).contains(tanMethod.uppercase())
}

fun circuitApi(circuitRoute: Route) {
    // Abort a cash-out operation.
    circuitRoute.post("/cashouts/{uuid}/abort") {
        val user = call.request.basicAuth()
        val uuid = call.getUriComponent("uuid")
        val maybeOperation = transaction {
            CashoutOperationEntity.find {
                CashoutOperationsTable.uuid eq UUID.fromString(uuid)
            }.firstOrNull()
        }
        if (maybeOperation == null) {
            val msg = "Cash-out operation $uuid not found."
            logger.debug(msg)
            throw notFound(msg)
        }
        if (maybeOperation.state == CashoutOperationState.CONFIRMED) {
            val msg = "Cash-out operation '$uuid' was confirmed already."
            logger.info(msg)
            throw SandboxError(HttpStatusCode.PreconditionFailed, msg)
        }
        if (maybeOperation.state != CashoutOperationState.PENDING) {
            val msg = "Found an unsupported cash-out operation state: ${maybeOperation.state}"
            logger.error(msg)
            throw internalServerError(msg)
        }
        // Operation found and pending: delete from the database.
        transaction { maybeOperation.delete() }
        call.respond(HttpStatusCode.NoContent)
        return@post
    }
    // Confirm a cash-out operation
    circuitRoute.post("/cashouts/{uuid}/confirm") {
        val user = call.request.basicAuth()
        // Exclude admin from this operation.
        if (user == "admin" || user == "bank") {
            val msg = "Institutional user '$user' shouldn't confirm any cash-out."
            logger.warn(msg)
            throw conflict(msg)
        }
        // Get the operation identifier.
        val operationUuid = call.getUriComponent("uuid")
        val op = transaction {
            CashoutOperationEntity.find {
                uuid eq UUID.fromString(operationUuid)
            }.firstOrNull()
        }
        // 404 if the operation is not found.
        if (op == null) {
            val msg = "Cash-out operation $operationUuid not found"
            logger.debug(msg)
            throw notFound(msg)
        }
        // 412 if the operation got already confirmefd.
        if (op.state == CashoutOperationState.CONFIRMED) {
            val msg = "Cash-out operation $operationUuid was already confirmed."
            logger.debug(msg)
            throw SandboxError(HttpStatusCode.PreconditionFailed, msg)
        }
        /**
         * Check the TAN.  Give precedence to the TAN found
         * in the environment, for testing purposes.  If that's
         * not found, then check with the actual TAN found in
         * the database.
         */
        val req = call.receive<CashoutConfirmation>()
        val maybeTanFromEnv = System.getenv("LIBEUFIN_CASHOUT_TEST_TAN")
        val checkTan = maybeTanFromEnv ?: op.tan
        if (req.tan != checkTan) {
            logger.debug("The confirmation of '${op.uuid}' has a wrong TAN '${req.tan}'")
            throw forbidden("wrong TAN")
        }
        /**
         * Correct TAN.  Wire the funds to the admin's bank account.  After
         * this step, the conversion monitor should detect this payment and
         * soon initiate the final transfer towards the user fiat bank account.
         * NOTE: the funds availability got already checked when this operation
         * was created.  On top of that, the 'wireTransfer()' helper does also
         * check for funds availability.  */
        wireTransfer(
            debitAccount = op.account,
            creditAccount = "admin",
            subject = op.subject,
            amount = op.amountDebit
        )
        transaction { op.state = CashoutOperationState.CONFIRMED }
        call.respond(HttpStatusCode.NoContent)
        return@post
    }
    // Retrieve the status of a cash-out operation.
    circuitRoute.get("/cashouts/{uuid}") {
        val user = call.request.basicAuth()
        val operationUuid = call.getUriComponent("uuid")
        // Parse and check the UUID.
        val maybeUuid = try {
            UUID.fromString(operationUuid)
        } catch (e: Exception) {
            val msg = "The cash-out UUID is invalid: $operationUuid"
            logger.debug(e.message)
            logger.debug(msg)
            throw badRequest(msg)
        }
        // Get the operation from the database.
        val maybeOperation = transaction {
            CashoutOperationEntity.find { uuid eq maybeUuid }.firstOrNull()
        }
        if (maybeOperation == null) {
            val msg = "Cash-out operation $operationUuid not found."
            logger.info(msg)
            throw notFound(msg)
        }
        call.respond(object { val status = maybeOperation.state })
        return@get
    }
    // Create a cash-out operation.
    circuitRoute.post("/cashouts") {
        val user = call.request.basicAuth()
        if (user == "admin" || user == "bank") throw forbidden("$user can't cash-out.")
        // No suitable default user, when the authentication is disabled.
        if (user == null) throw SandboxError(
            HttpStatusCode.ServiceUnavailable,
            "This endpoint isn't served when the authentication is disabled."
        )
        val req = call.receive<CircuitCashoutRequest>()

        // validate amounts: well-formed and supported currency.
        val amountDebit = parseAmount(req.amount_debit) // amount before rates.
        val amountCredit = parseAmount(req.amount_credit) // amount after rates, as expected by the client
        val demobank = ensureDemobank(call)
        if (amountDebit.currency != demobank.currency) {
            val msg = "The '${req::amount_debit.name}' field has the wrong currency"
            logger.info(msg)
            throw badRequest(msg)
        }
        if (amountCredit.currency == demobank.currency) {
            val msg = "The '${req::amount_credit.name}' field didn't change the currency."
            logger.info(msg)
            throw badRequest(msg)
        }
        // check if TAN is supported.
        val tanChannel = req.tan_channel?.uppercase() ?: SupportedTanChannels.SMS.name
        if (!isTanChannelSupported(tanChannel)) {
            val msg = "TAN method $tanChannel not supported."
            logger.info(msg)
            throw SandboxError(HttpStatusCode.ServiceUnavailable, msg)
        }
        // check if the user contact data would allow the TAN channel.
        val customer = getCustomer(username = user)
        if ((tanChannel == SupportedTanChannels.EMAIL.name)
        and (customer.email == null)) {
            logger.info("TAN can't be sent via e-mail.  User '$user' didn't share any address.")
            throw conflict("E-mail address not found.  Can't send the TAN")
        }
        if ((tanChannel == SupportedTanChannels.SMS.name)
            and (customer.phone == null)) {
            logger.info("TAN can't be sent via SMS.  User '$user' didn't share any phone number.")
            throw conflict("Phone number not found.  Can't send the TAN")
        }
        // check rates correctness
        val sellRatio = BigDecimal(ratiosAndFees.sell_at_ratio.toString())
        val sellFee = BigDecimal(ratiosAndFees.sell_out_fee.toString())
        val amountCreditCheck = (amountDebit.amount * sellRatio) - sellFee
        val commonRounding = MathContext(2) // ensures both amounts end with ".XY"
        if (amountCreditCheck.round(commonRounding) != amountCredit.amount.round(commonRounding)) {
            val msg = "Rates application are incorrect." +
                    "  The expected amount to credit is: ${amountCreditCheck}," +
                    " but ${amountCredit.amount.toPlainString()} was specified."
            logger.info(msg)
            throw badRequest(msg)
        }
        // check that the balance is sufficient
        val balance = getBalance(user, withPending = true)
        val balanceCheck = balance - amountDebit.amount
        if (balanceCheck < BigDecimal.ZERO && balanceCheck.abs() > BigDecimal(demobank.usersDebtLimit)) {
            val msg = "Cash-out not possible due to insufficient funds.  Balance ${balance.toPlainString()} would reach ${balanceCheck.toPlainString()}"
            logger.info(msg)
            throw SandboxError(HttpStatusCode.PreconditionFailed, msg)
        }
        // generate a subject if that's missing
        val cashoutSubject = req.subject ?: generateCashoutSubject(
            amountCredit = amountCredit,
            amountDebit = amountDebit
        )
        val op = transaction {
            CashoutOperationEntity.new {
                this.amountDebit = req.amount_debit
                this.subject = cashoutSubject
                this.creationTime = getUTCnow().toInstant().epochSecond
                this.tanChannel = tanChannel
                this.account = user
                this.tan = getRandomString(5)
            }
        }
        // Send the TAN.
        when (tanChannel) {
            SupportedTanChannels.EMAIL.name -> {
                // TBD
            }
            SupportedTanChannels.SMS.name -> {
                // TBD
            }
            else -> {
                val msg = "The bank didn't catch a unsupported TAN channel: $tanChannel."
                logger.error(msg)
                throw internalServerError(msg)
            }
        }
        call.respond(HttpStatusCode.Accepted, object {val uuid = op.uuid})
        return@post
    }
    // Get Circuit-relevant account data.
    circuitRoute.get("/accounts/{resourceName}") {
        val username = call.request.basicAuth()
        val resourceName = call.getUriComponent("resourceName")
        throwIfInstitutionalName(resourceName)
        allowOwnerOrAdmin(username, resourceName)
        val customer = getCustomer(resourceName)
        val bankAccount = getBankAccountFromLabel(resourceName)
        /**
         * Throwing when name or cash-out address aren't found ensures
         * that the customer was indeed added via the Circuit API, as opposed
         * to the Access API.
         */
        val potentialError = "$resourceName not managed by the Circuit API."
        call.respond(CircuitAccountInfo(
            username = customer.username,
            name = customer.name ?: throw notFound(potentialError),
            cashout_address = customer.cashout_address ?: throw notFound(potentialError),
            contact_data = CircuitContactData(
                email = customer.email,
                phone = customer.phone
            ),
            iban = bankAccount.iban
        ))
        return@get
    }
    // Get summary of all the accounts.
    circuitRoute.get("/accounts") {
        call.request.basicAuth(onlyAdmin = true)
        val customers = mutableListOf<Any>()
        transaction {
            DemobankCustomerEntity.all().forEach {
                customers.add(object {
                    val username = it.username
                    val name = it.name
                })
            }
        }
        call.respond(object {val customers = customers})
        return@get
    }

    // Change password.
    circuitRoute.patch("/accounts/{customerUsername}/auth") {
        val username = call.request.basicAuth()
        val customerUsername = call.getUriComponent("customerUsername")
        throwIfInstitutionalName(customerUsername)
        allowOwnerOrAdmin(username, customerUsername)
        // Flow here means admin or username have the rights for this operation.
        val req = call.receive<AccountPasswordChange>()
        /**
         * The resource/customer might still not exist, in case admin has requested.
         * On the other hand, when ordinary customers request, their existence is checked
         * along the basic authentication check.
         */
        transaction {
            val customer = getCustomer(customerUsername) // throws 404, if not found.
            customer.passwordHash = CryptoUtil.hashpw(req.new_password)
        }
        call.respond(HttpStatusCode.NoContent)
        return@patch
    }
    // Change account (mostly contact) data.
    circuitRoute.patch("/accounts/{resourceName}") {
        val username = call.request.basicAuth()
        if (username == null) {
            val msg = "Authentication disabled, don't have a default for this request."
            logger.info(msg)
            throw internalServerError(msg)
        }
        val resourceName = call.getUriComponent("resourceName")
        throwIfInstitutionalName(resourceName)
        allowOwnerOrAdmin(username, resourceName)
        // account found and authentication succeeded
        val req = call.receive<CircuitAccountReconfiguration>()
        if ((req.contact_data.email != null) && (!checkEmailAddress(req.contact_data.email))) {
            val msg = "Invalid e-mail address: ${req.contact_data.email}"
            logger.warn(msg)
            throw badRequest(msg)
        }
        if ((req.contact_data.phone != null) && (!checkPhoneNumber(req.contact_data.phone))) {
            val msg = "Invalid phone number: ${req.contact_data.phone}"
            logger.warn(msg)
            throw badRequest(msg)
        }
        try { parsePayto(req.cashout_address) } catch (e: InvalidPaytoError) {
            val msg = "Invalid cash-out address: ${req.cashout_address}"
            logger.warn(msg)
            throw badRequest(msg)
        }
        transaction {
            val user = getCustomer(resourceName)
            user.email = req.contact_data.email
            user.phone = req.contact_data.phone
            user.cashout_address = req.cashout_address
        }
        call.respond(HttpStatusCode.NoContent)
        return@patch
    }
    // Create new account.
    circuitRoute.post("/accounts") {
        call.request.basicAuth(onlyAdmin = true)
        val req = call.receive<CircuitAccountRequest>()
        // Validity and availability check on the input data.
        if (req.contact_data.email != null) {
            if (!checkEmailAddress(req.contact_data.email)) {
                val msg = "Invalid e-mail address: ${req.contact_data.email}.  Won't register"
                logger.warn(msg)
                throw badRequest(msg)
            }
            val maybeEmailConflict = DemobankCustomerEntity.find {
                DemobankCustomersTable.email eq req.contact_data.email
            }.firstOrNull()
            if (maybeEmailConflict != null) {
                // Warning since two individuals claimed one same e-mail address.
                val msg = "Won't register user ${req.username}: e-mail conflict on ${req.contact_data.email}"
                logger.warn(msg)
                throw conflict(msg)
            }
        }
        if (req.contact_data.phone != null) {
            if (!checkEmailAddress(req.contact_data.phone)) {
                val msg = "Invalid phone number: ${req.contact_data.phone}.  Won't register"
                logger.warn(msg)
                throw badRequest(msg)
            }
            val maybePhoneConflict = DemobankCustomerEntity.find {
                DemobankCustomersTable.phone eq req.contact_data.phone
            }.firstOrNull()
            if (maybePhoneConflict != null) {
                // Warning since two individuals claimed one same phone number.
                val msg = "Won't register user ${req.username}: phone conflict on ${req.contact_data.phone}"
                logger.warn(msg)
                throw conflict(msg)
            }
        }
        /**
         * Check that cash-out address parses.  IBAN is not
         * check-summed in this version; the cash-out operation
         * just fails for invalid IBANs and the user has then
         * the chance to update their IBAN.
         */
        try { parsePayto(req.cashout_address) }
        catch (e: InvalidPaytoError) {
            // Warning because the UI could avoid this.
            val invalidPaytoError = "Won't register account ${req.username}: invalid cash-out address: ${req.cashout_address}"
            logger.warn(invalidPaytoError)
            throw badRequest(invalidPaytoError)
        }
        transaction {
            val newAccount = insertNewAccount(
                username = req.username,
                password = req.password,
                name = req.name
            )
            newAccount.customer.phone = req.contact_data.phone
            newAccount.customer.email = req.contact_data.email
            newAccount.customer.cashout_address = req.cashout_address
        }
        call.respond(HttpStatusCode.NoContent)
        return@post
    }
    // Get (conversion rates via) config values.
    circuitRoute.get("/config") {
        call.respond(ConfigResp(ratios_and_fees = ratiosAndFees))
        return@get
    }
    // Only Admin and only when balance is zero.
    circuitRoute.delete("/accounts/{resourceName}") {
        call.request.basicAuth(onlyAdmin = true)
        val resourceName = call.getUriComponent("resourceName")
        throwIfInstitutionalName(resourceName)
        val bankAccount = getBankAccountFromLabel(resourceName)
        val customer = getCustomer(resourceName)
        val balance = getBalance(bankAccount)
        if (balance != BigDecimal.ZERO) {
            val msg = "Account $resourceName doesn't have zero balance.  Won't delete it"
            logger.error(msg)
            throw SandboxError(
                HttpStatusCode.PreconditionFailed,
                "Account balance is not zero."
            )
        }
        transaction {
            bankAccount.delete()
            customer.delete()
        }
        call.respond(HttpStatusCode.NoContent)
        return@delete
    }
}