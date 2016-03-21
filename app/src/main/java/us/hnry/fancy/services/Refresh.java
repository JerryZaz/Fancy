package us.hnry.fancy.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import us.hnry.fancy.data.StockPresenter;
import us.hnry.fancy.models.Quote;
import us.hnry.fancy.models.Quote.SingleQuote;
import us.hnry.fancy.utils.Utility;

/**
 * Created by Henry on 2/17/2016.
 * Service to update the data on the main screen automatically
 */
public class Refresh extends Service implements RefresherControls, StockPresenter.OnNewStockDataRetrieved {

    private static final long MIN_UPDATE_TIME = 1000 * 60;
    private static final String LOG_TAG = Refresh.class.getSimpleName();

    private final IBinder mBinder = new LocalBinder();
    private volatile boolean isRunning;

    private ReentrantLock mLock;
    private Handler mHandler;
    private List<UpdateListener> mListeners;

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            refreshMain();
            if (mHandler != null)
                mHandler.postDelayed(this, MIN_UPDATE_TIME);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        mLock = new ReentrantLock();
        mListeners = new ArrayList<>();
        mHandler = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isRunning) stop();
        mHandler.removeCallbacks(mRunnable);
        mHandler = null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        stop();
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        start(new UpdateListener() {
            @Override
            public void onUpdate(ArrayList<Quote.SingleQuote> newData) {
                Intent broadcastIntent = new Intent();
                broadcastIntent.setAction(Utility.BROADCAST);
                broadcastIntent.putExtra(Utility.QUOTE_INTENT, newData);
                sendBroadcast(broadcastIntent);
            }
        });

        if (mHandler == null) {
            mHandler = new Handler();
            mHandler.post(mRunnable);
        }

        return Service.START_STICKY;
    }

    @Override
    public void start() {
        if (isRunning) return;
        isRunning = true;
    }

    @Override
    public void start(UpdateListener listener) {
        start();
        registerListener(listener);
    }

    @Override
    public void registerListener(UpdateListener listener) {
        mLock.lock();
        if (!mListeners.contains(listener))
            mListeners.add(listener);
        mLock.unlock();
    }

    @Override
    public void unregisterListener(UpdateListener listener) {
        mLock.lock();
        if (mListeners.contains(listener))
            mListeners.remove(listener);
        mLock.unlock();
    }

    @Override
    public void warnListeners(ArrayList<Quote.SingleQuote> newData) {
        mLock.lock();
        for (UpdateListener listener : mListeners) {
            listener.onUpdate(newData);
        }
        mLock.unlock();
    }

    @Override
    public void stop() {
        if (isRunning) {
            isRunning = false;
            for (UpdateListener listener : mListeners) {
                unregisterListener(listener);
            }
        }
    }

    /**
     * Method responsible for refreshing the data displayed on the main screen.
     * It picks up the user's stored data to build the query, if there's none then it builds
     * the query upon the default list of symbols, with which a call to the server will be made
     * to fetch the most up-to-date data.
     */

    public void refreshMain() {
        StockPresenter presenter = new StockPresenter(getApplicationContext());
        presenter.fetchStockData(this);
    }

    @Override
    public void newDataAvailable(ArrayList<SingleQuote> quotes) {
        warnListeners(quotes);
    }


    public class LocalBinder extends Binder {
        public Refresh getService() {
            return Refresh.this;
        }
    }
}

