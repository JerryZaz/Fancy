package us.hnry.fancy.adapters;

import android.content.Context;
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
 */
public class DetailRecycler extends RecyclerView.Adapter<DetailRecycler.DetailRecyclerViewHolder> {

    private ArrayList<String> mKeys;
    private Context mContext;
    private Map<String, String> mMap;

    public DetailRecycler(Context context, ArrayList<String> keys, Map<String, String> map) {
        mContext = context;
        mKeys = keys;
        mMap = map;
    }

    @Override
    public DetailRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row_detail, parent, false);
        return new DetailRecyclerViewHolder(v);
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

    public class DetailRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView keyTextView;
        TextView valueTextView;

        public DetailRecyclerViewHolder(View itemView) {
            super(itemView);
            keyTextView = (TextView) itemView.findViewById(R.id.detail_single_item_key);
            valueTextView = (TextView) itemView.findViewById(R.id.detail_single_item_value);
        }
    }
}
