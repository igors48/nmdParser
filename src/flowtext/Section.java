package flowtext;

import flowtext.list.FlowList;
import flowtext.text.EmptyLine;
import util.Assert;
import util.TextTools;

import java.util.ArrayList;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 25.08.2008
 */
public class Section implements FlowTextObject {

    private final String id;
    private final List<FlowTextObject> content;

    private Title title;
    private SectionMode mode;

    public Section(final String _id) {
        Assert.isValidString(_id, "Id is not valid");

        this.id = _id;
        this.content = newArrayList();
        this.title = null;

        this.mode = SectionMode.UNDEFINED;
    }

    public void insertParagraph(final Paragraph _paragraph) throws SectionException {
        Assert.notNull(_paragraph, "Paragraph is null");

        if (this.mode == SectionMode.SECTION) {
            throw new SectionException("Can`t add paragraph to Section in Section mode.");
        }

        this.mode = SectionMode.PARAGRAPH;

        this.content.add(_paragraph);
    }

    public void insertSection(final Section _section) throws SectionException {
        Assert.notNull(_section, "Section is null");

        if (this.mode == SectionMode.PARAGRAPH) {
            throw new SectionException("Can`t add section to Section in Paragraph mode.");
        }

        this.mode = SectionMode.SECTION;

        this.content.add(_section);

    }

    public void insertEmptyLine() throws SectionException {

        if (this.mode == SectionMode.SECTION) {
            throw new SectionException("Can`t add empty line to Section in Section mode.");
        }

        this.mode = SectionMode.PARAGRAPH;
        this.content.add(new EmptyLine());
    }

    public void insertTitle(final Title _title) {
        Assert.notNull(_title, "Title is null");

        this.title = _title;
    }

    public void insertList(final FlowList _list) throws SectionException {
        Assert.notNull(_list, "List is null");

        if (this.mode == SectionMode.SECTION) {
            throw new SectionException("Can`t add list to Section in Section mode.");
        }

        this.mode = SectionMode.PARAGRAPH;
        this.content.add(_list);
    }

    public FlowTextType getType() {
        return FlowTextType.SECTION;
    }

    public String getId() {
        return this.id;
    }

    public List<FlowTextObject> getContent() {
        return this.content;
    }

    public Title getTitle() {
        return this.title;
    }

    public void removeDoubleEmptyLines() {

        if (this.mode != SectionMode.PARAGRAPH) {
            return;
        }

        int index = 0;

        while (index < this.content.size() - 1) {
            FlowTextObject first = this.content.get(index);
            FlowTextObject second = this.content.get(index + 1);

            if (isDoubleEmptyLine(first, second)) {
                this.content.remove(index);
                index = 0;
            } else {
                ++index;
            }
        }

    }

    //todo некрасиво сделано. м.б. insertTo(Section _section) и нужно ли оно вообще

    public void copyFrom(final Section _section) throws SectionException {
        Assert.notNull(_section, "Section is null");

        for (FlowTextObject object : _section.getContent()) {

            switch (object.getType()) {
                case PARAGRAPH: {
                    insertParagraph((Paragraph) object);
                    break;
                }
                case SECTION: {
                    // todo вложенные секции не обработаются
                    insertSection((Section) object);
                    break;
                }
                case EMPTY_LINE: {
                    this.content.add(object);
                    break;
                }
                case TITLE: {
                    insertTitle((Title) object);
                    break;
                }
                default: {
                    throw new SectionException("Unexpected type while section copied " + object.getType());
                }
            }
        }
    }

    public String getBrief(final int _length) {
        Assert.greater(_length, 0, "Length <= 0");

        StringBuffer buffer = new StringBuffer();

        for (FlowTextObject element : this.content) {

            if (element.getType() == FlowTextType.PARAGRAPH) {
                buffer.append(((Paragraph) element).getBrief(_length));
            }

            if (buffer.length() >= _length) {
                break;
            }
        }

        return TextTools.removeHtmlTags(TextTools.intelliCut(buffer.toString(), _length));
    }

    public int getSize() {
        int result = 0;

        for (FlowTextObject object : this.content) {

            if (object.getType() == FlowTextType.PARAGRAPH) {
                result += ((Paragraph) object).getSize();
            }
        }

        return result;
    }

    private boolean isDoubleEmptyLine(final FlowTextObject _first, final FlowTextObject _second) {
        FlowTextType first = _first.getType();
        FlowTextType second = _second.getType();

        return (first == FlowTextType.EMPTY_LINE) && (second == FlowTextType.EMPTY_LINE);
    }

    public static class SectionException extends Exception {

        public SectionException() {
            super();
        }

        public SectionException(final String _s) {
            super(_s);
        }

        public SectionException(final String _s, final Throwable _throwable) {
            super(_s, _throwable);
        }

        public SectionException(final Throwable _throwable) {
            super(_throwable);
        }
    }
}
