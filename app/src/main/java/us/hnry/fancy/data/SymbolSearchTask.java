package us.hnry.fancy.data;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import us.hnry.fancy.MainActivity;

/**
 *
 * Created by Henry on 2/1/2016.
 */
public class SymbolSearchTask extends AsyncTask<String, Void, ArrayList<Symbol>> {

    private final String LOG_TAG = FetchStockTask.class.getSimpleName();
    private MainActivity mActivity;
    private String mSymbolJSONString;
    private ArrayList<Symbol> mResults = new ArrayList<>();

    @Override
    protected ArrayList<Symbol> doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        mSymbolJSONString = null;

        try{
            final String BASE_URL = "http://chstocksearch.herokuapp.com/api";
            final String QUERY = params[0];

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendPath(QUERY)
                    .build();
            URL url = new URL(builtUri.toString());
            //Create the request to the API
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if(inputStream == null){
                Log.v(LOG_TAG, "Empty input stream");
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                //For debugging purposes
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            mSymbolJSONString = buffer.toString();
            Log.v(LOG_TAG, "Fetched data");
            JSONArray array = new JSONArray(mSymbolJSONString);
            if(array.length() > 0){
                for(int i = 0; i < array.length(); i++){
                    JSONObject singleObject = array.getJSONObject(i);
                    Symbol result = new Symbol(
                            singleObject.getString(Symbol.SYMBOL),
                            singleObject.getString(Symbol.TAG)
                    );
                    mResults.add(result);
                }
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return mResults;
    }

    @Override
    protected void onPostExecute(ArrayList<Symbol> symbols) {
        super.onPostExecute(symbols);
    }
}