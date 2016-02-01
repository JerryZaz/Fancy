package us.hnry.fancy.data;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Henry on 1/31/2016.
 */
public class FetchStockTask extends AsyncTask<Void, Void, Void> {

    private final String LOG_TAG = FetchStockTask.class.getSimpleName();
    private Context mContext;

    public FetchStockTask(Context context){
        mContext = context;
    }

    protected Void doInBackground(Void... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String yahooJSONString = null;


        try {
            final String BASE_URL = "http://query.yahooapis.com/v1/public/yql?";
            //TODO: Create query builder method to hax the api
            final String QUERY = "select * from yahoo.finance.quotes where symbol in (\"YHOO\",\"AAPL\",\"GOOG\",\"MSFT\")";

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter("q", QUERY)
                    .appendQueryParameter("env", "http://datatables.org/alltables.env")
                    .appendQueryParameter("format", "json")
                    .build();

            URL url = new URL(builtUri.toString());
            Log.v(LOG_TAG, url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
