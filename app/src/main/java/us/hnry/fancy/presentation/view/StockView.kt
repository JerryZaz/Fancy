package us.hnry.fancy.presentation.view

import us.hnry.fancy.network.model.SingleQuote
import us.hnry.fancy.network.model.Symbol

/**
 * @author Henry
 * 10/8/2017
 */
interface StockView {
    fun attachPresenter()
    fun detachPresenter()
    fun displayStockData(listOfQuotes: List<SingleQuote>)
    fun addSymbol(symbol: Symbol)
    fun removeSymbol(symbol: Symbol)
    fun logMessage(message: String)
}