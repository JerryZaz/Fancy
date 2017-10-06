package us.hnry.fancy.models;

import android.support.annotation.Nullable;

import us.hnry.fancy.models.Quote.SingleQuote;

/**
 * Created by Henry on 2/8/2016.
 * Model to receive single object results
 */
public class Single {

    @Nullable
    public Query query;

    public class Query {
        public Integer count;
        public String created;
        public String lang;
        public Results results;
    }

    public class Results {
        public SingleQuote quote;

        public SingleQuote getQuote() {
            return quote;
        }
    }
}
