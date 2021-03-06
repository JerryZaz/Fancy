package us.hnry.fancy.presentation.view

import us.hnry.fancy.presentation.model.StockDetail

/**
 * @author Henry
 * 10/8/2017
 */
interface StockView {
    fun attachPresenter()
    fun detachPresenter()
    fun displayStockData(listOfQuotes: List<StockDetail>)
    fun logMessage(message: String)
}