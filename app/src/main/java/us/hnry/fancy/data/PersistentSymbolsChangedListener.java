package us.hnry.fancy.data;

import us.hnry.fancy.models.Symbol;

/**
 * Created by Henry on 3/18/2016.
 */
public interface PersistentSymbolsChangedListener {
    void onSymbolAdded(Symbol symbol);
    void onSymbolRemoved(Symbol symbol);
}
