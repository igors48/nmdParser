package html.parser.tag.section;

import flowtext.DocumentBuilder;
import html.parser.TagHandler;
import html.parser.tag.HtmlTag;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 20.09.2008
 */
public class noOpTagHandler implements TagHandler {

    public void handle(final HtmlTag _tag, final DocumentBuilder _builder) throws TagHandlerException {
        Assert.notNull(_tag);
        Assert.notNull(_builder);

    }
}
