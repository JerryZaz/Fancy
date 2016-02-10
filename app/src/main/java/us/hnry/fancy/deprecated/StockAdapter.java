package us.hnry.fancy.deprecated;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import us.hnry.fancy.R;
import us.hnry.fancy.utils.Utility;

/**
 * Created by Henry on 1/31/2016.
 *
 */
@SuppressWarnings("ALL")
@Deprecated
public class StockAdapter extends ArrayAdapter {

    ArrayList<Stock> quotes;
    Context context;

    public StockAdapter(Context context, ArrayList<Stock> quotes) {
        super(context, R.layout.single_row , quotes);
        this.quotes = quotes;
        this.context = context;
    }

    public void setQuotes(ArrayList<Stock> quotes) {
        this.quotes = quotes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        StockViewHolder holder = null;

        if(row == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.single_row, parent, false);
            holder = new StockViewHolder(row);
            row.setTag(holder);
        }
        else {
            holder = (StockViewHolder) row.getTag();
        }

        Stock quote = quotes.get(position);
        holder.symbolTextView.setText(quote.getSymbol());
        holder.nameTextView.setText(quote.getName());
        holder.closeTextView.setText(Utility.formatDouble(quote.getPreviousClose()));
        holder.openTextView.setText(Utility.formatDouble(quote.getOpen()));
        holder.askTextView.setText(Utility.formatDouble(quote.getAsk()));

        double change = quote.getAsk() - quote.getOpen();
        holder.askTextView.setTextColor(
                change > 0 ? context.getResources().getColor(R.color.currentAskGreen) :
                        change < 0 ? Color.RED : Color.BLACK);

        return row;
    }

    class StockViewHolder{
        TextView symbolTextView;
        TextView nameTextView;
        TextView closeTextView;
        TextView openTextView;
        TextView askTextView;

        StockViewHolder(View v){
            symbolTextView = (TextView) v.findViewById(R.id.single_row_text_view_symbol);
            nameTextView = (TextView) v.findViewById(R.id.single_row_text_view_name);
            closeTextView = (TextView) v.findViewById(R.id.single_row_text_view_close);
            openTextView = (TextView) v.findViewById(R.id.single_row_text_view_open);
            askTextView = (TextView) v.findViewById(R.id.single_row_text_view_ask);
        }
    }
}
