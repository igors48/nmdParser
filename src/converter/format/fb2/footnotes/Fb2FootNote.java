package converter.format.fb2.footnotes;

import converter.format.fb2.Fb2Tools;
import converter.format.fb2.Stringable;
import util.Assert;

import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 07.01.2009
 */
public class Fb2FootNote implements Stringable {

    private static final String SECTION_START_PREFIX = "<section id=\"note";
    private static final String SECTION_START_POSTFIX = "\">";
    private static final String SECTION_END = "</section>";

    private static final String TITLE_START = "<title>";
    private static final String TITLE_END = "</title>";

    private static final String PARAGRAPH_START = "<p>";
    private static final String PARAGRAPH_END = "</p>";

    private final String id;
    private final String content;

    public Fb2FootNote(String _id, String _content) {
        Assert.isValidString(_id);
        Assert.isValidString(_content);

        this.id = _id;
        this.content = Fb2Tools.processEntities(_content);
    }

    public String[] getStrings() {
        List<String> result = newArrayList();

        result.add(SECTION_START_PREFIX + this.id + SECTION_START_POSTFIX);
        result.add(TITLE_START);
        result.add(PARAGRAPH_START + this.id + PARAGRAPH_END);
        result.add(TITLE_END);
        result.add(PARAGRAPH_START + this.content + PARAGRAPH_END);
        result.add(SECTION_END);

        return result.toArray(new String[result.size()]);
    }

    public String getContent() {
        return this.content;
    }

    public String getId() {
        return this.id;
    }
}
