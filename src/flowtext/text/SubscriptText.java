package flowtext.text;

import flowtext.FlowTextObjectText;
import flowtext.FlowTextType;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 25.08.2008
 */
public class SubscriptText implements FlowTextObjectText {

    private final String content;

    public SubscriptText(final String _content) {
        Assert.isValidString(_content);

        this.content = _content;
    }

    public FlowTextType getType() {
        return FlowTextType.SUBSCRIPT_TEXT;
    }

    public String getText() {
        return this.content;
    }
}
