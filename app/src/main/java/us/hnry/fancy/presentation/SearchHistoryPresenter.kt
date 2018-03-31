package us.hnry.fancy.presentation

import us.hnry.fancy.presentation.view.SearchHistoryView
import us.hnry.fancy.presentation.wrapper.BasePresenter
import us.hnry.fancy.presentation.wrapper.SearchHistoryRowItem

/**
 * @author Henry
 * 10/27/2017
 */
interface SearchHistoryPresenter : BasePresenter<SearchHistoryView>, SearchHistoryRowItem.EventHandler