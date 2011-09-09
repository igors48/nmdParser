package flowtext.list;

import flowtext.FlowTextObject;
import flowtext.FlowTextType;
import flowtext.Paragraph;
import util.Assert;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 25.01.2009
 */
public class ListItem implements FlowTextObject {

    private final List<Paragraph> items;

    public ListItem(final List<Paragraph> _items) {
        Assert.notNull(_items);
        Assert.greater(_items.size(), 0, "");

        this.items = _items;
    }

    public List<Paragraph> getItems() {
        return this.items;
    }

    public FlowTextType getType() {
        return FlowTextType.LISTITEM;
    }
}
