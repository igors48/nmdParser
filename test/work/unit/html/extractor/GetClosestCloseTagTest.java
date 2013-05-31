package work.unit.html.extractor;

import junit.framework.TestCase;
import util.html.HtmlTagExtractor;
import util.html.TagImage;

/**
 * @author Igor Usenko
 *         Date: 12.06.2009
 */
public class GetClosestCloseTagTest extends TestCase {

    public GetClosestCloseTagTest(final String _s) {
        super(_s);
    }

    // первоначальный тест

    public void testSmoke() {
        TagImage result = HtmlTagExtractor.getClosestCloseTag("0123<a>456</b>789", 0);

        assertNotNull(result);
        assertEquals(10, result.getPosition());
        assertEquals("b", result.getTag());
        assertEquals("</b>", result.getImage());
        assertEquals("", result.getAttributes());
    }

    // тест с указанием позиции

    public void testPosition() {
        TagImage result = HtmlTagExtractor.getClosestCloseTag("0123<a>456</b>78</c>9", 11);

        assertNotNull(result);
        assertEquals(16, result.getPosition());
        assertEquals("c", result.getTag());
        assertEquals("</c>", result.getImage());
        assertEquals("", result.getAttributes());
    }
}
