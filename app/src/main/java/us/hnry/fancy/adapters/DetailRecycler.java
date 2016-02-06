package us.hnry.fancy.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

import us.hnry.fancy.R;
import us.hnry.fancy.utils.Utility;

/**
 * Created by Henry on 2/5/2016.
 *
 */
public class DetailRecycler extends RecyclerView.Adapter<DetailRecycler.DetailRecyclerViewHolder> {

    private ArrayList<String> mKeys;
    private Map<String, String> mMap;

    public DetailRecycler(ArrayList<String> keys, Map<String, String> map) {
        mKeys = keys;
        mMap = map;
    }

    @Override
    public DetailRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row_detail, parent, false);
        return new DetailRecyclerViewHolder(v, new DetailRecyclerViewHolder.DetailViewHolderClicks() {
            @Override
            public void onItemClick(View caller) {
                Toast.makeText(caller.getContext(), "Worked", Toast.LENGTH_SHORT).show();
            }
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

    public static class DetailRecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView keyTextView;
        public TextView valueTextView;
        public DetailViewHolderClicks mListener;

        public DetailRecyclerViewHolder(View itemView, DetailViewHolderClicks listener) {
            super(itemView);
            keyTextView = (TextView) itemView.findViewById(R.id.detail_single_item_key);
            valueTextView = (TextView) itemView.findViewById(R.id.detail_single_item_value);
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
