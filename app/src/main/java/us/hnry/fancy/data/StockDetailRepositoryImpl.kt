package us.hnry.fancy.data

import io.reactivex.Completable
import io.reactivex.Single
import us.hnry.fancy.BuildConfig
import us.hnry.fancy.domain.StockDetailRepository
import us.hnry.fancy.domain.TrackedSymbolsDataSource
import us.hnry.fancy.domain.interactor.StockDetailUseCase
import us.hnry.fancy.domain.interactor.UseCaseHistoryAddSymbol
import us.hnry.fancy.local.dao.SearchHistoryDao
import us.hnry.fancy.network.StockServiceImpl.StockService
import us.hnry.fancy.network.model.Quote
import us.hnry.fancy.network.model.SingleQuote
import javax.inject.Inject

class StockDetailRepositoryImpl @Inject constructor(private val service: StockService, private val dao: SearchHistoryDao, private val symbolsDataSource: TrackedSymbolsDataSource) : StockDetailRepository {
    override fun getStockDetails(params: StockDetailUseCase.Params): Single<Quote<SingleQuote>> {
        return service.singleQuote(params.builtQuery, BuildConfig.ENV, BuildConfig.FORMAT)
    }

    override fun addSymbolToSearchHistory(params: UseCaseHistoryAddSymbol.Params): Completable {
        return Completable.fromRunnable({ dao.insert(params.entry) })
    }

    override fun getSymbolStatus(symbol: String): Single<Boolean> {
        return symbolsDataSource.isTracked(symbol)
    }

    override fun flipSymbolStatus(symbol: String): Single<Boolean> {
        return getSymbolStatus(symbol).doAfterSuccess({ value ->
            if (value) {
                removeSymbolFromFavorites(symbol)
            } else {
                addSymbolToFavorites(symbol)
            }
        }).flatMap({ getSymbolStatus(symbol) })
    }

    override fun addSymbolToFavorites(symbol: String): Single<Boolean> {
        return symbolsDataSource.addSymbol(symbol)
    }

    override fun removeSymbolFromFavorites(symbol: String): Single<Boolean> {
        return symbolsDataSource.removeSymbol(symbol)
    }
}