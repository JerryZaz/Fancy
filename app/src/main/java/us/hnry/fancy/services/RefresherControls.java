package us.hnry.fancy.services;

import java.util.ArrayList;

import us.hnry.fancy.models.Quote.SingleQuote;

/**
 * Created by Henry on 2/17/2016.
 */
public interface RefresherControls{
    void start();
    void stop();

    void start(UpdateListener listener);
    void registerListener(UpdateListener listener);
    void unregisterListener(UpdateListener listener);
    void warnListeners(ArrayList<SingleQuote> newData);

    interface UpdateListener{
        void onUpdate(ArrayList<SingleQuote> newData);
    }
}