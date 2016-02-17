package us.hnry.fancy.views;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by Henry on 2/2/2016.
 *
 */
@SuppressWarnings("ALL")
public class CustomListView extends ListView {

    private static final String TAG = CustomListView.class.getName();
    private DataSetObserver mDataSetObserver = new AdapterDataSetObserver();
    private Adapter mAdapter;

    @SuppressWarnings("unused")
    public CustomListView(Context context) {
        super(context);
    }

    @SuppressWarnings("unused")
    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressWarnings("unused")
    public CustomListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter(adapter);

        if (mAdapter != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
        }
        mAdapter = adapter;

        mAdapter.registerDataSetObserver(mDataSetObserver);
    }

    void refreshVisibleViews() {
        if (mAdapter != null) {
            for (int i = getFirstVisiblePosition(); i <= getLastVisiblePosition(); i ++) {
                final int dataPosition = i - getHeaderViewsCount();
                final int childPosition = i - getFirstVisiblePosition();
                if (dataPosition >= 0 && dataPosition < mAdapter.getCount()
                        && getChildAt(childPosition) != null) {
                    Log.v(TAG, "Refreshing view (data=" + dataPosition + ",child=" + childPosition + ")");
                    mAdapter.getView(dataPosition, getChildAt(childPosition), this);
                }
            }
        }
    }

    class AdapterDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            super.onChanged();

            refreshVisibleViews();
        }

        @Override
        public void onInvalidated() {
            super.onInvalidated();

            refreshVisibleViews();
        }
    }

}
