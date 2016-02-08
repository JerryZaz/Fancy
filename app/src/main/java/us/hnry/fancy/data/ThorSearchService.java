package us.hnry.fancy.data;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import us.hnry.fancy.models.Symbol;

/**
 * Created by Henry on 2/8/2016.
 */
public class ThorSearchService {
    public interface THOR{
        @GET("{query}")
        Call<ArrayList<Symbol>> getSymbols(
                @Path("query") String query
        );
    }
}
