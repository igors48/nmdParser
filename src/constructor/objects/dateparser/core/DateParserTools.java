package constructor.objects.dateparser.core;

import util.Assert;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.regex.Pattern;

import static util.CollectionUtils.newHashMap;

/**
 * Различные утилиты для парсера даты
 *
 * @author Igor Usenko
 *         Date: 11.01.2009
 */
public class DateParserTools {

    public static int getYear(final Date _date) {
        Assert.notNull(_date, "Date is null.");

        return getField(_date, Calendar.YEAR);
    }

    public static int getMonth(final Date _date) {
        Assert.notNull(_date, "Date is null.");

        return getField(_date, Calendar.MONTH);
    }

    public static int getDay(final Date _date) {
        Assert.notNull(_date, "Date is null.");

        return getField(_date, Calendar.DAY_OF_MONTH);
    }

    public static int getHour(final Date _date) {
        Assert.notNull(_date, "Date is null.");

        return getField(_date, Calendar.HOUR_OF_DAY);
    }

    public static int getMinute(final Date _date) {
        Assert.notNull(_date, "Date is null.");

        return getField(_date, Calendar.MINUTE);
    }

    public static int getSecond(final Date _date) {
        Assert.notNull(_date, "Date is null.");

        return getField(_date, Calendar.SECOND);
    }

    public static int getField(final Date _date, final int _fieldCode) {
        Assert.notNull(_date, "Date is null.");

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(_date);

        return calendar.get(_fieldCode);
    }

    public static Date createDate(final int _year, final int _month, final int _day, final int _hour, final int _minute, final int _second) {
        Calendar calendar = new GregorianCalendar(_year, _month, _day, _hour, _minute, _second);

        return calendar.getTime();
    }

    public static Map<Pattern, ParsingStrategy> createParsers() {
        Map<Pattern, ParsingStrategy> result = newHashMap();

        result.put(Pattern.compile(".*?less.*?than.*?a.*?minute.*?ago.*?", Pattern.CASE_INSENSITIVE), new LessThanMinuteStrategy());
        result.put(Pattern.compile(".*?minutes.*?ago.*?", Pattern.CASE_INSENSITIVE), new MinutesAgoStrategy());
        result.put(Pattern.compile(".*?today.*?", Pattern.CASE_INSENSITIVE), new TodayStrategy());
        result.put(Pattern.compile(".*?yesterday.*?", Pattern.CASE_INSENSITIVE), new YesterdayStrategy());

        String pattern = "MMMM dd, yyyy, h:mm a";

        DateFormatSymbols symbols = new DateFormatSymbols();
        String[] monthes = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        symbols.setMonths(monthes);

        result.put(Pattern.compile(".*", Pattern.CASE_INSENSITIVE), new StandardStrategy(pattern, symbols));

        return result;
    }

    public static String removeEnglishDayPostfixes(final String _text) {
        Assert.isValidString(_text, "Text is not valid.");

        String text = _text.replace("st,", ",");
        text = text.replace("rd,", ",");
        text = text.replace("nd,", ",");
        text = text.replace("th,", ",");

        return text;
    }
}
