package us.hnry.fancy.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import us.hnry.fancy.models.Quote;
import us.hnry.fancy.models.Single;

/**
 * Created by Henry on 2/7/2016.
 * This class contains the interface for the Endpoint requests
 * that the server can handle.
 */

/*
 * Retrofit will turn the HTTP API into a Java interface that
 * contains the Endpoints that the server can handle.
 * Each HTTP Request is an Endpoint.
 * <p/>
 * Two endpoints with the same annotations used as a counter-measure
 * to the fact that the server returns two different formats when
 * a single item is queried.
 */
public interface StockService {
    /**
     * Endpoint to query the server with a built query containing multiple companies
     *
     * @param builtQuery use the QuoteQueryBuilder to build the query
     * @param env        dataTables provider. Constant in the buildConfig
     * @param format     constant value in the BuildConfig
     * @return Retrofit.Response object containing a object of the Quote class
     */
    @GET("v1/public/yql?")
    Call<Quote> getQuotes(
            @Query("q") String builtQuery,
            @Query("env") String env,
            @Query("format") String format
    );

    /**
     * Endpoint to query the server with a built query containing only one company
     *
     * @param builtQuery use the QuoteQueryBuilder to build the query
     * @param env        dataTables provider. Constant in the buildConfig
     * @param format     constant value in the BuildConfig
     * @return Retrofit.Response object containing a object of the Single class
     */
    @GET("v1/public/yql?")
    Call<Single> getSingleQuote(
            @Query("q") String builtQuery,
            @Query("env") String env,
            @Query("format") String format
    );

    /**
     * Class that embodies the instantiation of Retrofit
     */
    class Implementation extends BaseRetrofit {
        /**
         * Get a Retrofit instance to query the server
         *
         * @param baseUrl constant in BuildConfig
         * @return an Retrofit instance of the StockService class
         */
        public static StockService get(String baseUrl) {
            setBaseUrl(baseUrl);
            return getBuilder().build().create(StockService.class);
        }
    }
}
