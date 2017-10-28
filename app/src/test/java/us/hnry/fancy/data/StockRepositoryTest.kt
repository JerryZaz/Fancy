package us.hnry.fancy.data

import org.junit.Assert
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import us.hnry.fancy.domain.StockRepository
import us.hnry.fancy.network.StockServiceImpl

/**
 * @author Henry
 * 10/8/2017
 */
@RunWith(MockitoJUnitRunner::class)
class StockRepositoryTest {

    @Mock
    lateinit var service: StockServiceImpl.StockService
    lateinit var repository: StockRepository

    @Before
    fun setUp() {
        repository = StockRepositoryImpl(service)
        Assert.assertNotNull(repository)
    }

    /*@Test
    fun testGetQuoteObservable() {
        val singleQuote: SingleQuote = mock(SingleQuote::class.java)
        `when`(singleQuote.symbol).thenReturn("SQ")

        val results: Results<*>? = mock(Results::class.java)
        `when`(results?.quote).thenReturn(singleQuote)

        val query: Query<*>? = mock(Query::class.java)
        `when`(query?.results).thenReturn(results)

        val quoteWrapper: Quote<*>? = mock(Quote::class.java)
        `when`(quoteWrapper?.query).thenReturn(query)

        `when`(service.singleQuotesStreamer(anyString(), anyString(), anyString()))
                .thenReturn(Observable.just(quoteWrapper))

        repository.getQuotes(StockUseCase.Params("SQ")).test().assertValue({ list: List<SingleQuote> -> list[0].symbol == "SQ" })
    }*/
}