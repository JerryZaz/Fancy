package us.hnry.fancy.presentation.view

import us.hnry.fancy.domain.model.HistoryEntry

/**
 * @author Henry
 * 10/27/2017
 */
interface SearchHistoryView : View {
    fun loadHistoryItems(items: List<HistoryEntry>)
    fun showErrorMessage(message: String?)
    fun launchStockDetail(symbol: String)
}