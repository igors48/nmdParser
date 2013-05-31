package work.unit.dateparsing;

import constructor.objects.dateparser.core.DateParserTools;
import junit.framework.TestCase;

/**
 * @author Igor Usenko
 *         Date: 11.01.2009
 */
public class DateConverterUtilsTest extends TestCase {

    public DateConverterUtilsTest(String s) {
        super(s);
    }

    public void testRemovePrefixSt() {
        String result = DateParserTools.removeEnglishDayPostfixes("th January 1st, 2009, 7:50 pm th");

        assertEquals("th January 1, 2009, 7:50 pm th", result);
    }

    public void testRemovePrefixRd() {
        String result = DateParserTools.removeEnglishDayPostfixes("th January 3rd, 2009, 7:50 pm th");

        assertEquals("th January 3, 2009, 7:50 pm th", result);
    }

    public void testRemovePrefixNd() {
        String result = DateParserTools.removeEnglishDayPostfixes("th January 2nd, 2009, 7:50 pm th");

        assertEquals("th January 2, 2009, 7:50 pm th", result);
    }

    public void testRemovePrefixTh() {
        String result = DateParserTools.removeEnglishDayPostfixes("th January 6th, 2009, 7:50 pm th");

        assertEquals("th January 6, 2009, 7:50 pm th", result);
    }
}
