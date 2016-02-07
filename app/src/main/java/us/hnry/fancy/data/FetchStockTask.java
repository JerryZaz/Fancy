package us.hnry.fancy.data;

import android.content.Context;
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

import us.hnry.fancy.BuildConfig;
import us.hnry.fancy.models.Stock;
import us.hnry.fancy.utils.Utility;

/**
 * Created by Henry on 1/31/2016.
 *
 */
public class FetchStockTask extends AsyncTask<String, Void, ArrayList<Stock>> {

    private final String LOG_TAG = FetchStockTask.class.getSimpleName();
    private final Context mContext;

    private String mStockJSONString;
    private int mResultsCount;

    private ArrayList<Stock> quotes = new ArrayList<>();

    public FetchStockTask(Context context) {
        this.mContext = context;
    }

    protected ArrayList<Stock> doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        mStockJSONString = null;


        try {
            final String BASE_URL = BuildConfig.BASE_URL;

            final String QUERY = params[0];

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter("q", QUERY)
                    .appendQueryParameter("env", BuildConfig.ENV)
                    .appendQueryParameter("format", "json")
                    .build();

            URL url = new URL(builtUri.toString());

            //Create the request to the API
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a string
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
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
            mStockJSONString = buffer.toString();
            Log.v(LOG_TAG, "Fetched data");
            run();

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
        return quotes;
    }

    @Override
    protected void onPostExecute(ArrayList<Stock> stocks) {
        super.onPostExecute(stocks);
    }

    private Stock getStockFromJson(JSONObject singleQuote) {

        final String DEFAULT_STRING = "null";
        final double DEFAULT_DOUBLE = Utility.DEFAULT_DOUBLE;
        final long DEFAULT_LONG = Utility.DEFAULT_LONG;

        Stock quote = null;

        try {
            quote = new Stock(
                    singleQuote.getString(Stock.QUOTE_SYMBOL),
                    singleQuote.getString(Stock.QUOTE_ASK).equals(DEFAULT_STRING) ? DEFAULT_DOUBLE : Double.parseDouble(singleQuote.getString(Stock.QUOTE_ASK)),
                    singleQuote.getString(Stock.QUOTE_AVERAGE_DAILY_VOLUME).equals(DEFAULT_STRING) ? DEFAULT_LONG : Long.parseLong(singleQuote.getString(Stock.QUOTE_AVERAGE_DAILY_VOLUME)),
                    singleQuote.getString(Stock.QUOTE_BID).equals(DEFAULT_STRING) ? DEFAULT_DOUBLE : Double.parseDouble(singleQuote.getString(Stock.QUOTE_BID)),
                    singleQuote.getString(Stock.QUOTE_ASK_REALTIME).equals(DEFAULT_STRING) ? DEFAULT_DOUBLE : Double.parseDouble(singleQuote.getString(Stock.QUOTE_ASK_REALTIME)),
                    singleQuote.getString(Stock.QUOTE_BID_REALTIME).equals(DEFAULT_STRING) ? DEFAULT_DOUBLE : Double.parseDouble(singleQuote.getString(Stock.QUOTE_BID_REALTIME)),
                    singleQuote.getString(Stock.QUOTE_BOOK_VALUE).equals(DEFAULT_STRING) ? DEFAULT_DOUBLE : Double.parseDouble(singleQuote.getString(Stock.QUOTE_BOOK_VALUE)),
                    singleQuote.getString(Stock.QUOTE_CHANGE).equals(DEFAULT_STRING) ? DEFAULT_DOUBLE : Double.parseDouble(singleQuote.getString(Stock.QUOTE_CHANGE)),
                    singleQuote.getString(Stock.QUOTE_CURRENCY),
                    singleQuote.getString(Stock.QUOTE_CHANGE_REALTIME).equals(DEFAULT_STRING) ? DEFAULT_DOUBLE : Double.parseDouble(singleQuote.getString(Stock.QUOTE_CHANGE_REALTIME)),
                    singleQuote.getString(Stock.QUOTE_LAST_TRADE_DATE),
                    singleQuote.getString(Stock.QUOTE_EARNINGS_SHARE).equals(DEFAULT_STRING) ? DEFAULT_DOUBLE : Double.parseDouble(singleQuote.getString(Stock.QUOTE_EARNINGS_SHARE)),
                    singleQuote.getString(Stock.QUOTE_EPS_ESTIMATE_CURRENT_YEAR).equals(DEFAULT_STRING) ? DEFAULT_DOUBLE : Double.parseDouble(singleQuote.getString(Stock.QUOTE_EPS_ESTIMATE_CURRENT_YEAR)),
                    singleQuote.getString(Stock.QUOTE_EPS_ESTIMATE_NEXT_YEAR).equals(DEFAULT_STRING) ? DEFAULT_DOUBLE : Double.parseDouble(singleQuote.getString(Stock.QUOTE_EPS_ESTIMATE_NEXT_YEAR)),
                    singleQuote.getString(Stock.QUOTE_EPS_ESTIMATE_NEXT_QUARTER).equals(DEFAULT_STRING) ? DEFAULT_DOUBLE : Double.parseDouble(singleQuote.getString(Stock.QUOTE_EPS_ESTIMATE_NEXT_QUARTER)),
                    singleQuote.getString(Stock.QUOTE_DAYS_LOW).equals(DEFAULT_STRING) ? DEFAULT_DOUBLE : Double.parseDouble(singleQuote.getString(Stock.QUOTE_DAYS_LOW)),
                    singleQuote.getString(Stock.QUOTE_DAYS_HIGH).equals(DEFAULT_STRING) ? DEFAULT_DOUBLE : Double.parseDouble(singleQuote.getString(Stock.QUOTE_DAYS_HIGH)),
                    singleQuote.getString(Stock.QUOTE_YEAR_LOW).equals(DEFAULT_STRING) ? DEFAULT_DOUBLE : Double.parseDouble(singleQuote.getString(Stock.QUOTE_YEAR_LOW)),
                    singleQuote.getString(Stock.QUOTE_YEAR_HIGH).equals(DEFAULT_STRING) ? DEFAULT_DOUBLE : Double.parseDouble(singleQuote.getString(Stock.QUOTE_YEAR_HIGH)),
                    singleQuote.getString(Stock.QUOTE_CHANGE_FROM_YEAR_LOW).equals(DEFAULT_STRING) ? DEFAULT_DOUBLE : Double.parseDouble(singleQuote.getString(Stock.QUOTE_CHANGE_FROM_YEAR_LOW)),
                    singleQuote.getString(Stock.QUOTE_PERCENT_CHANGE_FROM_YEAR_LOW),
                    singleQuote.getString(Stock.QUOTE_PERCENT_CHANGE_FROM_YEAR_HIGH),
                    singleQuote.getString(Stock.QUOTE_LAST_TRADE_PRICE_ONLY).equals(DEFAULT_STRING) ? DEFAULT_DOUBLE : Double.parseDouble(singleQuote.getString(Stock.QUOTE_LAST_TRADE_PRICE_ONLY)),
                    singleQuote.getString(Stock.QUOTE_FIFTY_DAY_MOVING_AVERAGE).equals(DEFAULT_STRING) ? DEFAULT_DOUBLE : Double.parseDouble(singleQuote.getString(Stock.QUOTE_FIFTY_DAY_MOVING_AVERAGE)),
                    singleQuote.getString(Stock.QUOTE_TWO_HUNDRED_DAY_MOVING_AVERAGE).equals(DEFAULT_STRING) ? DEFAULT_DOUBLE : Double.parseDouble(singleQuote.getString(Stock.QUOTE_TWO_HUNDRED_DAY_MOVING_AVERAGE)),
                    singleQuote.getString(Stock.QUOTE_NAME),
                    singleQuote.getString(Stock.QUOTE_OPEN).equals(DEFAULT_STRING) ? DEFAULT_DOUBLE : Double.parseDouble(singleQuote.getString(Stock.QUOTE_OPEN)),
                    singleQuote.getString(Stock.QUOTE_PREVIOUS_CLOSE).equals(DEFAULT_STRING) ? DEFAULT_DOUBLE : Double.parseDouble(singleQuote.getString(Stock.QUOTE_PREVIOUS_CLOSE)),
                    singleQuote.getString(Stock.QUOTE_TICKER_TREND)
            );
        } catch (JSONException | NumberFormatException e) {
            e.printStackTrace();
            Log.w(LOG_TAG, "Exception handled: " + e);
        }
        return quote;
    }

    public void run() {
        try {
            JSONObject jsonObject = new JSONObject(mStockJSONString);
            JSONObject queryObject = jsonObject.getJSONObject("query");
            mResultsCount = queryObject.getInt("count");
            JSONObject resultsObject = queryObject.getJSONObject("results");
            if (mResultsCount < 2) {
                JSONObject quoteObject = resultsObject.getJSONObject("quote");
                Stock quote = getStockFromJson(quoteObject);
                quotes.add(quote);
            } else {
                JSONArray quoteArray = resultsObject.getJSONArray("quote");

                for (int i = 0; i < quoteArray.length(); i++) {
                    JSONObject singleQuote = quoteArray.getJSONObject(i);
                    Stock quote = getStockFromJson(singleQuote);
                    quotes.add(quote);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
