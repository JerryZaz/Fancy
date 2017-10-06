package us.hnry.fancy.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import us.hnry.fancy.BuildConfig;
import us.hnry.fancy.R;
import us.hnry.fancy.adapters.SearchRecycler;
import us.hnry.fancy.data.ThorSearchService.THOR;
import us.hnry.fancy.data.model.Symbol;
import us.hnry.fancy.utils.Utility;
import us.hnry.fancy.views.DividerItemDecoration;

/**
 * Created by Henry on 2/8/2016.
 * Refactoring Thor Search to make use of Retrofit and RecyclerView,
 * hence the inclusion of "Retro" to distinguish this class from RetroSearch
 * which made requests to the Thor server using ASyncTask.
 * <p>
 * The Thor service is used to find companies based on an input, it returns company names
 * that match the search, along with their registered <b>Symbol</b>. This symbol is then
 * used to do a <b>StockService</b> request to get the data related to this company.
 */
public class ThorRetroSearch extends Fragment {

    private EditText mEditTextSearch;
    private Button mButtonSearch;
    private RecyclerView mRecyclerViewSearch;
    private SearchRecycler searchAdapter;
    private Call<ArrayList<Symbol>> call;
    private ArrayList<Symbol> mResults;
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
        mEditTextSearch = layout.findViewById(R.id.search_edit_text);
        mEditTextSearch.setHint("Company Lookup (e.g. Amazon)");
        mButtonSearch = layout.findViewById(R.id.search_button);

        searchAdapter = new SearchRecycler(mResults);
        mRecyclerViewSearch = layout.findViewById(R.id.search_recycler_view);
        mRecyclerViewSearch.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerViewSearch.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewSearch.addItemDecoration(new DividerItemDecoration(getActivity(), null));
        mRecyclerViewSearch.setAdapter(searchAdapter);

        //Instantiate retrofit with the known attributes, the API URL
        // and the GSonConverter because I know I'll be receiving a JSON back.
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.THOR_BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Use the retrofit object to generate an implementation of the THOR interface
        final THOR thor = retrofit.create(THOR.class);

        mButtonSearch.setOnClickListener(v -> {
            //This code snippet hides the soft keyboard
            //When implemented in a fragment, it must be instantiated in onActivityCreated
            mInputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);

            // Thor doesn't react too well to multi-word requests, so I make use of a Utility
            // method to fetch the first word and get rid of anything else
            // @TODO: Validate against the user entering symbols and numbers
            String search = Utility.getStringBeforeBlank(mEditTextSearch.getText().toString());
            // Replace the text, a sort of warning to the user.
            mEditTextSearch.setText(search);

            if (!search.equals("")) {

                final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setTitle("Collecting SingleResult");
                progressDialog.setMessage("We're almost there!");
                progressDialog.setCancelable(false);
                progressDialog.setIndeterminate(true);
                progressDialog.show();

                //Call to the service to make an HTTP request to the server
                call = thor.getSymbols(search);

                // Execute the request asynchronously with a callback listener to fetch the
                // response or the error message (if any) while talking to the server,
                // creating the request, or processing the response.
                call.enqueue(new Callback<ArrayList<Symbol>>() {
                    /**
                     * From the interface: Invoked for a received HTTP response.
                     *
                     * @param response call .isSuccess to determine if the response indicates
                     *                 success.
                     */
                    @Override
                    public void onResponse(Call<ArrayList<Symbol>> call, Response<ArrayList<Symbol>> response) {
                        if (response != null && response.body() != null) {
                            mResults = response.body();
                            searchAdapter.swapList(mResults);
                        } else if (response != null) {
                            if (response.code() == 401) {
                                Toast.makeText(getActivity(), "Unauthenticated", Toast.LENGTH_SHORT).show();
                            } else if (response.code() >= 400) {
                                Toast.makeText(getActivity(), "Client error "
                                        + response.code() + " " + response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        if (response != null && !response.isSuccessful()) {
                            Snackbar.make(v, "Your search returned no results", Snackbar.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Symbol>> call, Throwable t) {
                        Log.e("getSymbols threw ", "" + t.getMessage());
                    }
                });
            } else {
                Snackbar.make(v, "Please enter a search term", Snackbar.LENGTH_SHORT).show();
            }
        });
        return layout;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (call != null) {
            //always...
            call.cancel();
        }
    }
}
