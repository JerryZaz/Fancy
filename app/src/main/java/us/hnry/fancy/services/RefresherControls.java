package us.hnry.fancy.services;

import java.util.ArrayList;

import us.hnry.fancy.models.Quote;

/**
 * Created by Henry on 2/17/2016.
 */
public interface RefresherControls{
    void start();
    void stop();

    void start(UpdateListener listener);

    interface UpdateListener{
        void onUpdate(ArrayList<Quote.SingleQuote> lastTime);
    }
}