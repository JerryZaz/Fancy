package us.hnry.fancy.utils;

import java.util.Observable;

/**
 * Created by Henry on 3/21/2016.
 */
public class ObservableObject extends Observable {
    private static ObservableObject instance = new ObservableObject();

    private ObservableObject() {
    }

    public static ObservableObject getInstance() {
        return instance;
    }

    public void updateValue(Object data) {
        synchronized (this) {
            setChanged();
            notifyObservers(data);
        }
    }
}