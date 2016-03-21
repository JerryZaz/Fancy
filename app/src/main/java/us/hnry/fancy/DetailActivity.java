package us.hnry.fancy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import us.hnry.fancy.fragments.DetailQuoteFragment;

public class DetailActivity extends AppCompatActivity {

    static final String FRAGMENT_TAG_DETAIL = "fragment_detail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DetailQuoteFragment detailFragment;
        if (savedInstanceState == null) {
            detailFragment = new DetailQuoteFragment();
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.detail_container,
                            detailFragment,
                            FRAGMENT_TAG_DETAIL)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
