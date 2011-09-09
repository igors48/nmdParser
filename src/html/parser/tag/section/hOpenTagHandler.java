package html.parser.tag.section;

import flowtext.DocumentBuilder;
import flowtext.Section;
import html.parser.TagHandler;
import html.parser.tag.HtmlTag;
import html.parser.tag.format.TextFormat;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 20.09.2008
 */
public class hOpenTagHandler implements TagHandler {

    public void handle(final HtmlTag _tag, final DocumentBuilder _builder) throws TagHandlerException {
        Assert.notNull(_tag);
        Assert.notNull(_builder);

        try {
            _builder.changeCurrentParagraph();
            _builder.insertEmptyLine();
            _builder.pushFormat(TextFormat.STRONG);
        } catch (Section.SectionException e) {
            throw new TagHandlerException(e);
        }
    }

}
