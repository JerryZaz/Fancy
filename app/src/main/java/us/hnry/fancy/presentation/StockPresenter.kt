package us.hnry.fancy.presentation

import us.hnry.fancy.presentation.view.StockView

/**
 * @author Henry
 * 10/8/2017
 */
interface StockPresenter {
    fun attachView(view: StockView)
    fun detachView()
    fun symbolSetChanged(vararg symbols: String)
}