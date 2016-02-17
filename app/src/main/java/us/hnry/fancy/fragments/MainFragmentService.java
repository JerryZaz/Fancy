package us.hnry.fancy.fragments;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import us.hnry.fancy.R;
import us.hnry.fancy.adapters.RetroQuoteRecycler;
import us.hnry.fancy.models.Quote;
import us.hnry.fancy.services.Refresh;
import us.hnry.fancy.utils.Utility;

/**
 * Created by Henry on 2/17/2016.
 */
public class MainFragmentService extends Fragment {

    private static final String LOG_TAG = MainFragmentService.class.getSimpleName();
    public static MainFragmentService runningInstance;
    private static MainFragmentService instance;
    @Bind(R.id.fragment_main_recycler_view)
    RecyclerView mRecyclerView;
    private ArrayList<Quote.SingleQuote> mQuotes;
    private RetroQuoteRecycler mAdapter;
    private Refresh mRefreshService;
    private UpdateReceiver receiver;
    private boolean connected;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Refresh.LocalBinder binder = (Refresh.LocalBinder) service;
            mRefreshService = binder.getService();
            connected = true;
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

    public void bind() {
        if(connected) return;
        Intent bindingIntent = new Intent(getActivity(), Refresh.class);
        getActivity().bindService(bindingIntent, mConnection, Context.BIND_AUTO_CREATE);
        getActivity().startService(bindingIntent);
    }

    public void unbind() {
        if (connected) {
            getActivity().stopService(new Intent(getActivity(), Refresh.class));
            getActivity().unbindService(mConnection);
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

        mAdapter = new RetroQuoteRecycler(mQuotes, getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        //receiver = new UpdateReceiver(mQuotes, mAdapter);
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbind();
        ButterKnife.unbind(this);
    }

    public static class UpdateReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(final Context context, final Intent intent) {

            if(MainFragmentService.getInstance() != null) {

                MainFragmentService.getInstance().mQuotes = intent.getParcelableArrayListExtra(Utility.QUOTE_INTENT);
                MainFragmentService.getInstance().mAdapter.swapList(MainFragmentService.getInstance().mQuotes);
                Toast.makeText(context, MainFragmentService.getInstance().mQuotes.get(0).getAsk(), Toast.LENGTH_LONG).show();
            }
        }
    }
}
