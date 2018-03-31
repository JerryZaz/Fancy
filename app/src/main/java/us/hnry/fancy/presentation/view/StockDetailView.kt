package us.hnry.fancy.presentation.view

import us.hnry.fancy.presentation.wrapper.StockDetailRowItem

/**
 * @author Henry
 * 10/29/2017
 */
interface StockDetailView : View {
    fun updateHeader(title: String)
    fun loadStockDetails(details: List<StockDetailRowItem>)
    fun onSymbolAdded()
    fun onSymbolRemoved()
    fun showErrorMessage(string: String)
}