package us.hnry.fancy.presentation

import us.hnry.fancy.presentation.view.StockDetailView
import us.hnry.fancy.presentation.wrapper.BasePresenter

/**
 * @author Henry
 * 10/29/2017
 */
interface StockDetailPresenter : BasePresenter<StockDetailView> {
    fun loadSymbolData(symbol: String)
    fun trackButtonTapped()
    fun shareButtonTapped()
}