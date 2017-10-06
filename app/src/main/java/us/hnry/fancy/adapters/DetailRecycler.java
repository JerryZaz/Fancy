package us.hnry.fancy.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import us.hnry.fancy.R;
import us.hnry.fancy.utils.Utility;

/**
 * Created by Henry on 2/5/2016.
 * RecyclerView Adapter for the Detail view. It takes the fields of a Parcelable object and
 * displays each field in the Recycler View.
 */
public class DetailRecycler extends RecyclerView.Adapter<DetailRecycler.DetailRecyclerViewHolder> {

    private ArrayList<String> mKeys;
    private Map<String, String> mMap;

    /**
     * The RecyclerView in the detail will be populated by a Map representation of a SingleQuote
     * @param keys being the list of variables of the Model class, as they were stored in the Map
     * @param map representation of all the instance variables and instance values.
     */
    public DetailRecycler(ArrayList<String> keys, Map<String, String> map) {
        mKeys = keys;
        mMap = map;
    }

    @Override
    public DetailRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row_detail, parent, false);
        return new DetailRecyclerViewHolder(v, caller -> {
            // nothing to do here
        });
    }

    @Override
    public void onBindViewHolder(DetailRecyclerViewHolder holder, int position) {
        String key = Utility.splitCamelCase(mKeys.get(position));
        holder.keyTextView.setText(key);
        try {
            holder.valueTextView.setText(
                    Utility.formatDouble(mMap.get(mKeys.get(position)))
            );
        } catch (NumberFormatException e) {
            holder.valueTextView.setText(
                    mMap.get(mKeys.get(position))
            );
        }
        holder.itemView.setTag(key);
    }

    @Override
    public int getItemCount() {
        return mKeys.size();
    }

    /**
     * RecyclerView ViewHolder implementation to display each item of the Parcelable
     * Quote object.
     */
    public static class DetailRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView keyTextView;
        public TextView valueTextView;
        public DetailViewHolderClicks mListener;

        public DetailRecyclerViewHolder(View itemView, DetailViewHolderClicks listener) {
            super(itemView);
            keyTextView = itemView.findViewById(R.id.detail_single_item_key);
            valueTextView = itemView.findViewById(R.id.detail_single_item_value);
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(v);
        }

        public interface DetailViewHolderClicks {
            void onItemClick(View caller);
        }
    }
}
