package greader.entities;

import util.Assert;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 18.11.2011
 */
public class Category {

    private final String id;
    private final String label;

    public Category(final String _id, final String _label) {
        Assert.notNull(_id, "Id is null;");
        this.id = _id;

        Assert.notNull(_label, "Label is null");
        this.label = _label;
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

}
