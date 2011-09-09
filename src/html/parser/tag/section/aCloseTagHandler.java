package html.parser.tag.section;

import flowtext.DocumentBuilder;
import html.parser.TagHandler;
import html.parser.tag.HtmlTag;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 07.01.2009
 */
public class aCloseTagHandler implements TagHandler {

    public void handle(final HtmlTag _tag, final DocumentBuilder _builder) {
        Assert.notNull(_tag);
        Assert.notNull(_builder);

        _builder.insertStoredFootNote();
    }
}