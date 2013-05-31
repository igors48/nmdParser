package flowtext.text;

import flowtext.FlowTextObjectText;
import flowtext.FlowTextType;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 25.08.2008
 */
public class StrikethroughText implements FlowTextObjectText {

    private final String content;

    public StrikethroughText(final String _content) {
        Assert.isValidString(_content);

        this.content = _content;
    }

    public FlowTextType getType() {
        return FlowTextType.STRIKETHROUGH_TEXT;
    }

    public String getText() {
        return this.content;
    }
}
