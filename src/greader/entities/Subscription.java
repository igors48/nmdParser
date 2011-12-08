package greader.entities;

import util.Assert;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 27.08.2011
 */
public class Subscription {

    private String id;
    private String title;
    private Category[] categories;

    public Subscription(final String _id, final String _title, final String _category) {
        this(_id, _title, new Category[]{new Category(_category, _category)});
    }

    public Subscription(final String _id, final String _title, final Category[] _categories) {
        setId(_id);
        setTitle(_title);

        Assert.notNull(_categories, "Categories is null");
        this.categories = _categories;
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

    public Category[] getCategories() {
        return this.categories;
    }

    public void setCategories(final Category[] _categories) {
        Assert.notNull(_categories, "Categories is null");
        this.categories = _categories;
    }

}
