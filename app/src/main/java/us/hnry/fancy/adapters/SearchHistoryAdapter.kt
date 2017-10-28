package us.hnry.fancy.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import us.hnry.fancy.R
import us.hnry.fancy.adapters.holders.SearchHistoryViewHolder
import us.hnry.fancy.databinding.SearchHistoryItemBinding
import us.hnry.fancy.presentation.wrapper.SearchHistoryRowItem

/**
 * @author Henry
 * 10/28/2017
 */
class SearchHistoryAdapter(context: Context) : BaseAdapter<SearchHistoryRowItem, SearchHistoryViewHolder>(context) {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SearchHistoryViewHolder {
        val holder = SearchHistoryViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.search_history_item, parent, false))
        holder.binding = SearchHistoryItemBinding.bind(holder.itemView)
        return holder
    }

    override fun onBindViewHolder(holder: SearchHistoryViewHolder?, position: Int) {
        holder?.let {
            val item = items[position]
            holder.binding?.searchHistoryItem = item
        }
    }
}