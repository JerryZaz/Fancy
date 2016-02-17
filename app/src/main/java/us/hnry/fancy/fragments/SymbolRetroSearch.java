package us.hnry.fancy.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import us.hnry.fancy.BuildConfig;
import us.hnry.fancy.DetailActivity;
import us.hnry.fancy.R;
import us.hnry.fancy.data.StockService;
import us.hnry.fancy.models.Quote;
import us.hnry.fancy.models.Single;
import us.hnry.fancy.utils.QuoteQueryBuilder;
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
    private EditText mEditTextSearch;
    private Button mButtonSearch;
    private Intent mLaunchDetail;
    private InputMethodManager mInputMethodManager;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mInputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_search_recycler, container, false);

        mEditTextSearch = (EditText) layout.findViewById(R.id.search_edit_text);
        mEditTextSearch.setHint("Company Symbol (e.g. AMZN)");
        mButtonSearch = (Button) layout.findViewById(R.id.search_button);

        mButtonSearch = (Button) layout.findViewById(R.id.search_button);
        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //This code snippet hides the soft keyboard
                //When implemented in a fragment, it must be instantiated in onActivityCreated
                mInputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);

                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setTitle("Collecting Results");
                progressDialog.setMessage("We're almost there!");
                progressDialog.setCancelable(false);
                progressDialog.setIndeterminate(true);
                progressDialog.show();

                final String userInput = mEditTextSearch.getText().toString();
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

                    // Get a Retrofit instance
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    // Use the retrofit instance to generate an implementation of the
                    // StockAPI interface
                    StockService.SAPI onesapi = retrofit.create(StockService.SAPI.class);

                    // Call to the service to make an HTTP request to the server
                    Call<Single> call = onesapi.getSingleQuote(builtQuery, ENV, FORMAT);

                    // Execute the request asynchronously with a callback listener to fetch the
                    // response or the error message (if any) while talking to the server,
                    // creating the request, or processing the response.
                    call.enqueue(new Callback<Single>() {

                        /**
                         * From the interface: Invoked for a received HTTP response.
                         * @param response call .isSuccess to determine if the response indicates
                         *                 success.
                         */
                        @Override
                        public void onResponse(Response<Single> response) {
                            try {
                                // Dig into the response, which holds an instance of the Single
                                // model class, to fetch the actual Quote.
                                Quote.SingleQuote quote = response.body().query.results.getQuote();

                                mLaunchDetail = new Intent(v.getContext(), DetailActivity.class);
                                mLaunchDetail.putExtra(Utility.QUOTE_INTENT, quote);
                                v.getContext().startActivity(mLaunchDetail);

                            } catch (NullPointerException e) {
                                if (response.body().query.count <= 0) {
                                    Snackbar.make(v, "Your search returned no results", Snackbar.LENGTH_SHORT).show();
                                }
                                if (response.code() == 401) {
                                    Log.e(TAG, "><ServiceReturned: Unauthenticated>");
                                } else if (response.code() >= 400) {
                                    Log.e(TAG, "><ServiceReturned: Client error "
                                            + response.code() + " " + response.message() + ">");
                                }
                            } finally {
                                if (!response.isSuccess()) {
                                    Snackbar.make(v, "Your search returned no results", Snackbar.LENGTH_SHORT).show();
                                }
                                progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.e(TAG, "><ServiceReturned: >" + t.getMessage());
                            progressDialog.dismiss();
                        }
                    });
                } else {
                    Snackbar.make(v, "Please enter a search term", Snackbar.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
        return layout;
    }
}
