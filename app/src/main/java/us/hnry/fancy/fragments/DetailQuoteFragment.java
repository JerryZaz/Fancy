package us.hnry.fancy.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.hnry.fancy.R;
import us.hnry.fancy.adapters.QuoteDetailsAdapter;
import us.hnry.fancy.data.StockPresenter;
import us.hnry.fancy.data.model.SingleQuote;
import us.hnry.fancy.data.model.Symbol;
import us.hnry.fancy.utils.Utility;
import us.hnry.fancy.views.DividerItemDecoration;

/**
 * Created by Henry on 2/8/2016.
 * Remastered Detail Fragment that consumes a SingleQuote object to
 * instantiate a RecyclerViewAdapter to feed the RecyclerView.
 * Floating Action Button allows the user to track if not tracking, or un-track if tracking.
 * Second FAB packs up the Quote object in an intent to share through email.
 */
public class DetailQuoteFragment extends Fragment implements StockPresenter.PersistentSymbolsChangedListener {

    private RecyclerView mDetailRecyclerView;
    private FloatingActionButton mTrackedFab;

    private SingleQuote fromIntent;
    private Symbol mFromIntentSymbol;

    private Intent mShareDetail;

    private ProgressDialog mProgressDialog;

    private StockPresenter mPresenter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle("Fetching data");
        mProgressDialog.setMessage("We're almost there!");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_detail, container, false);

        mPresenter = new StockPresenter(getActivity(), this);
        FloatingActionButton shareFab = getActivity().findViewById(R.id.share_fab);
        mTrackedFab = getActivity().findViewById(R.id.fab);

        fromIntent = getActivity().getIntent().getParcelableExtra(Utility.QUOTE_INTENT);
        if (fromIntent != null) {
            getActivity().setTitle(fromIntent.getName());
            mFromIntentSymbol = new Symbol(fromIntent.getName(), fromIntent.getSymbol());
            if(mPresenter.isTracked(mFromIntentSymbol)){
                mTrackedFab.setImageResource(R.drawable.ic_check_circle_white_24dp);
            }
            new Thread() {
                @Override
                public void run() {
                    try {
                        final QuoteDetailsAdapter detailAdapter = consumeParcelableQuoteFromIntent(fromIntent);
                        getActivity().runOnUiThread(() -> {
                            mDetailRecyclerView.setAdapter(detailAdapter);
                            mProgressDialog.dismiss();
                        });
                    } catch (InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }.start();

        } else {
            Toast.makeText(getActivity(),
                    "Selection returned invalid results from the server.",
                    Toast.LENGTH_SHORT).show();
            getActivity().finish();
        }

        shareFab.setOnClickListener(v -> {
            final ProgressDialog shareIntentProgressBar = new ProgressDialog(getActivity());
            shareIntentProgressBar.setMessage("Putting your data in a nice envelope");
            shareIntentProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            shareIntentProgressBar.setCancelable(false);
            shareIntentProgressBar.setIndeterminate(true);
            shareIntentProgressBar.show();

            new Thread() {
                @Override
                public void run() {
                    mShareDetail = new Intent(Intent.ACTION_SEND);
                    mShareDetail.setData(Uri.parse("mailto:"));
                    mShareDetail.putExtra(Intent.EXTRA_SUBJECT, "Market Stalker - Shared Stock Data");
                    try {
                        mShareDetail.putExtra(Intent.EXTRA_TEXT, Utility.consumeParcelableQuote(fromIntent));
                        mShareDetail.setType("message/rfc822");

                        getActivity().runOnUiThread(() -> {
                            shareIntentProgressBar.dismiss();
                            PackageManager packageManager = getActivity().getPackageManager();
                            List<ResolveInfo> appList = packageManager.queryIntentActivities(mShareDetail, PackageManager.MATCH_ALL);
                            if (appList.size() > 0) {
                                startActivity(mShareDetail);
                            }
                        });
                    } catch (InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                        getActivity().runOnUiThread(() -> Snackbar.make(v, "Something went wrong.", Snackbar.LENGTH_LONG).show());
                    }
                }
            }.start();
        });

        mTrackedFab.setOnClickListener(view -> {
            if (fromIntent != null) {
                if (mPresenter.isTracked(mFromIntentSymbol)) {
                    mPresenter.removeSymbol(mFromIntentSymbol);
                    Snackbar.make(view, "Removed from Favorites", Snackbar.LENGTH_SHORT).show();
                } else {
                    mPresenter.addSymbol(mFromIntentSymbol);
                    Snackbar.make(view, "Added to Favorites", Snackbar.LENGTH_SHORT).show();
                }
            } else {
                Snackbar.make(view, "Invalid data from server.", Snackbar.LENGTH_LONG).show();
            }
        });

        mDetailRecyclerView = layout.findViewById(R.id.content_detail_list_view);
        mDetailRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mDetailRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mDetailRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), null));

        return layout;
    }

    /**
     * Takes the parcelable SingleQuote received, fetches its individual fields to
     * get an instance of the Adapter and populate the RecyclerView.
     *
     * @param quote to be consumed
     * @return RecyclerViewAdapter
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private QuoteDetailsAdapter consumeParcelableQuoteFromIntent(SingleQuote quote)
            throws InvocationTargetException, IllegalAccessException {
        ArrayList<String> keys = new ArrayList<>();
        Method[] methods = quote.getClass().getMethods();
        Map<String, String> map = new HashMap<>();

        for (Method m : methods) {
            if (m.getName().startsWith("get")) {
                String name = m.getName().substring(3);
                if (!name.equals("Class")) {
                    String value = String.valueOf(m.invoke(quote));
                    if (!value.equals("null")) {
                        if (!name.equals("LastTradeWithTime")) {
                            map.put(name, value);
                            keys.add(name);
                        } else {
                            value = Utility.removeXMLTagsFromLastTradeWithTime(value);
                            map.put(name, value);
                            keys.add(name);
                        }
                    }
                }
            }
        }
        return new QuoteDetailsAdapter(keys, map);
    }

    @Override
    public void onSymbolAdded(Symbol symbol) {
        mTrackedFab.setImageResource(R.drawable.ic_check_circle_white_24dp);
    }

    @Override
    public void onSymbolRemoved(Symbol symbol) {
        mTrackedFab.setImageResource(R.drawable.ic_favorite_white_24dp);
    }
}
