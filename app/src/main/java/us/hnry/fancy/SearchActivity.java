package us.hnry.fancy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import us.hnry.fancy.fragments.SymbolRetroSearch;
import us.hnry.fancy.fragments.ThorRetroSearch;
import us.hnry.fancy.utils.Utility;

public class SearchActivity extends AppCompatActivity {

    static final String FRAGMENT_TAG_THOR = "fragment_search_thor";
    static final String FRAGMENT_TAG_SYMBOL = "fragment_search_symbol";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        int fragmentSelector = getIntent().getIntExtra(Utility.SEARCH_INTENT, 0);
        switch (fragmentSelector) {
            case Utility.THOR_SEARCH:
                setTitle("Thor Search");
                getFragmentManager().beginTransaction().add(R.id.thor_container, new ThorRetroSearch(), FRAGMENT_TAG_THOR).commit();
                break;
            case Utility.SYMBOL_SEARCH:
                setTitle("Symbol Search");
                getFragmentManager().beginTransaction().add(R.id.thor_container, new SymbolRetroSearch(), FRAGMENT_TAG_SYMBOL).commit();
                break;
        }
    }
}
