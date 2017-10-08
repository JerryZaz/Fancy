package us.hnry.fancy.fragments

import us.hnry.fancy.network.model.Symbol

/**
 * @author Henry
 * 10/8/2017
 *
 * Classes that implement this interface become reactive to changes to the
 * Persistent Symbols set.
 */
interface PersistentSymbolsChangedListener {
    /**
     * React upon a symbol added to the Persistent Symbols Set
     *
     * @param symbol added
     */
    fun onSymbolAdded(symbol: Symbol)

    /**
     * React upon a symbol removed from the Persistent Symbols Set.
     *
     * @param symbol removed
     */
    fun onSymbolRemoved(symbol: Symbol)
}