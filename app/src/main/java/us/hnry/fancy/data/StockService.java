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
public class StockService {

    /**
     * Retrofit will turn the HTTP API into a Java interface that
     * contains the Endpoints that the server can handle.
     * Each HTTP Request is an Endpoint.
     *
     * Two endpoints with the same annotations used as a counter-measure
     * to the fact that the server returns two different formats when
     * a single item is queried.
     */
    public interface SAPI {
        @GET("v1/public/yql?")
        Call<Quote> getQuotes(
                @Query("q") String builtQuery,
                @Query("env") String env,
                @Query("format") String format
        );
        @GET("v1/public/yql?")
        Call<Single> getSingleQuote(
                @Query("q") String builtQuery,
                @Query("env") String env,
                @Query("format") String format
        );
    }
}
