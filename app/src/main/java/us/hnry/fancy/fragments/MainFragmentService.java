package us.hnry.fancy.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;
import us.hnry.fancy.MainActivity;
import us.hnry.fancy.R;
import us.hnry.fancy.SearchActivity;
import us.hnry.fancy.adapters.QuotesAdapter;
import us.hnry.fancy.network.StockPresenter;
import us.hnry.fancy.network.StockPresenter.PersistentSymbolsChangedListener;
import us.hnry.fancy.network.model.SingleQuote;
import us.hnry.fancy.network.model.Symbol;
import us.hnry.fancy.services.Refresh;
import us.hnry.fancy.ui.MainItemTouchCallback;
import us.hnry.fancy.utils.ObservableObject;
import us.hnry.fancy.utils.Utility;

/**
 * Created by Henry on 2/17/2016.
 * Remastered MainRetroFragment to implement the Library and the Refresh Service, this is where
 * the main screen RecyclerView's content is managed in coordination with the Library, the Adapter
 * and the service.
 */
public class MainFragmentService extends Fragment implements PersistentSymbolsChangedListener, Observer {

    private static final String LOG_TAG = MainFragmentService.class.getSimpleName();

    @BindView(R.id.fragment_main_recycler_view)
    RecyclerView mRecyclerView;
    UpdateReceiver receiver;

    private QuotesAdapter mAdapter;
    private StockPresenter mPresenter;
    private Refresh mRefreshService;
    private Refresh.LocalBinder binder;
    private boolean connected;
    private ProgressDialog mProgressDialog;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (binder != null) binder = null;
            binder = (Refresh.LocalBinder) service;
            if (mRefreshService != null) mRefreshService.stop();
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

    @Override
    public void onResume() {
        Log.v(LOG_TAG, "onResume");
        super.onResume();
        bind();
    }

    @Override
    public void onStop() {
        super.onStop();

        if (receiver != null) {
            getActivity().unregisterReceiver(receiver);
        }

        unbind();
        Log.v(LOG_TAG, "onStop");
    }

    /**
     * Checks if the Refresh Service is bound or binding. If not, it binds the service.
     */
    public void bind() {
        if (connected || MainActivity.sRefresherBinding) return;
        MainActivity.sRefresherBinding = true;
        Log.v(LOG_TAG, "binding...");
        mProgressDialog.setTitle("Service Connected!");
        mProgressDialog.setMessage("We're almost there!");
        Intent bindingIntent = new Intent(getActivity(), Refresh.class);
        getActivity().bindService(bindingIntent, mConnection, Context.BIND_AUTO_CREATE);
        getActivity().startService(bindingIntent);

        if (receiver == null) {
            receiver = new UpdateReceiver();
        }
        getActivity().registerReceiver(receiver, new IntentFilter(Utility.BROADCAST));
    }

    /**
     * Checks if the Refresh Service is bound. If bound, it unbinds the service.
     */
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

        View layout = inflater.inflate(R.layout.fragment_main_recycler, container, false);
        ButterKnife.bind(this, layout);

        mPresenter = new StockPresenter(getActivity(), this);
        ObservableObject.getInstance().addObserver(this);

        FloatingActionButton searchFab = getActivity().findViewById(R.id.search_fab);
        searchFab.setOnClickListener(v -> startActivity(new Intent(getActivity(), SearchActivity.class)
                .putExtra(Utility.SEARCH_INTENT, Utility.THOR_SEARCH)));

        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle("Setting up something fancy!");
        mProgressDialog.setMessage("Connecting to the service...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();

        mAdapter = new QuotesAdapter(getActivity(), this);
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
    public void onSymbolAdded(Symbol symbol) {
        Snackbar.make(mRecyclerView,
                symbol.getSymbol() + " will be visible after the next update",
                Snackbar.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onSymbolRemoved(final Symbol symbol) {
        if (mPresenter.isTracked(symbol)) {
            if (mPresenter.removeSymbol(symbol)) {
                Snackbar.make(mRecyclerView, "Symbol removed from Favorites", Snackbar.LENGTH_LONG)
                        .setAction("Undo", v -> mPresenter.addSymbol(symbol))
                        .show();
            }
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        List<SingleQuote> retrievedData = (List<SingleQuote>) data;
        mAdapter.swapList(retrievedData);
        if (mProgressDialog != null) mProgressDialog.dismiss();
    }

    /**
     * Broadcast Receiver for the Refresh Service, which broadcasts the data fetched
     * from the server
     */
    public static class UpdateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {

            ArrayList<SingleQuote> data = intent.getParcelableArrayListExtra(StockPresenter.QUOTE_INTENT);
            ObservableObject.getInstance().updateValue(data);
            MainActivity.sRefresherBinding = false;
        }
    }
}
