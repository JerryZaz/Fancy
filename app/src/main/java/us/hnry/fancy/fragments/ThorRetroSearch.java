package us.hnry.fancy.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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
import retrofit2.GsonConverterFactory;
import retrofit2.Response;
import retrofit2.Retrofit;
import us.hnry.fancy.BuildConfig;
import us.hnry.fancy.R;
import us.hnry.fancy.adapters.SearchRecycler;
import us.hnry.fancy.data.ThorSearchService.THOR;
import us.hnry.fancy.models.Symbol;
import us.hnry.fancy.utils.Utility;

/**
 * Created by Henry on 2/8/2016.
 * Refactoring Thor Search to make use of Retrofit and RecyclerView
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
        mEditTextSearch = (EditText) layout.findViewById(R.id.search_edit_text);
        mButtonSearch = (Button) layout.findViewById(R.id.search_button);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.THOR_BASE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        searchAdapter = new SearchRecycler(mResults, getActivity());
        mRecyclerViewSearch = (RecyclerView) layout.findViewById(R.id.search_recycler_view);
        mRecyclerViewSearch.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerViewSearch.setAdapter(searchAdapter);

        final THOR thor = retrofit.create(THOR.class);
        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                mInputMethodManager.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                String search = Utility.getStringBeforeBlank(mEditTextSearch.getText().toString());
                mEditTextSearch.setText(search);
                if (!search.equals("")) {
                    final ProgressDialog progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setTitle("Collecting Results");
                    progressDialog.setMessage("We're almost there!");
                    progressDialog.setCancelable(false);
                    progressDialog.setIndeterminate(true);
                    progressDialog.show();

                    call = thor.getSymbols(search);
                    call.enqueue(new Callback<ArrayList<Symbol>>() {
                        @Override
                        public void onResponse(Response<ArrayList<Symbol>> response) {
                            try {
                                mResults = response.body();
                                searchAdapter.swapList(mResults);
                            } catch (NullPointerException e) {
                                Log.v("Catch", "Reached.");
                                Toast toast = null;
                                if (response.code() == 401) {
                                    toast = Toast.makeText(getActivity(), "Unauthenticated", Toast.LENGTH_SHORT);
                                } else if (response.code() >= 400) {
                                    toast = Toast.makeText(getActivity(), "Client error "
                                            + response.code() + " " + response.message(), Toast.LENGTH_SHORT);
                                }
                                if (toast != null) {
                                    toast.show();
                                }
                            } finally {
                                if(!response.isSuccess()){
                                    Snackbar.make(v, "Your search returned no results", Snackbar.LENGTH_SHORT).show();
                                }
                                progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Log.e("getSymbols threw ", t.getMessage());
                        }
                    });
                } else {
                    Snackbar.make(v, "Please enter a search term", Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        return layout;
    }

    @Override
    public void onStop() {
        super.onStop();
        if (call != null) {
            call.cancel();
        }
    }
}
