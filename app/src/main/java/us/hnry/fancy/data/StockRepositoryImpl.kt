package us.hnry.fancy.data

import io.reactivex.Observable
import us.hnry.fancy.BuildConfig
import us.hnry.fancy.domain.StockRepository
import us.hnry.fancy.domain.StockUseCase
import us.hnry.fancy.network.StockServiceImpl
import us.hnry.fancy.network.model.Quote
import us.hnry.fancy.network.model.SingleQuote
import java.util.function.Function

/**
 * @author Henry
 * 10/7/2017
 */
class StockRepositoryImpl(private val stockService: StockServiceImpl.StockService) : StockRepository {
    override fun getQuotes(params: StockUseCase.Params): Observable<List<SingleQuote>> {
        return if (params.symbolsCount == 1) {
            stockService.singleQuotesStreamer(params.builtQuery, BuildConfig.ENV, BuildConfig.FORMAT)
                    .flatMap({ quote ->
                        Observable.just(SingleQuoteTransformer().apply(quote))
                    })
        } else {
            stockService.quotesStreamer(params.builtQuery, BuildConfig.ENV, BuildConfig.FORMAT)
                    .flatMap({ quote ->
                        Observable.just(MultipleQuoteTransformer().apply(quote))
                    })
        }
    }

    class SingleQuoteTransformer : Function<Quote<SingleQuote>, List<SingleQuote>> {
        override fun apply(quoteWrapper: Quote<SingleQuote>): List<SingleQuote> {
            val list = ArrayList<SingleQuote>()
            val singleQuote = quoteWrapper.query?.results?.quote
            singleQuote?.let { list.add(it) }
            return list
        }
    }

    class MultipleQuoteTransformer : Function<Quote<List<SingleQuote>>, List<SingleQuote>?> {
        override fun apply(wrapper: Quote<List<SingleQuote>>): List<SingleQuote>? {
            return wrapper.query?.results?.quote
        }
    }
}