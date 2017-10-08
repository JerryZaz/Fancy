package us.hnry.fancy.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import us.hnry.fancy.BuildConfig
import us.hnry.fancy.network.model.Symbol
import java.util.*

/**
 * Created by Henry on 2/8/2016.
 * This class contains the interface for the Endpoint requests
 * that the Thor server can handle
 */
class ThorSearchService(baseUrl: String) : BaseRetrofit(baseUrl) {
    companion object {
        fun get(): THOR {
            val service: ThorSearchService = ThorSearchService(BuildConfig.THOR_BASE_API_URL)
            return service.builder.build().create(THOR::class.java)
        }
    }

    /**
     * Retrofit will turn Thor HTTP API
     * into a Java interface that contains the Endpoints
     * the Thor server can handle.
     * Each HTTP Request is an Endpoint.
     */
    interface THOR {
        @GET("{query}")
        fun getSymbols(
                @Path("query") query: String
        ): Call<ArrayList<Symbol>>
    }
}
