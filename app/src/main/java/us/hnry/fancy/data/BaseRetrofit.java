package us.hnry.fancy.data;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Henry
 * 3/16/2016
 */
abstract class BaseRetrofit {

    private static String BASE_URL;

    static void setBaseUrl(String baseUrl) {
        BASE_URL = baseUrl;
    }

    static Retrofit.Builder getBuilder(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());
    }

    private static OkHttpClient buildLogger() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }
}
