package work.unit.dateparsing;

import constructor.objects.dateparser.core.DateParser;
import constructor.objects.dateparser.core.DateParserTools;
import junit.framework.TestCase;
import timeservice.StillTimeService;
import timeservice.TimeService;

import java.util.Date;

/**
 * @author Igor Usenko
 *         Date: 09.01.2009
 */
public class DateConverterTest extends TestCase {

    public DateConverterTest(String s) {
        super(s);
    }

    //StillTimeService(int _year, int _month, int _day, int _hour, int _minute, int _second)

    public void testLessThanMinuteAgo() {
        DateParser parser = new DateParser(DateParserTools.createParsers());

        Date origin = DateParserTools.createDate(2008, 0, 12, 16, 0, 0);
        TimeService timeService = new StillTimeService(origin);

        Date date = parser.parse("less than a minute  ago", 0, timeService);

        assertEquals(2008, DateParserTools.getYear(date));
        assertEquals(0, DateParserTools.getMonth(date));
        assertEquals(12, DateParserTools.getDay(date));
        assertEquals(16, DateParserTools.getHour(date));
        assertEquals(0, DateParserTools.getMinute(date));
        assertEquals(0, DateParserTools.getSecond(date));
    }

    public void testMinutesAgo() {
        DateParser parser = new DateParser(DateParserTools.createParsers());

        Date origin = DateParserTools.createDate(2008, 0, 12, 16, 0, 0);
        TimeService timeService = new StillTimeService(origin);

        Date date = parser.parse("16 minutes ago", 1, timeService);

        assertEquals(2008, DateParserTools.getYear(date));
        assertEquals(0, DateParserTools.getMonth(date));
        assertEquals(12, DateParserTools.getDay(date));
        assertEquals(16, DateParserTools.getHour(date));
        assertEquals(44, DateParserTools.getMinute(date));
        assertEquals(0, DateParserTools.getSecond(date));
    }

    public void testToday() {
        DateParser parser = new DateParser(DateParserTools.createParsers());

        Date origin = DateParserTools.createDate(2008, 0, 12, 16, 0, 0);
        TimeService timeService = new StillTimeService(origin);

        Date date = parser.parse("Today, 3:13 am", -1, timeService);

        assertEquals(2008, DateParserTools.getYear(date));
        assertEquals(0, DateParserTools.getMonth(date));
        assertEquals(12, DateParserTools.getDay(date));
        assertEquals(2, DateParserTools.getHour(date));
        assertEquals(13, DateParserTools.getMinute(date));
        assertEquals(0, DateParserTools.getSecond(date));
    }

    public void testYesterday() {
        DateParser parser = new DateParser(DateParserTools.createParsers());

        Date origin = DateParserTools.createDate(2008, 0, 12, 16, 0, 0);
        TimeService timeService = new StillTimeService(origin);

        Date date = parser.parse("Yesterday, 9:22 pm", 0, timeService);

        assertEquals(2008, DateParserTools.getYear(date));
        assertEquals(0, DateParserTools.getMonth(date));
        assertEquals(11, DateParserTools.getDay(date));
        assertEquals(21, DateParserTools.getHour(date));
        assertEquals(22, DateParserTools.getMinute(date));
        assertEquals(0, DateParserTools.getSecond(date));
    }

    public void testYesterdayMonth() {
        DateParser parser = new DateParser(DateParserTools.createParsers());

        Date origin = DateParserTools.createDate(2008, 5, 1, 16, 0, 0);
        TimeService timeService = new StillTimeService(origin);

        Date date = parser.parse("Yesterday, 9:22 pm", 0, timeService);

        assertEquals(2008, DateParserTools.getYear(date));
        assertEquals(4, DateParserTools.getMonth(date));
        assertEquals(31, DateParserTools.getDay(date));
        assertEquals(21, DateParserTools.getHour(date));
        assertEquals(22, DateParserTools.getMinute(date));
        assertEquals(0, DateParserTools.getSecond(date));
    }

    public void testYesterdayYear() {
        DateParser parser = new DateParser(DateParserTools.createParsers());

        Date origin = DateParserTools.createDate(2008, 0, 1, 16, 0, 0);
        TimeService timeService = new StillTimeService(origin);

        Date date = parser.parse("Yesterday, 9:22 pm", 0, timeService);

        assertEquals(2007, DateParserTools.getYear(date));
        assertEquals(11, DateParserTools.getMonth(date));
        assertEquals(31, DateParserTools.getDay(date));
        assertEquals(21, DateParserTools.getHour(date));
        assertEquals(22, DateParserTools.getMinute(date));
        assertEquals(0, DateParserTools.getSecond(date));
    }

    public void testStandard01() {
        DateParser parser = new DateParser(DateParserTools.createParsers());

        Date origin = DateParserTools.createDate(2008, 0, 1, 16, 0, 0);
        TimeService timeService = new StillTimeService(origin);

        Date date = parser.parse("January 8, 2009, 5:41 am", 0, timeService);

        assertEquals(2009, DateParserTools.getYear(date));
        assertEquals(0, DateParserTools.getMonth(date));
        assertEquals(8, DateParserTools.getDay(date));
        assertEquals(5, DateParserTools.getHour(date));
        assertEquals(41, DateParserTools.getMinute(date));
        assertEquals(0, DateParserTools.getSecond(date));
    }

    public void testStandard02() {
        DateParser parser = new DateParser(DateParserTools.createParsers());

        Date origin = DateParserTools.createDate(2008, 0, 1, 16, 0, 0);
        TimeService timeService = new StillTimeService(origin);

        Date date = parser.parse("January 28th, 2009, 11:38 pm", 0, timeService);

        assertEquals(2009, DateParserTools.getYear(date));
        assertEquals(0, DateParserTools.getMonth(date));
        assertEquals(28, DateParserTools.getDay(date));
        assertEquals(23, DateParserTools.getHour(date));
        assertEquals(38, DateParserTools.getMinute(date));
        assertEquals(0, DateParserTools.getSecond(date));
    }

}
