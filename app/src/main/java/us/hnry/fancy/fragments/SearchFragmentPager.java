package us.hnry.fancy.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import us.hnry.fancy.R;
import us.hnry.fancy.adapters.SearchPagerAdapter;
import us.hnry.fancy.utils.Utility;

/**
 * Created by Henry on 2/16/2016.
 * Handles the display of the two search Fragments using the ViewPager class and displaying them
 * in a Tab Layout.
 */
public class SearchFragmentPager extends Fragment {

    ViewPager activitySearchPager;
    TabLayout mSlidingTabs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_search_pager, container, false);
        activitySearchPager = layout.findViewById(R.id.activity_search_pager);
        activitySearchPager.setAdapter(new SearchPagerAdapter(getFragmentManager()));

        mSlidingTabs = layout.findViewById(R.id.fragment_search_sliding_tabs);
        mSlidingTabs.setupWithViewPager(activitySearchPager);
        mSlidingTabs.setTabMode(TabLayout.MODE_FIXED);
        int searchSelector = getActivity().getIntent().getIntExtra(Utility.SEARCH_INTENT, 0);
        activitySearchPager.setCurrentItem(searchSelector, true);
        return layout;
    }
}
