package us.hnry.fancy.utils

import junit.framework.Assert.assertEquals
import org.junit.Test

/**
 * @author Henry
 * *         2/5/2016
 */
class UtilityTest {

    @Test
    @Throws(Exception::class)
    fun testFormatDouble() {
        assertEquals("2.30M", Utility.formatDouble(2300000.0))
        assertEquals("107.61", Utility.formatDouble(107.60843))
        assertEquals("N/A", Utility.formatDouble(Utility.DEFAULT_DOUBLE))
        assertEquals("N/A", Utility.formatDouble(null))
    }

    @Test
    @Throws(Exception::class)
    fun testGetStringBeforeBlank() {
        assertEquals("white", Utility.getStringBeforeBlank("white house"))
    }

    @Test
    @Throws(Exception::class)
    fun testSplitCamelCase() {
        assertEquals("A Foo Bar Test", Utility.splitCamelCase("AFooBarTest"))
        assertEquals("AFBT", Utility.splitCamelCase("AFBT"))
        assertEquals("AFOO Bar Test", Utility.splitCamelCase("AFOOBarTest"))
        assertEquals("Price EPS", Utility.splitCamelCase("PriceEPS"))
    }

    @Test
    @Throws(Exception::class)
    fun testRemoveXMLTags() {
        assertEquals("Foo", Utility.removeXMLTagsFromLastTradeWithTime("<b>Foo</b>"))
    }
}