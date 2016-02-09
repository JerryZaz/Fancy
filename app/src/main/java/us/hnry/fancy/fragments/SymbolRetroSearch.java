package us.hnry.fancy.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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
 *
 */
public class SymbolRetroSearch extends Fragment {
    private TextView mTextView;
    private EditText mEditTextSearch;
    private Button mButtonSearch;
    private ArrayList<Quote.SingleQuote> mQuotes;
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
        mTextView = (TextView) layout.findViewById(R.id.search_text_view_warning);
        mTextView.setText("Using Symbol search. To search by company, go back to the main screen and use Thor search.");
        mEditTextSearch = (EditText) layout.findViewById(R.id.search_edit_text);
        mEditTextSearch.setHint("Company Symbol");
        mButtonSearch = (Button) layout.findViewById(R.id.search_button);

        mButtonSearch = (Button) layout.findViewById(R.id.search_button);
        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
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
                    String query = userInput.toUpperCase();
                    QuoteQueryBuilder queryBuilder = new QuoteQueryBuilder(query);
                    String builtQuery = queryBuilder.build();

                    final String BASE_URL = BuildConfig.BASE_API_URL;
                    final String ENV = BuildConfig.ENV;
                    final String FORMAT = "json";

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    StockService.ONESAPI onesapi = retrofit.create(StockService.ONESAPI.class);
                    Call<Single> call = onesapi.getQuotes(builtQuery, ENV, FORMAT);
                    call.enqueue(new Callback<Single>() {
                        @Override
                        public void onResponse(Response<Single> response) {
                            try {
                                Quote.SingleQuote quote = response.body().query.results.getQuote();
                                mLaunchDetail = new Intent(v.getContext(), DetailActivity.class);
                                mLaunchDetail.putExtra(Utility.QUOTE_INTENT, quote);
                                v.getContext().startActivity(mLaunchDetail);
                            } catch (NullPointerException e) {
                                Log.v("Catch", "Reached.");
                                Toast toast = null;
                                if (response.code() == 401) {
                                    toast = Toast.makeText(v.getContext(), "Unauthenticated", Toast.LENGTH_SHORT);
                                } else if (response.code() >= 400) {
                                    toast = Toast.makeText(v.getContext(), "Client error "
                                            + response.code() + " " + response.message(), Toast.LENGTH_SHORT);
                                }
                                if (toast != null) {
                                    toast.show();
                                }
                            } finally {
                                progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.e("getQuotes threw ", t.getMessage());
                            progressDialog.dismiss();
                        }
                    });
                }
            }
        });
        return layout;
    }
}
