package us.hnry.fancy.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Henry
 * 10/7/2017
 */
open class BaseRetrofit(baseUrl: String) {
    var builder: Retrofit.Builder = Retrofit.Builder().baseUrl(baseUrl)
            .client(buildLogger())
            .addConverterFactory(buildConverterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

    fun buildConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    private fun buildLogger(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }
}