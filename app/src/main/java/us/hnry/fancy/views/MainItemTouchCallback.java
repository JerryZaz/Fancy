package us.hnry.fancy.views;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import us.hnry.fancy.adapters.RetroQuoteRecycler;

/**
 * Created by Henry on 3/18/2016.
 */
public class MainItemTouchCallback extends ItemTouchHelper.Callback {
    private ItemTouchHelperListener mListener;

    public MainItemTouchCallback(RetroQuoteRecycler adapter) {
        mListener = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mListener.onItemDragged();
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mListener.onItemSwiped(viewHolder.getAdapterPosition());
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    /**
     * Implementing this interface will make a class reactive to cards being dragged or swiped.
     */
    public interface ItemTouchHelperListener{

        /**
         * Handle the Card-being-swiped event.
         * @param adapterPosition index of the item being swiped.
         */
        void onItemSwiped(int adapterPosition);

        /**
         * Handle the Card-being-dragged event.
         */
        void onItemDragged();
    }
}
