package us.hnry.fancy.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import us.hnry.fancy.R;
import us.hnry.fancy.adapters.SearchAdapter;
import us.hnry.fancy.network.ThorSearchService;
import us.hnry.fancy.network.model.Symbol;
import us.hnry.fancy.ui.DividerItemDecoration;
import us.hnry.fancy.utils.SystemUtil;
import us.hnry.fancy.utils.Utility;

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
    private SearchAdapter searchAdapter;
    private Call<ArrayList<Symbol>> call;
    private List<Symbol> mResults;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_search_recycler, container, false);

        mEditTextSearch = layout.findViewById(R.id.search_edit_text);
        mEditTextSearch.setHint("Company Lookup (e.g. Amazon)");

        searchAdapter = new SearchAdapter(getActivity());

        RecyclerView recyclerView = layout.findViewById(R.id.search_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), null));
        recyclerView.setAdapter(searchAdapter);

        Button searchButton = layout.findViewById(R.id.search_button);
        searchButton.setOnClickListener(v -> {

            SystemUtil.INSTANCE.hideSoftKeyboard(getActivity(), getView());

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
                call = ThorSearchService.Companion.get().getSymbols(search);

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
