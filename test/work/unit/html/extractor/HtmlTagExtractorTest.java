package work.unit.html.extractor;

import junit.framework.TestCase;

import java.util.List;

import util.html.HtmlTagBounds;
import util.html.HtmlTagExtractor;

/**
 * @author Igor Usenko
 *         Date: 11.03.2009
 */
public class HtmlTagExtractorTest extends TestCase {

    public static String FIXTURE = "trashtrashtrash<td colspan=\"2\"><span class=\"postbody\">asdfrg</span>aaas</td>opaopaopa<td colspan=\"2\"><span class=\"postbody\">asdfrg</span>aaQQQas</td>";
    public static String FIXTURE_BOUNDARY = "trashtrashtrash<td colspan=\"2\"><span class=\"postbody\">asdfrg</span>aaas";

    public HtmlTagExtractorTest(String s) {
        super(s);
    }

    // простой тест на правильность работы
    public void testSmoke(){
        List<HtmlTagBounds> result = HtmlTagExtractor.extractTag("<td colspan=\"2\">", FIXTURE);
        
        assertEquals(2, result.size());

        String test = FIXTURE.substring(result.get(0).getContentStart(), result.get(0).getEnd() - result.get(0).getClosingTagLength());
        assertEquals("<span class=\"postbody\">asdfrg</span>aaas", test);

        test = FIXTURE.substring(result.get(1).getContentStart(), result.get(1).getEnd() - result.get(1).getClosingTagLength());
        assertEquals("<span class=\"postbody\">asdfrg</span>aaQQQas", test);
    }

    // тест граничной ситуации
    public void testBoundary(){
        List<HtmlTagBounds> result = HtmlTagExtractor.extractTag("<td colspan=\"2\">", FIXTURE_BOUNDARY);

        assertEquals(1, result.size());

        String test = FIXTURE_BOUNDARY.substring(result.get(0).getContentStart(), result.get(0).getEnd() - result.get(0).getClosingTagLength());
        assertEquals("<span class=\"postbody\">asdfrg</span>aaas", test);
    }
}
