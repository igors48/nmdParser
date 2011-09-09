package html.parser.tag.format;

import flowtext.DocumentBuilder;
import html.parser.TagHandler;
import html.parser.tag.HtmlTag;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 19.09.2008
 */
public class SimpleTextFormatHandler implements TagHandler {

    public void handle(final HtmlTag _tag, final DocumentBuilder _builder) {
        Assert.notNull(_tag);
        Assert.notNull(_builder);

        _builder.pushFormat(TextFormat.SIMPLE);
    }
}
