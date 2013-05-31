package work.unit.html.extractor;

import junit.framework.TestCase;
import util.html.HtmlTagExtractor;
import util.html.TagImage;

/**
 * @author Igor Usenko
 *         Date: 12.06.2009
 */
public class GetClosestOpenTagTest extends TestCase {

    public GetClosestOpenTagTest(final String _s) {
        super(_s);
    }

    // первоначальный тест

    public void testSmoke() {
        TagImage result = HtmlTagExtractor.getClosestOpenTag("0123<a>456<b>", 0);

        assertNotNull(result);
        assertEquals(4, result.getPosition());
        assertEquals("a", result.getTag());
        assertEquals("<a>", result.getImage());
        assertEquals("", result.getAttributes());
    }

    // тест с указанием стартовой позиции

    public void testPosition() {
        TagImage result = HtmlTagExtractor.getClosestOpenTag("0123<a>4</b>56<b>", 7);

        assertNotNull(result);
        assertEquals(14, result.getPosition());
        assertEquals("b", result.getTag());
        assertEquals("<b>", result.getImage());
        assertEquals("", result.getAttributes());
    }

    // тест с указанием стартовой позиции плюс тег с аттрибутами

    public void testPositionWithAttrs() {
        TagImage result = HtmlTagExtractor.getClosestOpenTag("0123<a>456<b ref=\"ref\">", 7);

        assertNotNull(result);
        assertEquals(10, result.getPosition());
        assertEquals("b", result.getTag());
        assertEquals("<b ref=\"ref\">", result.getImage());
        assertEquals(" ref=\"ref\"", result.getAttributes());
    }
}
