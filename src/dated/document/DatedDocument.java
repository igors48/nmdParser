package dated.document;

import dated.DatedItem;
import util.Assert;

import java.util.List;


/**
 * @author Igor Usenko
 *         Date: 09.10.2008
 */
public class DatedDocument {

    private final Header header;
    private final List<DatedItemsSection> items;

    public DatedDocument(Header _header, List<DatedItemsSection> _items) {
        Assert.notNull(_header);
        Assert.notNull(_items);

        this.header = _header;
        this.items = _items;
    }

    public String getAuthorFirstName() {
        return this.header.getAuthorFirstName();
    }

    public String getAuthorLastName() {
        return this.header.getAuthorLastName();
    }

    public String getBookTitle() {
        return this.header.getBookTitle();
    }

    public int getTopicCount() {
        return this.items.size();
    }

    public int getItemCount(int _topicIndex) {
        Assert.inRangeInclusive(_topicIndex, 0, getTopicCount(), "");

        return this.items.get(_topicIndex).size();
    }

    public DatedItem getItem(int _topicIndex, int _itemIndex) {
        Assert.inRangeInclusive(_itemIndex, 0, getItemCount(_topicIndex), "");

        return this.items.get(_topicIndex).get(_itemIndex);
    }
}