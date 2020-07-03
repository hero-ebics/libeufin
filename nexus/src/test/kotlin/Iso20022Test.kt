package tech.libeufin.nexus
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.Test
import org.w3c.dom.Document
import tech.libeufin.util.XMLUtil
import kotlin.test.assertEquals

fun loadXmlResource(name: String): Document {
    val classLoader = ClassLoader.getSystemClassLoader()
    val res = classLoader.getResource(name)
    if (res == null) {
        throw Exception("resource $name not found");
    }
    return XMLUtil.parseStringIntoDom(res.readText())
}

class Iso20022Test {
    @Test
    fun testTransactionsImport() {
        val camt53 = loadXmlResource("iso20022-samples/camt.053/de.camt.053.001.02.xml")
        val r = parseCamtMessage(camt53)
        assertEquals(r.messageId, "msg-001")
        assertEquals(r.creationDateTime, "2020-07-03T12:44:40+05:30")
        assertEquals(r.messageType, CashManagementResponseType.Statement)
        assertEquals(r.reports.size, 1)
        assertEquals(r.reports[0].entries[0].entryAmount.amount, "100.00")
        assertEquals(r.reports[0].entries[0].entryAmount.currency, "EUR")
        assertEquals(r.reports[0].entries[0].status, EntryStatus.BOOK)
        assertEquals(r.reports[0].entries[0].entryRef, null)
        assertEquals(r.reports[0].entries[0].accountServicerRef, "acctsvcrref-001")
        assertEquals(r.reports[0].entries[0].bankTransactionCode.domain, "PMNT")
        assertEquals(r.reports[0].entries[0].bankTransactionCode.family, "RCDT")
        assertEquals(r.reports[0].entries[0].bankTransactionCode.subfamily, "ESCT")
        assertEquals(r.reports[0].entries[0].bankTransactionCode.proprietaryCode, "166")
        assertEquals(r.reports[0].entries[0].bankTransactionCode.proprietaryIssuer, "DK")
        assertEquals(r.reports[0].entries[0].transactionInfos.size, 1)

        // Make sure that round-tripping of entry CamtBankAccountEntry JSON works
        for (entry in r.reports.flatMap { it.entries }) {
            val txStr = jacksonObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(entry)
            println(txStr)
            val tx2 = jacksonObjectMapper().readValue(txStr, CamtBankAccountEntry::class.java)
            val tx2Str = jacksonObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(tx2)
            assertEquals(jacksonObjectMapper().readTree(txStr), jacksonObjectMapper().readTree(tx2Str))
        }
    }
}
