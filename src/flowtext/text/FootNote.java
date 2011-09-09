package flowtext.text;

import flowtext.FlowTextObjectText;
import flowtext.FlowTextType;
import util.Assert;

/**
 * —сылка
 *
 * @author Igor Usenko
 *         Date: 07.01.2009
 */
public class FootNote implements FlowTextObjectText {

    private final String content;
    private final String base;

    public FootNote(final String _content, final String _base) {
        Assert.isValidString(_content, "Foot note content is not valid");
        Assert.isValidString(_base, "Foot note base is not valid");

        this.content = _content;
        this.base = _base;
    }

    public FlowTextType getType() {
        return FlowTextType.FOOT_NOTE;
    }

    public String getText() {
        return this.content;
    }

    public String getBase() {
        return this.base;
    }
}