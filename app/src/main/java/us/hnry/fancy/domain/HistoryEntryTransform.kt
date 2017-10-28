package us.hnry.fancy.domain

import io.reactivex.functions.Function
import us.hnry.fancy.domain.model.HistoryEntry
import us.hnry.fancy.local.entity.Stock

/**
 * @author Henry
 * 10/27/2017
 */
class HistoryEntryTransform : Function<Stock, HistoryEntry> {
    override fun apply(t: Stock): HistoryEntry {
        return HistoryEntry(t.symbol, t.name)
    }
}