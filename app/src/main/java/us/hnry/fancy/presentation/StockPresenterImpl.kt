package us.hnry.fancy.presentation

import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import us.hnry.fancy.domain.StockRepository
import us.hnry.fancy.domain.StockUseCase
import us.hnry.fancy.network.model.Symbol
import us.hnry.fancy.presentation.view.StockView

/**
 * @author Henry
 * 10/8/2017
 */
class StockPresenterImpl(repository: StockRepository, observeOn: Scheduler, subscribeOn: Scheduler, vararg symbols: String) : StockPresenter {
    private lateinit var mView: StockView

    private val useCaseParams by lazy { StockUseCase.Params(*symbols) }
    private val useCase by lazy { StockUseCase(repository, subscribeOn, observeOn) }
    private val disposables by lazy { CompositeDisposable() }
    private val subscription by lazy {
        useCase.execute(useCaseParams).subscribeBy(
                onNext = {
                    mView.logMessage("onNext")
                    mView.displayStockData(it)
                },
                onError = {
                    mView.logMessage("onError(${it.localizedMessage})")
                },
                onComplete = { mView.logMessage("onComplete") }
        )
    }

    override fun attachView(view: StockView) {
        mView = view
        mView.logMessage("attachView")
        disposables.addAll(subscription)
    }

    override fun detachView() {
        mView.logMessage("detachView")
        if (!disposables.isDisposed) {
            mView.logMessage("un-subscribing")
            disposables.dispose()
        }
    }

    override fun onSymbolAdded(symbol: Symbol?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onSymbolRemoved(symbol: Symbol?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}