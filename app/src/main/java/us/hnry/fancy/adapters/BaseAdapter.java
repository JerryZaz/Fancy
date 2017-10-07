package us.hnry.fancy.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Henry
 *         10/6/2017
 */

public abstract class BaseAdapter<I, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {
    private Context context;
    private List<I> items;

    public BaseAdapter(@NonNull Context context) {
        this.context = context;
        items = new ArrayList<>();
    }

    public Context getContext() {
        return context;
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public List<I> getItems() {
        return items;
    }

    /**
     * Helper method that updates the data displayed in the recycler view
     *
     * @param param the new information to be displayed
     */
    public void swapList(List<I> param) {

        if (items != null) {
            items.clear();
            items.addAll(param);
        } else {
            items = param;
        }
        notifyDataSetChanged();
    }
}
