package us.hnry.fancy.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import us.hnry.fancy.BuildConfig;
import us.hnry.fancy.DetailActivity;
import us.hnry.fancy.R;
import us.hnry.fancy.data.StockService;
import us.hnry.fancy.data.model.Quote;
import us.hnry.fancy.data.model.SingleQuote;
import us.hnry.fancy.utils.QuoteQueryBuilder;
import us.hnry.fancy.utils.SystemUtil;
import us.hnry.fancy.utils.Utility;

/**
 * Created by Henry on 2/8/2016.
 * Replaced Symbol Search, implements Retrofit instead of ASyncTask for the
 * search function where the user inputs the exact symbol of a company and
 * the request is handled, fetching the details of such company and presenting them
 * to the user.
 */
public class SymbolRetroSearch extends Fragment {

    final static String TAG = SymbolRetroSearch.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_search_recycler, container, false);

        final EditText searchEditText = layout.findViewById(R.id.search_edit_text);
        searchEditText.setHint("Company Symbol (e.g. AMZN)");

        Button searchButton = layout.findViewById(R.id.search_button);
        searchButton.setOnClickListener(v -> {
            SystemUtil.hideSoftKeyboard(getActivity(), getView());

            final ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setTitle("Collecting SingleResult");
            progressDialog.setMessage("We're almost there!");
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(true);
            progressDialog.show();

            final String userInput = searchEditText.getText().toString();
            if (!userInput.equals("")) {
                //@TODO: Validate against numbers and symbols
                String query = userInput.toUpperCase();

                //Call the Utility QuoteQueryBuilder class to build
                // a query with the user input
                QuoteQueryBuilder queryBuilder = new QuoteQueryBuilder(query);
                String builtQuery = queryBuilder.build();

                final String BASE_URL = BuildConfig.BASE_API_URL;
                final String ENV = BuildConfig.ENV;
                final String FORMAT = "json";

                // Call to the service to make an HTTP request to the server
                Call<Quote<SingleQuote>> call = StockService.Implementation.get(BASE_URL)
                        .getSingleQuote(builtQuery, ENV, FORMAT);

                // Execute the request asynchronously with a callback listener to fetch the
                // response or the error message (if any) while talking to the server,
                // creating the request, or processing the response.
                call.enqueue(new Callback<Quote<SingleQuote>>() {
                    /**
                     * From the interface: Invoked for a received HTTP response.
                     *
                     * @param response call .isSuccess to determine if the response indicates
                     *                 success.
                     */
                    @Override
                    public void onResponse(Call<Quote<SingleQuote>> call, Response<Quote<SingleQuote>> response) {
                        if (response != null) {
                            Quote<SingleQuote> single = response.body();
                            if (single != null && single.getQuery() != null && single.getQuery().getCount() > 0) {
                                // Dig into the response, which holds an instance of the Single
                                // model class, to fetch the actual Quote.
                                SingleQuote quote = single.getQuery().getResults().getQuote();

                                Intent launchDetail = new Intent(v.getContext(), DetailActivity.class);
                                launchDetail.putExtra(Utility.QUOTE_INTENT, quote);
                                v.getContext().startActivity(launchDetail);
                            } else if (single != null && single.getQuery() != null && single.getQuery().getCount() <= 0) {
                                Snackbar.make(v, "Your search returned no results", Snackbar.LENGTH_SHORT).show();
                            } else {
                                if (response.code() == 401) {
                                    Log.e(TAG, "><ServiceReturned: Unauthenticated>");
                                } else if (response.code() >= 400) {
                                    Log.e(TAG, "><ServiceReturned: Client error "
                                            + response.code() + " " + response.message() + ">");
                                }
                            }
                        }

                        if (response != null && !response.isSuccessful()) {
                            Snackbar.make(v, "Your search returned no results", Snackbar.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<Quote<SingleQuote>> call, Throwable t) {
                        Log.e(TAG, "><ServiceReturned: >" + t.getMessage());
                        progressDialog.dismiss();
                    }
                });
            } else {
                Snackbar.make(v, "Please enter a search term", Snackbar.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
        return layout;
    }
}
