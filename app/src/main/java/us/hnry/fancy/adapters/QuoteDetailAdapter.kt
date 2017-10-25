package us.hnry.fancy.adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.ViewGroup
import us.hnry.fancy.R
import us.hnry.fancy.adapters.holders.BaseQuoteViewHolder
import us.hnry.fancy.adapters.holders.QuoteEmaViewHolder
import us.hnry.fancy.adapters.holders.QuoteHistoryViewHolder
import us.hnry.fancy.adapters.holders.QuoteSummaryViewHolder
import us.hnry.fancy.adapters.util.QuoteAdapterRowTypes
import us.hnry.fancy.databinding.QuoteEmaItemBinding
import us.hnry.fancy.databinding.QuoteHistoryItemBinding
import us.hnry.fancy.databinding.QuoteSummaryItemBinding
import us.hnry.fancy.presentation.model.Summary
import us.hnry.fancy.presentation.wrapper.BaseQuoteRowItem
import us.hnry.fancy.presentation.wrapper.HistoryRowItem
import us.hnry.fancy.presentation.wrapper.MovingAverageRowItem
import us.hnry.fancy.presentation.wrapper.SummaryRowItem
import us.hnry.fancy.utils.Utility

/**
 * @author Henry
 * 10/22/2017
 */
class QuoteDetailAdapter(context: Context) : BaseAdapter<BaseQuoteRowItem<*>, BaseQuoteViewHolder<*>>(context) {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseQuoteViewHolder<*> {
        when (viewType) {
            QuoteAdapterRowTypes.SUMMARY -> {
                parent?.let {
                    val holder = QuoteSummaryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.quote_summary_item, parent, false))
                    holder.binding = QuoteSummaryItemBinding.bind(holder.itemView)
                    return holder
                }
            }
            QuoteAdapterRowTypes.HISTORY -> {
                parent?.let {
                    val holder = QuoteHistoryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.quote_history_item, parent, false))
                    holder.binding = QuoteHistoryItemBinding.bind(holder.itemView)
                    return holder
                }
            }
            QuoteAdapterRowTypes.EMA -> {
                parent?.let {
                    val holder = QuoteEmaViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.quote_ema_item, parent, false))
                    holder.binding = QuoteEmaItemBinding.bind(holder.itemView)
                    return holder
                }
            }
        }
        return QuoteSummaryViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.quote_summary_item, parent, false))
    }

    override fun onBindViewHolder(holder: BaseQuoteViewHolder<*>?, position: Int) {
        val item = items[position]
        if (holder is QuoteSummaryViewHolder && item is SummaryRowItem) {
            val binding = holder.binding as QuoteSummaryItemBinding
            binding.quoteSummary = item

            val color = getAskColor(item.item)
            binding.quoteSummaryItemAsk.setTextColor(color)

        } else if (holder is QuoteHistoryViewHolder && item is HistoryRowItem) {
            val binding = holder.binding as QuoteHistoryItemBinding
            binding.quoteHistory = item
        } else if (holder is QuoteEmaViewHolder && item is MovingAverageRowItem) {
            val binding = holder.binding as QuoteEmaItemBinding
            binding.quoteEma = item
        }
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].rowType
    }

    private fun getAskColor(summary: Summary): Int {
        val change = Utility.compareAskOpen(summary)
        return when {
            change > 0 -> ContextCompat.getColor(context, R.color.currentAskGreen)
            change < 0 -> ContextCompat.getColor(context, R.color.currentAskRed)
            else -> ContextCompat.getColor(context, R.color.currentAskNeutral)
        }
    }
}