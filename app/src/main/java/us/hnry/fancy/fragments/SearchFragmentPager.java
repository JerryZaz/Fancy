package us.hnry.fancy.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import us.hnry.fancy.R;
import us.hnry.fancy.adapters.SearchPagerAdapter;
import us.hnry.fancy.utils.Utility;

/**
 * Created by Henry on 2/16/2016.
 *
 */
public class SearchFragmentPager extends Fragment {

    @Bind(R.id.activity_search_pager)
    ViewPager activitySearchPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_search_pager, container, false);
        ButterKnife.bind(this, layout);
        activitySearchPager.setAdapter(new SearchPagerAdapter(getFragmentManager()));
        int searchSelector = getActivity().getIntent().getIntExtra(Utility.SEARCH_INTENT, 0);
        activitySearchPager.setCurrentItem(searchSelector, true);
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
