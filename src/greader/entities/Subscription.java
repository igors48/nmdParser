package greader.entities;

import util.Assert;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 27.08.2011
 */
public class Subscription {

    private String id;
    private String title;

    public Subscription(final String _id, final String _title) {
        setId(_id);
        setTitle(_title);
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String _id) {
        Assert.notNull(_id, "Id is null");
        this.id = _id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(final String _title) {
        Assert.notNull(_title, "Title is null");
        this.title = _title;
    }

}
