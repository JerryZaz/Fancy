package us.hnry.fancy.presentation.view

import us.hnry.fancy.domain.model.HistoryEntry

/**
 * @author Henry
 * 10/27/2017
 */
interface SearchHistoryView {
    fun loadHistoryItems(items: List<HistoryEntry>)
    fun showErrorMessage(message: String?)
}