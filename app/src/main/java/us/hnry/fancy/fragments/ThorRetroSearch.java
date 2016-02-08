package us.hnry.fancy.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import us.hnry.fancy.R;

/**
 * Created by Henry on 2/8/2016.
 * Refactoring Thor Search to make use of Retrofit and RecyclerView
 */
public class ThorRetroSearch extends Fragment {

    private EditText mEditTextSearch;
    private Button mButtonSearch;
    private RecyclerView mRecyclerViewSearch;
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
        mRecyclerViewSearch = (RecyclerView) layout.findViewById(R.id.search_recycler_view);
        mRecyclerViewSearch.setLayoutManager(new LinearLayoutManager(getActivity()));
        return layout;
    }
}
