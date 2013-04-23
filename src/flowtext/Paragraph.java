package flowtext;

import flowtext.resource.Resource;
import flowtext.text.*;
import util.Assert;

import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 25.08.2008
 */
public class Paragraph implements FlowTextObject {

    //TODO Section, Paragraph, Title ������ ��� FlowTextObjectContainer
    private final List<FlowTextObject> content;

    public Paragraph() {
        this.content = newArrayList();
    }

    public void insertSimpleText(String _text) {
        Assert.isValidString(_text);

        this.content.add(new SimpleText(_text));
    }

    public void insertStrongText(String _text) {
        Assert.isValidString(_text);

        this.content.add(new StrongText(_text));
    }

    public void insertEmphasisText(String _text) {
        Assert.isValidString(_text);

        this.content.add(new EmphasisText(_text));
    }

    public void insertCodeText(String _text) {
        Assert.isValidString(_text);

        this.content.add(new CodeText(_text));
    }

    public void insertStrikethroughText(String _text) {
        Assert.isValidString(_text);

        this.content.add(new StrikethroughText(_text));
    }

    public void insertFootNote(String _text, String _base) {
        Assert.isValidString(_text);
        Assert.notNull(_base);

        this.content.add(new FootNote(_text, _base));
    }

    public void insertResource(String _base, String _address) {
        Assert.isValidString(_base);
        Assert.isValidString(_address);

        this.content.add(new Resource(_base, _address));
    }

    public FlowTextType getType() {
        return FlowTextType.PARAGRAPH;
    }

    public List<FlowTextObject> getContent() {
        return this.content;
    }

    public String getBrief(int _length) {
        Assert.greater(_length, 0, "");

        StringBuffer buffer = new StringBuffer();

        for (FlowTextObject element : this.content) {

            if (FlowTextUtil.isText(element)) {
                buffer.append(((FlowTextObjectText) element).getText());
            }

            if (buffer.length() >= _length) {
                break;
            }
        }

        return buffer.toString();
    }

    public int getSize() {
        int result = 0;

        for (FlowTextObject object : this.content) {

            if (FlowTextUtil.isText(object)) {
                result += ((FlowTextObjectText) object).getText().length();
            }
        }

        return result;
    }

    //todo �������� �����

    public void addContent(List<FlowTextObject> _content) {
        Assert.notNull(_content);

        for (FlowTextObject object : _content) {
            this.content.add(object);
        }
    }

}
