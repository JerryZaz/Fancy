package us.hnry.fancy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import us.hnry.fancy.fragments.SearchFragmentPager;

public class SearchActivity extends AppCompatActivity {

    private static final String FRAGMENT_TAG_SEARCH = "fragment.search.pager";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SearchFragmentPager searchFragment;
        if(savedInstanceState == null){
            searchFragment = new SearchFragmentPager();
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.thor_container, searchFragment, FRAGMENT_TAG_SEARCH)
                    .commit();
        }
    }
}
