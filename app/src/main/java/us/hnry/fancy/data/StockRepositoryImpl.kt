package us.hnry.fancy.data

import android.util.Log
import io.reactivex.Observable
import us.hnry.fancy.BuildConfig
import us.hnry.fancy.domain.StockRepository
import us.hnry.fancy.domain.StockUseCase
import us.hnry.fancy.network.StockServiceImpl
import us.hnry.fancy.network.model.SingleQuote

/**
 * @author Henry
 * 10/7/2017
 */
class StockRepositoryImpl(private val stockService: StockServiceImpl.StockService) : StockRepository {
    override fun getQuotes(params: StockUseCase.Params): Observable<List<SingleQuote>> {

        Log.v("Henry ~", "SymbolsCount[${params.symbolsCount}]: ${params.builtQuery}")

        return if (params.symbolsCount == 1) {
            stockService.singleQuotesStreamer(params.builtQuery, BuildConfig.ENV, BuildConfig.FORMAT)
                    .flatMap({ quote ->
                        val list = ArrayList<SingleQuote>()
                        list.add(quote)
                        Observable.just(list)
                    })
        } else {
            stockService.quotesStreamer(params.builtQuery, BuildConfig.ENV, BuildConfig.FORMAT)
        }
    }
}