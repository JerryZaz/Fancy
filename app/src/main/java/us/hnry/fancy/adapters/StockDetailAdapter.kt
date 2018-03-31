package us.hnry.fancy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import us.hnry.fancy.R
import us.hnry.fancy.adapters.holders.StockDetailViewHolder
import us.hnry.fancy.databinding.StockDetailItemBinding
import us.hnry.fancy.presentation.wrapper.StockDetailRowItem

/**
 * @author Henry
 * 10/29/2017
 */
class StockDetailAdapter(context: Context) : BaseAdapter<StockDetailRowItem, StockDetailViewHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): StockDetailViewHolder {
        val holder = StockDetailViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.stock_detail_item, parent, false))
        holder.binding = StockDetailItemBinding.bind(holder.itemView)
        return holder
    }

    override fun onBindViewHolder(holder: StockDetailViewHolder?, position: Int) {
        val item = items[position]
        if (holder is StockDetailViewHolder) {
            holder.binding?.stockDetail = item
        }
    }
}