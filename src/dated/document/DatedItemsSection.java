package dated.document;

import dated.DatedItem;
import util.Assert;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 08.12.2008
 */
public class DatedItemsSection {

    private final String title;
    private final List<DatedItem> items;

    public DatedItemsSection(String _title, List<DatedItem> _items) {
        this.title = _title;

        Assert.notNull(_items);
        this.items = _items;
    }

    public List<DatedItem> getItems() {
        return this.items;
    }

    public int size() {
        return this.items.size();
    }

    public DatedItem get(int _index) {
        Assert.greaterOrEqual(_index, 0, "");
        Assert.less(_index, this.items.size(), "");

        return this.items.get(_index);
    }

    public String getTitle() {
        return this.title;
    }
}
