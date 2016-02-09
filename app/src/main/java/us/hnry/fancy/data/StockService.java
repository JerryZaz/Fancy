package us.hnry.fancy.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import us.hnry.fancy.models.Quote;
import us.hnry.fancy.models.Single;

/**
 * Created by Henry on 2/7/2016.
 * Retrofit interface
 */
public class StockService {

    public interface SAPI {
        @GET("v1/public/yql?")
        Call<Quote> getQuotes(
                @Query("q") String builtQuery,
                @Query("env") String env,
                @Query("format") String format
        );
    }

    public interface ONESAPI{
        @GET("v1/public/yql?")
        Call<Single> getQuotes(
                @Query("q") String builtQuery,
                @Query("env") String env,
                @Query("format") String format
        );
    }

}
