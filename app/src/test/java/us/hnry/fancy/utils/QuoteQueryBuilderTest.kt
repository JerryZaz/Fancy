package us.hnry.fancy.utils

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Henry
 * *         10/7/2017
 */
class QuoteQueryBuilderTest {
    private val baseQuery = "select * from yahoo.finance.quotes where symbol in "

    @Test
    fun testQuoteQueryBuilder() {
        assertEquals("$baseQuery(\"APPL\")", QuoteQueryBuilder("APPL").build())
    }

    @Test
    fun testQuoteQueryBuilderUpperCaseRule() {
        assertEquals("$baseQuery(\"APPL\")", QuoteQueryBuilder("appl").build())
    }

    @Test
    fun testQuoteQueryBuilder_sortMultipleSymbols() {
        assertEquals("$baseQuery(\"APPL\",\"SQ\")", QuoteQueryBuilder("sq", "appl").build())
    }

    @Test
    fun testQuoteQueryBuilderKt() {
        assertEquals("$baseQuery(\"APPL\")", QuoteQueryBuilder("APPL").build())
    }

    @Test
    fun testQuoteQueryBuilder_noArgs() {
        assertEquals(baseQuery + "()", QuoteQueryBuilder().build())
    }
}