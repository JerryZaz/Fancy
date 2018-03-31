package us.hnry.fancy.presentation

import us.hnry.fancy.presentation.view.StockView
import us.hnry.fancy.presentation.wrapper.BasePresenter

/**
 * @author Henry
 * 10/8/2017
 */
interface StockPresenter : BasePresenter<StockView> {
    fun startStreaming()
    fun symbolSetChanged(vararg symbols: String)
}