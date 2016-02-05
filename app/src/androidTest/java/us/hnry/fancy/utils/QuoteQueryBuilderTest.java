package us.hnry.fancy.utils;

import junit.framework.TestCase;

import us.hnry.fancy.BuildConfig;

/**
 * Created by Henry on 2/5/2016.
 *
 */
public class QuoteQueryBuilderTest extends TestCase {

    public void testQuoteQueryBuilderBuild() throws Exception {
        assertEquals(BuildConfig.BASE_QUERY + "(\"APPL\")", new QuoteQueryBuilder("APPL").build());
        assertEquals(BuildConfig.BASE_QUERY + "(\"APPL\")", new QuoteQueryBuilder("appl").build());
    }
}