package us.hnry.fancy.data.model;

import android.support.annotation.Nullable;

/**
 * @author Henry
 *         2/7/2016
 */
public class Quote<T> {

    @Nullable
    private Query<T> query;

    @Nullable
    public Query<T> getQuery() {
        return query;
    }
}
