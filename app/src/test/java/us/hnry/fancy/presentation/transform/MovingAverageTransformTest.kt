package us.hnry.fancy.presentation.transform

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import us.hnry.fancy.network.model.SingleQuote

/**
 * @author Henry
 * 10/25/2017
 */
@RunWith(MockitoJUnitRunner::class)
class MovingAverageTransformTest {
    @Test
    fun testMovingAverageTransform() {
        val quoteDetail = mock(SingleQuote::class.java)
        `when`(quoteDetail.fiftyDaysMovingAverage).thenReturn("156.87")
        `when`(quoteDetail.twoHundredDaysMovingAverage).thenReturn("152.40")

        val ema = MovingAverageTransform().apply(QuoteTransform().apply(quoteDetail))
        assertEquals("156.87", ema.fiftyDays)
        assertEquals("152.40", ema.twoHundredDays)
    }
}