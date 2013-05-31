package converter.format.fb2.footnotes;

import converter.format.fb2.Fb2Tools;
import converter.format.fb2.Stringable;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 07.01.2009
 */
public class Fb2FootNoteLink implements Stringable {

    private static final String PREFIX = "<a xlink:href=\"#note";
    private static final String SUFFIX = "\" type=\"note\">";
    private static final String POSTFIX = "</a>";

    private final String id;

    public Fb2FootNoteLink(final String _content) {
        Assert.isValidString(_content, "Content is not valid");

        this.id = Fb2Tools.processEntities(_content);
    }

    public String[] getStrings() {
        StringBuffer result = new StringBuffer(PREFIX);

        result.append(this.id);
        result.append(SUFFIX);
        result.append(this.id);
        result.append(POSTFIX);

        return new String[]{result.toString()};
    }

}
