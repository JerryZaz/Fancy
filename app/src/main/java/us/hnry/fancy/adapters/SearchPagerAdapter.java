package us.hnry.fancy.adapters;

import android.app.Fragment;
import android.app.FragmentManager;

import us.hnry.fancy.fragments.SymbolRetroSearch;
import us.hnry.fancy.fragments.ThorRetroSearch;

/**
 * @author Henry
 * 2/16/2016
 */
public class SearchPagerAdapter extends AndroidPagerAdapter {

    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] {
            "Thor Search",
            "Symbol Search"
    };

    public SearchPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public android.app.Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new ThorRetroSearch();
                break;
            case 1:
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
        return PAGE_COUNT;
    }
}