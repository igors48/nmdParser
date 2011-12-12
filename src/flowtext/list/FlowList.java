package flowtext.list;

import flowtext.FlowTextObject;
import flowtext.FlowTextType;
import flowtext.Paragraph;
import util.Assert;

import java.util.ArrayList;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 25.01.2009
 */
public class FlowList implements FlowTextObject {

    private final List<ListItem> items;

    public FlowList() {
        this.items = newArrayList();
    }

    public FlowTextType getType() {
        return FlowTextType.LIST;
    }

    public void appendItem(final List<Paragraph> _paragraphs) {
        Assert.notNull(_paragraphs);

        this.items.add(new ListItem(_paragraphs));
    }

    public void appendInnerList(final FlowList _list) {
        Assert.notNull(_list);
    }
}
