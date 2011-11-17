package work.unit.dateparsing;

import constructor.objects.dateparser.core.DateParserTools;
import constructor.objects.dateparser.core.TimeParser;
import junit.framework.TestCase;

import java.util.Date;

/**
 * @author Igor Usenko
 *         Date: 11.01.2009
 */
public class TimeConverterTest extends TestCase {

    public TimeConverterTest(String s) {
        super(s);
    }

    public void test24Format() {
        Date result = TimeParser.convert("bla 13:48 bla bla ");

        assertEquals(13, DateParserTools.getHour(result));
        assertEquals(48, DateParserTools.getMinute(result));
    }

    public void test12FormatPm() {
        Date result = TimeParser.convert("bla 1:48 pm bla bla ");

        assertEquals(13, DateParserTools.getHour(result));
        assertEquals(48, DateParserTools.getMinute(result));
    }

    public void test12FormatAm() {
        Date result = TimeParser.convert("bla 1:48 am bla bla ");

        assertEquals(1, DateParserTools.getHour(result));
        assertEquals(48, DateParserTools.getMinute(result));
    }

    public void test12FormatAm0Hour() {
        Date result = TimeParser.convert("bla 12:00 am bla bla ");

        assertEquals(0, DateParserTools.getHour(result));
        assertEquals(0, DateParserTools.getMinute(result));
    }

}
