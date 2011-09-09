package flowtext;

import flowtext.text.EmptyLine;
import util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 07.09.2008
 */
public class Title implements FlowTextObject {

    private final List<FlowTextObject> content;

    public Title() {
        this.content = new ArrayList<FlowTextObject>();
    }

    public FlowTextType getType() {
        return FlowTextType.TITLE;
    }

    public void insertParagraph(final Paragraph _paragraph) {
        Assert.notNull(_paragraph);

        this.content.add(_paragraph);
    }

    public void insertEmptyLine() {
        this.content.add(new EmptyLine());
    }

    public List<FlowTextObject> getContent() {
        return this.content;
    }
}
