package converter.format.fb2.resource;

import converter.format.fb2.Stringable;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 06.09.2008
 */
public class Fb2ResourceLink implements Stringable {

    private static final String RESOURCE_START = "<image l:href=\"#";
    private static final String RESOURCE_END = "\"/>";

    private final String tag;

    public Fb2ResourceLink(final String _tag) {
        Assert.isValidString(_tag, "Tag is not valid");

        this.tag = _tag;
    }

    public String getTag() {
        return this.tag;
    }

    public String[] getStrings() {
        StringBuffer result = new StringBuffer(RESOURCE_START);

        result.append(this.tag);
        result.append(RESOURCE_END);

        return new String[]{result.toString()};
    }
}
