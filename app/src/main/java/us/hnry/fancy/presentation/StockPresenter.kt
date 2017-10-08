package us.hnry.fancy.presentation

import us.hnry.fancy.network.StockPresenter
import us.hnry.fancy.presentation.view.StockView

/**
 * @author Henry
 * 10/8/2017
 */
interface StockPresenter : StockPresenter.PersistentSymbolsChangedListener {
    fun attachView(view: StockView)
    fun detachView()
}