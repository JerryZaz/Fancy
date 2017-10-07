package us.hnry.fancy.utils;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * @author Henry
 *         10/7/2017
 */
public class QuoteQueryBuilderTest {
    private String baseQuery = "select * from yahoo.finance.quotes where symbol in ";

    @Test
    public void testQuoteQueryBuilder() {
        assertEquals(baseQuery + "(\"APPL\")", new QuoteQueryBuilder("APPL").build());
    }

    @Test
    public void testQuoteQueryBuilderUpperCaseRule() {
        assertEquals(baseQuery + "(\"APPL\")", new QuoteQueryBuilder("appl").build());
    }

    @Test
    public void testQuoteQueryBuilder_sortMultipleSymbols() {
        assertEquals(baseQuery + "(\"APPL\",\"SQ\")", new QuoteQueryBuilder("sq", "appl").build());
    }

    @Test
    public void testQuoteQueryBuilderKt() {
        assertEquals(baseQuery + "(\"APPL\")", new QuoteQueryBuilder("APPL").build());
    }

    @Test
    public void testQuoteQueryBuilder_noArgs() {
        assertEquals(baseQuery + "()", new QuoteQueryBuilder().build());
    }
}