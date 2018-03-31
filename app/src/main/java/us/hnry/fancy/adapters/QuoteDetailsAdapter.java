package us.hnry.fancy.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Map;

import us.hnry.fancy.R;
import us.hnry.fancy.adapters.holders.QuoteDetailsViewHolder;
import us.hnry.fancy.utils.Utility;

/**
 * Created by Henry on 2/5/2016.
 * RecyclerView Adapter for the Detail view. It takes the fields of a Parcelable object and
 * displays each field in the Recycler View.
 */
public class QuoteDetailsAdapter extends RecyclerView.Adapter<QuoteDetailsViewHolder> {

    private List<String> mKeys;
    private Map<String, String> mMap;

    /**
     * The RecyclerView in the detail will be populated by a Map representation of a SingleQuote
     *
     * @param keys being the list of variables of the Model class, as they were stored in the Map
     * @param map  representation of all the instance variables and instance values.
     */
    public QuoteDetailsAdapter(List<String> keys, Map<String, String> map) {
        mKeys = keys;
        mMap = map;
    }

    @Override
    public QuoteDetailsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stock_detail_item, parent, false);
        return new QuoteDetailsViewHolder(v, caller -> {
            // nothing to do here
        });
    }

    @Override
    public void onBindViewHolder(QuoteDetailsViewHolder holder, int position) {
        String key = Utility.splitCamelCase(mKeys.get(position));
        holder.setKeyText(key);
        try {
            holder.setValueText(
                    Utility.formatDouble(mMap.get(mKeys.get(position)))
            );
        } catch (NumberFormatException e) {
            holder.setValueText(
                    mMap.get(mKeys.get(position))
            );
        }
        holder.itemView.setTag(key);
    }

    @Override
    public int getItemCount() {
        return mKeys.size();
    }


}
