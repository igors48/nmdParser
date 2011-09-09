package flowtext;

import util.Assert;
import util.html.HtmlUtils;

/**
 * @author Igor Usenko
 *         Date: 10.10.2008
 */
public final class FlowTextUtil {

    public static boolean isText(final FlowTextObject _object) {
        Assert.notNull(_object, "Object is null");

        return (_object.getType() == FlowTextType.CODE_TEXT ||
                _object.getType() == FlowTextType.EMPHASIS_TEXT ||
                _object.getType() == FlowTextType.SIMPLE_TEXT ||
                _object.getType() == FlowTextType.STRONG_TEXT);
    }

    public static Title createTitle(final String _content) {
        Assert.isValidString(_content, "Title content is not valid");
        Title result = new Title();

        Paragraph paragraph = new Paragraph();
        paragraph.insertStrongText(HtmlUtils.cleanUpTags(_content));

        result.insertParagraph(paragraph);

        return result;
    }

    private FlowTextUtil() {
        //empty
    }
}
