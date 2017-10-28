package us.hnry.fancy.adapters;

import android.app.Fragment;
import android.app.FragmentManager;

import us.hnry.fancy.fragments.SearchHistoryFragment;
import us.hnry.fancy.fragments.SymbolRetroSearch;

/**
 * @author Henry
 *         2/16/2016
 */
public class SearchPagerAdapter extends AndroidPagerAdapter {

    private String[] tabTitles = new String[]{
            "Search History",
            "Symbol Search"
    };

    public SearchPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.app.Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new SearchHistoryFragment();
                break;
            default:
                fragment = new SymbolRetroSearch();
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }
}