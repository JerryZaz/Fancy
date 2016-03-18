package us.hnry.fancy.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import us.hnry.fancy.MainActivity;
import us.hnry.fancy.R;
import us.hnry.fancy.SearchActivity;
import us.hnry.fancy.adapters.RetroQuoteRecycler;
import us.hnry.fancy.data.PersistentSymbolsChangedListener;
import us.hnry.fancy.models.Quote;
import us.hnry.fancy.models.Symbol;
import us.hnry.fancy.services.Refresh;
import us.hnry.fancy.utils.Utility;
import us.hnry.fancy.views.MainItemTouchCallback;

/**
 * Created by Henry on 2/17/2016.
 */
public class MainFragmentService extends Fragment implements PersistentSymbolsChangedListener {

    private static final String LOG_TAG = MainFragmentService.class.getSimpleName();
    private static MainFragmentService runningInstance;

    @Bind(R.id.fragment_main_recycler_view)
    RecyclerView mRecyclerView;

    private FloatingActionButton mSearchFab;

    private ArrayList<Quote.SingleQuote> mQuotes;
    private Set<String> mSetOfStocks;
    private RetroQuoteRecycler mAdapter;
    private Refresh mRefreshService;
    private Refresh.LocalBinder binder;
    private boolean connected;
    private ProgressDialog mProgressDialog;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if(binder != null) binder = null;
            binder = (Refresh.LocalBinder) service;
            if(mRefreshService != null) mRefreshService.stop();
            mRefreshService = binder.getService();
            connected = true;
            mProgressDialog.setTitle("Refreshing your data...");
            mProgressDialog.setMessage("We are almost there!");
            Log.v(LOG_TAG, "Service Connected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.v(LOG_TAG, "Service Crashed");
        }
    };

    public static MainFragmentService getInstance() {
        return runningInstance;
    }

    @Override
    public void onResume() {
        Log.v(LOG_TAG, "onResume");
        super.onResume();
        bind();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v(LOG_TAG, "onPause");

    }

    @Override
    public void onStop() {
        super.onStop();
        unbind();
        Log.v(LOG_TAG, "onStop");
    }

    public void bind() {
        if(connected || MainActivity.sRefresherBinding) return;
        MainActivity.sRefresherBinding = true;
        Log.v(LOG_TAG, "binding...");
        mProgressDialog.setTitle("Service Connected!");
        mProgressDialog.setMessage("We're almost there!");
        Intent bindingIntent = new Intent(getActivity(), Refresh.class);
        getActivity().bindService(bindingIntent, mConnection, Context.BIND_AUTO_CREATE);
        getActivity().startService(bindingIntent);
    }

    public void unbind() {
        Log.v(LOG_TAG, "unbinding");
        if (connected) {
            mRefreshService.stop();
            getActivity().unbindService(mConnection);
            getActivity().stopService(new Intent(getActivity(), Refresh.class));
            connected = false;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        runningInstance = this;
        View layout = inflater.inflate(R.layout.fragment_main_recycler, container, false);
        ButterKnife.bind(this, layout);

        mSearchFab = (FloatingActionButton) getActivity().findViewById(R.id.search_fab);
        mSearchFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SearchActivity.class).putExtra(Utility.SEARCH_INTENT, Utility.THOR_SEARCH));
            }
        });

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle("Setting up something fancy!");
        mProgressDialog.setMessage("Connecting to the service...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();

        mAdapter = new RetroQuoteRecycler(mQuotes, getActivity(), this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        ItemTouchHelper.Callback callback = new MainItemTouchCallback(mAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mRecyclerView);
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onSymbolAdded(Symbol symbol) {
        /* Nothing yet */
    }

    @Override
    public void onSymbolRemoved(Symbol symbol) {
        SharedPreferences preferences = getActivity().getSharedPreferences(Utility.PERSISTENT, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        if(mSetOfStocks == null){
            mSetOfStocks = preferences.getStringSet(Utility.PERSISTENT_SYMBOLS_SET,
                    new HashSet<>(Arrays.asList(Utility.DEFAULT_SYMBOLS)));
        }
        if(mSetOfStocks.contains(symbol.getSymbol())){
            mSetOfStocks.remove(symbol.getSymbol());
            editor.clear();
            editor.putStringSet(Utility.PERSISTENT_SYMBOLS_SET, mSetOfStocks);
            editor.apply();
        }
    }

    public static class UpdateReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(final Context context, final Intent intent) {

            if(MainFragmentService.getInstance().mProgressDialog != null) MainFragmentService.getInstance().mProgressDialog.dismiss();
            if(MainFragmentService.getInstance() != null) {
                MainFragmentService.getInstance().mQuotes = intent.getParcelableArrayListExtra(Utility.QUOTE_INTENT);
                MainFragmentService.getInstance().mAdapter.swapList(MainFragmentService.getInstance().mQuotes);
                Log.v(LOG_TAG, "Package received");
                MainActivity.sRefresherBinding = false;
            }
        }
    }
}
