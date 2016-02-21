package us.hnry.fancy.services;

import java.util.ArrayList;

import us.hnry.fancy.models.Quote.SingleQuote;

/**
 * Created by Henry on 2/17/2016.
 * 
 */
public interface RefresherControls{
    /**
     * Start the Refresh service without
     * registering a listener
     */
    void start();

    /**
     * Stop the service and unregister
     * the listeners
     */
    void stop();

    /**
     * Start the Refresh service registering
     * an update listener.
     * @param listener instance
     */
    void start(UpdateListener listener);

    void registerListener(UpdateListener listener);
    void unregisterListener(UpdateListener listener);

    /**
     * Sends the update to every registered
     * listener
     * @param newData update to be distributed
     */
    void warnListeners(ArrayList<SingleQuote> newData);

    interface UpdateListener{
        /**
         * Where the new data from the update
         * is handled.
         * @param newData update received
         */
        void onUpdate(ArrayList<SingleQuote> newData);
    }
}