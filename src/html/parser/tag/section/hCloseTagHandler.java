package html.parser.tag.section;

import flowtext.DocumentBuilder;
import flowtext.Section;
import html.parser.TagHandler;
import html.parser.tag.HtmlTag;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 20.09.2008
 */
public class hCloseTagHandler implements TagHandler {

    public void handle(final HtmlTag _tag, final DocumentBuilder _builder) throws TagHandlerException {
        Assert.notNull(_tag);
        Assert.notNull(_builder);

        try {
            _builder.popFormat();
            _builder.changeCurrentParagraph();
        } catch (Section.SectionException e) {
            throw new TagHandlerException(e);
        }
    }
}