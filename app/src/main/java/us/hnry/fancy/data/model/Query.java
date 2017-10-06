package us.hnry.fancy.data.model;

/**
 * @author Henry
 *         10/6/2017
 */

public class Query<T> {

    private Integer count;
    private String created;
    private String lang;
    private Results<T> results;

    private Query() {
        // default empty constructor
    }

    public Results<T> getResults() {
        return results;
    }

    public Integer getCount() {
        return count;
    }

    public String getCreated() {
        return created;
    }

    public String getLang() {
        return lang;
    }
}