package us.hnry.fancy.deprecated;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import us.hnry.fancy.R;
import us.hnry.fancy.utils.Utility;

/**
 * Created by Henry on 2/1/2016.
 *
 */
@Deprecated
public class DetailAdapter extends ArrayAdapter {

    private ArrayList<String> mKeys;
    private Context mContext;
    private Map<String, String> mMap;

    public DetailAdapter(Context context, ArrayList<String> keys, Map<String, String> map) {
        super(context, R.layout.single_row_detail, keys);
        mContext = context;
        mKeys = keys;
        mMap = map;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        DetailViewHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.single_row_detail, parent, false);
            holder = new DetailViewHolder(row);
            row.setTag(holder);
        } else {
            holder = (DetailViewHolder) row.getTag();
        }
        holder.keyTextView.setText(Utility.splitCamelCase(mKeys.get(position)));
        try {
            holder.valueTextView.setText(
                    Utility.formatDouble(mMap.get(mKeys.get(position)))
            );
        } catch (NumberFormatException e) {
            holder.valueTextView.setText(
                    mMap.get(mKeys.get(position))
            );
        }

        return row;
    }

    class DetailViewHolder {
        TextView keyTextView;
        TextView valueTextView;

        DetailViewHolder(View v) {
            keyTextView = (TextView) v.findViewById(R.id.detail_single_item_key);
            valueTextView = (TextView) v.findViewById(R.id.detail_single_item_value);
        }
    }
}
