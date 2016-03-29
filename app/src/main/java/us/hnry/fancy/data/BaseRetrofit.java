package us.hnry.fancy.data;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * Created by Henry on 3/16/2016.
 */
public abstract class BaseRetrofit {

    protected static String BASE_URL;

    protected static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }

    static Retrofit.Builder getBuilder(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(buildLogger())
                .addConverterFactory(GsonConverterFactory.create());
    }

    static OkHttpClient buildLogger(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }
}
