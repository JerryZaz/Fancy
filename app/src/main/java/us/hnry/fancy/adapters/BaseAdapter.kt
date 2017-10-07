package us.hnry.fancy.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView

/**
 * @author Henry
 * 10/7/2017
 */
abstract class BaseAdapter<I, V : RecyclerView.ViewHolder>(var context: Context) : RecyclerView.Adapter<V>() {
    var items: ArrayList<I> = ArrayList()

    override fun getItemCount(): Int {
        return items.size
    }

    fun swapList(newItems: List<I>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}