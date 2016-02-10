package us.hnry.fancy.deprecated;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import us.hnry.fancy.R;
import us.hnry.fancy.models.Symbol;

/**
 * Created by Henry on 2/1/2016.
 *
 */
@SuppressWarnings("ALL")
@Deprecated
public class SearchAdapter extends ArrayAdapter {

    private Context mContext;
    private ArrayList<Symbol> results;

    public SearchAdapter(Context context, ArrayList<Symbol> results) {
        super(context, R.layout.single_row_search, results);
        this.mContext = context;
        this.results = results;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        SearchViewHolder holder = null;
        if(row == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.single_row_search, parent, false);
            holder = new SearchViewHolder(row);
            row.setTag(holder);
        }
        else {
            holder = (SearchViewHolder) row.getTag();
        }

        Symbol watch = results.get(position);
        holder.companyTextView.setText(watch.getCompany());
        holder.symbolTextView.setText(watch.getSymbol());

        return row;
    }

    class SearchViewHolder{

        TextView companyTextView;
        TextView symbolTextView;

        SearchViewHolder(View v){
            companyTextView = (TextView) v.findViewById(R.id.search_single_row_company);
            symbolTextView = (TextView) v.findViewById(R.id.search_single_row_symbol);
        }
    }
}
