package html.parser.tag.section;

import flowtext.DocumentBuilder;
import html.parser.TagHandler;
import html.parser.tag.HtmlTag;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 07.01.2009
 */
public class aOpenTagHandler implements TagHandler {

    public void handle(final HtmlTag _tag, final DocumentBuilder _builder) {
        Assert.notNull(_tag);
        Assert.notNull(_builder);

        String data = _tag.getAttribute("href");

        if (data != null) {

            //посвящено баге на 3DNews 11 may 2009 - ссылка действительно была пустой
            if (data.isEmpty()) {
                _builder.storeFootNote("Empty link.");
            } else {
                _builder.storeFootNote(data);
            }
        }
    }
}