package us.hnry.fancy.adapters;

import android.app.Fragment;

import us.hnry.fancy.fragments.SymbolRetroSearch;
import us.hnry.fancy.fragments.ThorRetroSearch;

/**
 * Created by Henry on 2/16/2016.
 */
public class SearchPagerAdapter extends AndroidPagerAdapter {


    public SearchPagerAdapter(android.app.FragmentManager fm) {
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
        String title = "";
        switch (position){
            case 0:
                title = "Thor Search";
                break;
            case 1:
                title = "Symbol Search";
                break;
        }
        return title;
    }

    @Override
    public int getCount() {
        return 2;
    }
}