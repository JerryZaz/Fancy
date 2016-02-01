package us.hnry.fancy.data;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Henry on 1/31/2016.
 *
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

            //Create the request to the API
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a string
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if(inputStream == null){
                Log.v(LOG_TAG, "Empty input stream");
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            yahooJSONString = buffer.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        return null;
    }
}
