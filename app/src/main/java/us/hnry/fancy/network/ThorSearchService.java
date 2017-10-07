package us.hnry.fancy.network;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import us.hnry.fancy.network.model.Symbol;

/**
 * Created by Henry on 2/8/2016.
 * This class contains the interface for the Endpoint requests
 * that the Thor server can handle
 */
public class ThorSearchService {

    /**
     * Retrofit will turn Thor HTTP API
     * into a Java interface that contains the Endpoints
     * the Thor server can handle.
     * Each HTTP Request is an Endpoint.
     */
    public interface THOR{
        @GET("{query}")
        Call<ArrayList<Symbol>> getSymbols(
                @Path("query") String query
        );
    }
}
