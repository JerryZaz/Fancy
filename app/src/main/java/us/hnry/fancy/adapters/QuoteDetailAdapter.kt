package us.hnry.fancy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import us.hnry.fancy.R
import us.hnry.fancy.adapters.holders.BaseQuoteViewHolder
import us.hnry.fancy.adapters.holders.QuoteSummaryViewHolder
import us.hnry.fancy.adapters.util.QuoteAdapterRowTypes
import us.hnry.fancy.databinding.QuoteSummaryItemBinding
import us.hnry.fancy.presentation.wrapper.BaseQuoteRowItem
import us.hnry.fancy.presentation.wrapper.SummaryRowItem

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
        }
        return QuoteSummaryViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.quote_summary_item, parent, false))
    }

    override fun onBindViewHolder(holder: BaseQuoteViewHolder<*>?, position: Int) {
        if (holder is QuoteSummaryViewHolder) {
            val item = items[position] as SummaryRowItem
            val binding = holder.binding as QuoteSummaryItemBinding
            binding.quoteSummary = item
        }
    }
}