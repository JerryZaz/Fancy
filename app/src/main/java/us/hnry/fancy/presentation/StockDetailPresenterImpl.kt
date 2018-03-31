package us.hnry.fancy.presentation

import io.reactivex.Scheduler
import io.reactivex.rxkotlin.subscribeBy
import us.hnry.fancy.domain.StockDetailRepository
import us.hnry.fancy.domain.interactor.StockDetailUseCase
import us.hnry.fancy.domain.interactor.UseCaseHistoryAddSymbol
import us.hnry.fancy.domain.interactor.UseCaseTrackedSymbolStatus
import us.hnry.fancy.domain.interactor.UseCaseTrackedSymbolsFlipStatus
import us.hnry.fancy.network.model.SingleQuote
import us.hnry.fancy.presentation.transform.StockDetailRowItemsTransform
import us.hnry.fancy.presentation.view.StockDetailView
import javax.inject.Inject
import javax.inject.Named

class StockDetailPresenterImpl @Inject constructor(repository: StockDetailRepository, @Named("subscribeOn") subscribeOn: Scheduler, @Named("observeOn") observeOn: Scheduler) : StockDetailPresenter, BasePresenterImpl<StockDetailView>() {

    private val stockDetailUseCase by lazy { StockDetailUseCase(repository, subscribeOn, observeOn) }
    private val addSymbolToHistory by lazy { UseCaseHistoryAddSymbol(repository, subscribeOn, observeOn) }
    private val getSymbolStatus by lazy { UseCaseTrackedSymbolStatus(repository, subscribeOn, observeOn) }
    private val flipSymbolStatus by lazy { UseCaseTrackedSymbolsFlipStatus(repository, subscribeOn, observeOn) }

    private var symbol: String? = null
    private var name: String? = null

    override fun loadSymbolData(symbol: String) {
        disposables.add(stockDetailUseCase.execute(StockDetailUseCase.Params(symbol)).subscribeBy(onNext = {
            processQuote(it.query?.results?.quote)
        }))
    }

    private fun processQuote(singleQuote: SingleQuote?) {
        singleQuote?.let {
            val details = StockDetailRowItemsTransform().apply(it)
            mView?.loadStockDetails(details)

            symbol = singleQuote.symbol
            name = singleQuote.name

            name?.let { title -> mView?.updateHeader(title) }

            updateSymbolStatus()
            updateSearchHistory(singleQuote)
        }
    }

    private fun updateSymbolStatus() {
        val symbol = symbol
        symbol?.let {
            disposables.add(getSymbolStatus.execute(symbol).subscribeBy(onSuccess = {
                if (it) {
                    mView?.onSymbolAdded()
                } else {
                    mView?.onSymbolRemoved()
                }
            }))
        }
    }

    private fun updateSearchHistory(quote: SingleQuote?) {
        quote?.let {
            val symbol = quote.symbol
            symbol?.let {
                val name = quote.name
                name?.let { addSymbolToHistory.execute(UseCaseHistoryAddSymbol.Params(symbol, name)) }
            }
        }
    }

    override fun trackButtonTapped() {
        val symbol = symbol
        symbol?.let {
            flipSymbolStatus.execute(symbol).subscribeBy(onSuccess = {
                mView?.showErrorMessage("Status [$it]")

                if (it) {
                    mView?.onSymbolAdded()
                } else {
                    mView?.onSymbolRemoved()
                }
            })
        }
    }

    override fun shareButtonTapped() {
        // not implemented yet
    }

}