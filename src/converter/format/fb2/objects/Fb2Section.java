package converter.format.fb2.objects;

import converter.format.fb2.Stringable;
import converter.format.fb2.text.Fb2EmptyLine;
import flowtext.SectionMode;
import util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 31.08.2008
 */
public class Fb2Section implements Stringable {

    private static final String OPEN_TAG = "<section id=\"";
    private static final String INTER_TAG = "\">";
    private static final String CLOSE_TAG = "</section>";

    private final String id;
    private final List<Stringable> content;

    private SectionMode mode;

    public Fb2Section(String _id) {
        Assert.isValidString(_id);

        this.id = _id;
        this.content = newArrayList();

        this.mode = SectionMode.UNDEFINED;
    }

    public void insertParagraph(Fb2Paragraph _paragraph) throws Fb2SectionException {
        Assert.notNull(_paragraph);

        if (this.mode == SectionMode.SECTION) {
            throw new Fb2SectionException("Can`t add paragraph to Section in Section mode.");
        }

        this.mode = SectionMode.PARAGRAPH;

        this.content.add(_paragraph);
    }

    public void insertSection(Fb2Section _section) throws Fb2SectionException {
        Assert.notNull(_section);

        if (this.mode == SectionMode.PARAGRAPH) {
            throw new Fb2SectionException("Can`t add section to Section in Paragraph mode.");
        }

        this.mode = SectionMode.SECTION;

        this.content.add(_section);

    }

    public void insertEmptyLine() throws Fb2SectionException {

        if (this.mode == SectionMode.SECTION) {
            throw new Fb2SectionException("Can`t add empty line to Section in Section mode.");
        }

        this.mode = SectionMode.PARAGRAPH;

        this.content.add(new Fb2EmptyLine());
    }

    public void insertTitle(Fb2Title _title) {
        Assert.notNull(_title);

        this.content.add(_title);
    }

    public String[] getStrings() {
        List<String> result = newArrayList();
        StringBuffer buffer = new StringBuffer();

        buffer.append(OPEN_TAG);
        buffer.append(this.id);
        buffer.append(INTER_TAG);
        result.add(buffer.toString());
        result.addAll(Arrays.asList(getContentStrings()));
        result.add(CLOSE_TAG);

        return result.toArray(new String[result.size()]);
    }

    private String[] getContentStrings() {
        List<String> result = newArrayList();

        for (Stringable current : this.content) {
            String[] image = current.getStrings();
            result.addAll(Arrays.asList(image));
        }

        return result.toArray(new String[result.size()]);
    }

    public static class Fb2SectionException extends Exception {

        public Fb2SectionException() {
            super();
        }

        public Fb2SectionException(final String _s) {
            super(_s);
        }

        public Fb2SectionException(final String _s, final Throwable _throwable) {
            super(_s, _throwable);
        }

        public Fb2SectionException(final Throwable _throwable) {
            super(_throwable);
        }
    }
}
