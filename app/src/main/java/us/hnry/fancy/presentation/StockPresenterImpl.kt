package us.hnry.fancy.presentation

import com.google.firebase.crash.FirebaseCrash
import io.reactivex.Scheduler
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.rxkotlin.subscribeBy
import us.hnry.fancy.domain.StockRepository
import us.hnry.fancy.domain.interactor.StockUseCase
import us.hnry.fancy.network.model.SingleQuote
import us.hnry.fancy.presentation.model.StockDetail
import us.hnry.fancy.presentation.transform.QuoteTransform
import us.hnry.fancy.presentation.view.StockView

/**
 * @author Henry
 * 10/8/2017
 */
class StockPresenterImpl(repository: StockRepository, observeOn: Scheduler, subscribeOn: Scheduler, vararg symbols: String) : StockPresenter, BasePresenterImpl<StockView>() {
    private val defaultParams by lazy { StockUseCase.Params(*symbols) }
    private val useCase by lazy { StockUseCase(repository, subscribeOn, observeOn) }
    private val subscription by lazy { buildSubscription(defaultParams) }

    override fun attachView(view: StockView) {
        super.attachView(view)
        disposables.addAll(subscription)
    }

    override fun symbolSetChanged(vararg symbols: String) {
        // not implemented yet
    }

    private fun buildSubscription(params: StockUseCase.Params): Disposable {
        return useCase.execute(params).subscribeBy(
                onNext = {
                    mView?.displayStockData(QuoteDetailTransform().apply(it))
                },
                onError = {
                    FirebaseCrash.report(it)
                    mView?.logMessage("onError(${it.localizedMessage})")
                }
        )
    }

    private class QuoteDetailTransform : Function<List<SingleQuote>, List<StockDetail>> {
        override fun apply(t: List<SingleQuote>): List<StockDetail> {
            val listOfStockDetail = mutableListOf<StockDetail>()
            t.forEach { listOfStockDetail.add(QuoteTransform().apply(it)) }
            return listOfStockDetail
        }
    }
}