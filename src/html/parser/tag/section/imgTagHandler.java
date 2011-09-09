package html.parser.tag.section;

import flowtext.DocumentBuilder;
import html.parser.TagHandler;
import html.parser.tag.HtmlTag;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 21.09.2008
 */
public class imgTagHandler implements TagHandler {

    public void handle(final HtmlTag _tag, final DocumentBuilder _builder) throws TagHandlerException {
        Assert.notNull(_tag);
        Assert.notNull(_builder);

        String url = _tag.getAttribute("src");

        if (url == null) {
            throw new TagHandlerException("Can`t find SRC attribute in IMG tag");
        }

        if (!url.isEmpty()) {
            _builder.insertResourceLink(url);
        }
    }
}
