package us.hnry.fancy.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import us.hnry.fancy.DetailActivity;
import us.hnry.fancy.R;
import us.hnry.fancy.adapters.holders.QuotesViewHolder;
import us.hnry.fancy.network.StockPresenter.PersistentSymbolsChangedListener;
import us.hnry.fancy.network.model.SingleQuote;
import us.hnry.fancy.network.model.Symbol;
import us.hnry.fancy.ui.MainItemTouchCallback.ItemTouchHelperListener;
import us.hnry.fancy.utils.Utility;

/**
 * Created by Henry on 2/8/2016.
 * Remastered StockRecycler to handle RecyclerView and its click events.
 * <p>
 * Name of the class also makes reference to the Retrofit implementation that fetches
 * the data onto the new Quote model class.
 */
public class QuotesAdapter extends BaseAdapter<SingleQuote, QuotesViewHolder>
        implements ItemTouchHelperListener {

    @NonNull
    private PersistentSymbolsChangedListener mListener;

    /**
     * @param context  Used only to fetch resource items.
     * @param listener Adapter requires a registered listener of the
     *                 PersistentSymbolsChangedListener interface
     */
    public QuotesAdapter(@NonNull Context context, @NonNull PersistentSymbolsChangedListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    public QuotesViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row_main_card, parent, false);
        return new QuotesViewHolder(itemView, caller -> {
            Intent launchDetail = new Intent(caller.getContext(), DetailActivity.class);
            launchDetail.putExtra(Utility.QUOTE_INTENT, (SingleQuote) itemView.getTag());
            caller.getContext().startActivity(launchDetail);
        });
    }

    @Override
    public void onBindViewHolder(QuotesViewHolder holder, int position) {
        SingleQuote singleQuote = getItems().get(position);
        holder.setSymbol(singleQuote.getSymbol());
        holder.setSymbolName(singleQuote.getName());

        holder.setCloseValue(Utility.formatDouble(singleQuote.getPreviousClose()));

        String open = singleQuote.getOpen();
        holder.setOpenValue(Utility.formatDouble(open));
        String ask = singleQuote.getAsk();
        holder.setAskValue(Utility.formatDouble(ask));
        if (open != null && ask != null) {
            double difference = Utility.compareAskOpen(singleQuote);
            holder.getAskTextView().setTextColor(
                    difference > 0 ? getContext().getResources().getColor(R.color.currentAskGreen) :
                            difference < 0 ? Color.RED : Color.BLACK
            );
        }

        holder.itemView.setTag(singleQuote);
    }

    @Override
    public void onItemSwiped(int adapterPosition) {
        SingleQuote quote = getItems().get(adapterPosition);
        getItems().remove(quote);
        mListener.onSymbolRemoved(new Symbol(quote.getName(), quote.getSymbol()));
        notifyDataSetChanged();
    }

    @Override
    public void onItemDragged() {
        // nothing to do here
    }
}
