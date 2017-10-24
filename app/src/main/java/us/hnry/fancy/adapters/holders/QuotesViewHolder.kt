package us.hnry.fancy.adapters.holders

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import us.hnry.fancy.R
import us.hnry.fancy.adapters.QuoteDetailAdapter
import us.hnry.fancy.presentation.model.StockDetail
import us.hnry.fancy.presentation.transform.QuoteDetailRowItemTransform
import us.hnry.fancy.ui.DividerItemDecoration

/**
 * @author Henry
 * 10/7/2017
 */
class QuotesViewHolder(itemView: View, private val listener: RetroQuoteViewHolderClicks) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private val headerView by lazy { itemView.findViewById<TextView>(R.id.quote_detail_row_header) }
    private val recyclerView by lazy { itemView.findViewById<RecyclerView>(R.id.quote_detail_row_list) }
    private val adapter by lazy { QuoteDetailAdapter(itemView.context) }

    init {
        itemView.setOnClickListener(this)
        recyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(itemView.context, null))
        recyclerView.adapter = adapter
    }

    fun setData(stockDetail: StockDetail) {
        val label = "${stockDetail.symbolName} (${stockDetail.symbol})"
        headerView.text = label

        adapter.swapList(QuoteDetailRowItemTransform().apply(stockDetail))
    }

    override fun onClick(v: View) {
        listener.onItemClick(v)
    }

    interface RetroQuoteViewHolderClicks {
        fun onItemClick(view: View)
    }
}