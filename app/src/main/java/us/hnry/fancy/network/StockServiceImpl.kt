package us.hnry.fancy.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import us.hnry.fancy.BuildConfig
import us.hnry.fancy.network.model.Quote
import us.hnry.fancy.network.model.SingleQuote

/**
 * Created by Henry on 2/7/2016.
 * This class contains the interface for the Endpoint requests
 * that the server can handle.
 */
class StockServiceImpl internal constructor(baseUrl: String) : BaseRetrofit(baseUrl) {

    companion object {
        /**
         * Get a Retrofit instance to query the server

         * @return an Retrofit instance of the StockService class
         */
        fun get(): StockService {
            val implementation = StockServiceImpl(BuildConfig.BASE_API_URL)
            return implementation.builder.build().create(StockService::class.java)
        }
    }

    /*
    * Retrofit will turn the HTTP API into a Java interface that
    * contains the Endpoints that the server can handle.
    * Each HTTP Request is an Endpoint.
    * <p/>
    * Two endpoints with the same annotations used as a counter-measure
    * to the fact that the server returns two different formats when
    * a single item is queried.
    */
    interface StockService {
        /**
         * Endpoint to query the server with a built query containing multiple companies
         * @param builtQuery use the QuoteQueryBuilder to build the query
         * @param env        dataTables provider. Constant in the buildConfig
         * @param format     constant value in the BuildConfig
         * @return Retrofit.Response object containing a object of the Quote class
         */
        @GET("v1/public/yql?")
        fun getQuotes(
                @Query("q") builtQuery: String,
                @Query("env") env: String,
                @Query("format") format: String
        ): Call<Quote<List<SingleQuote>>>

        /**
         * Endpoint to query the server with a built query containing only one company
         * @param builtQuery use the QuoteQueryBuilder to build the query
         * @param env        dataTables provider. Constant in the buildConfig
         * @param format     constant value in the BuildConfig
         * @return Retrofit.Response object containing a object of the Single class
         */
        @GET("v1/public/yql?")
        fun getSingleQuote(
                @Query("q") builtQuery: String,
                @Query("env") env: String,
                @Query("format") format: String
        ): Call<Quote<SingleQuote>>
    }
}
