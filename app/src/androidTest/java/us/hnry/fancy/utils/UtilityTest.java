package us.hnry.fancy.utils;

import junit.framework.TestCase;

/**
 * Created by Henry on 2/5/2016.
 *
 */
public class UtilityTest extends TestCase {

    public void testFormatDouble() throws Exception {
        assertEquals("2.30M", Utility.formatDouble(2300000));
        assertEquals("107.61", Utility.formatDouble(107.60843));
        assertEquals("N/A", Utility.formatDouble(Utility.DEFAULT_DOUBLE));
        assertEquals("N/A", Utility.formatDouble(null));
    }

    public void testGetStringBeforeBlank() throws Exception {
        assertEquals("white", Utility.getStringBeforeBlank("white house"));
    }

    public void testSplitCamelCase() throws Exception {
        assertEquals("A Foo Bar Test", Utility.splitCamelCase("AFooBarTest"));
        assertEquals("AFBT", Utility.splitCamelCase("AFBT"));
        assertEquals("AFOO Bar Test", Utility.splitCamelCase("AFOOBarTest"));
        assertEquals("Price EPS", Utility.splitCamelCase("PriceEPS"));
    }
}