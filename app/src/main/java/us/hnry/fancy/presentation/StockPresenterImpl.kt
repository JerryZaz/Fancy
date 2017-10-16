package us.hnry.fancy.presentation

import com.google.firebase.crash.FirebaseCrash
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import us.hnry.fancy.domain.StockRepository
import us.hnry.fancy.domain.StockUseCase
import us.hnry.fancy.presentation.view.StockView

/**
 * @author Henry
 * 10/8/2017
 */
class StockPresenterImpl(repository: StockRepository, observeOn: Scheduler, subscribeOn: Scheduler, vararg symbols: String) : StockPresenter {
    private lateinit var mView: StockView

    private val defaultParams by lazy { StockUseCase.Params(*symbols) }
    private val useCase by lazy { StockUseCase(repository, subscribeOn, observeOn) }
    private val disposables by lazy { CompositeDisposable() }
    private val subscription by lazy {
        buildSubscription(defaultParams)
    }

    override fun attachView(view: StockView) {
        mView = view
        disposables.addAll(subscription)
    }

    override fun detachView() {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }
    }

    override fun symbolSetChanged(vararg symbols: String) {
        if (!disposables.isDisposed) {
            disposables.dispose()
        }

        val temporaryParams = StockUseCase.Params(*symbols)
        disposables.addAll(buildSubscription(temporaryParams))
    }

    private fun buildSubscription(params: StockUseCase.Params): Disposable {
        return useCase.execute(params).subscribeBy(
                onNext = {
                    mView.displayStockData(it)
                },
                onError = {
                    FirebaseCrash.report(it)
                    mView.logMessage("onError(${it.localizedMessage})")
                }
        )
    }
}