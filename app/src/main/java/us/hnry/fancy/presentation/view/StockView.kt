package us.hnry.fancy.presentation.view

import us.hnry.fancy.network.model.SingleQuote

/**
 * @author Henry
 * 10/8/2017
 */
interface StockView {
    fun attachPresenter()
    fun detachPresenter()
    fun displayStockData(listOfQuotes: List<SingleQuote>)
    fun logMessage(message: String)
}