package constructor.objects.dateparser.core;

import util.Assert;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Парсер строкового представления времени
 *
 * @author Igor Usenko
 *         Date: 11.01.2009
 */
public class TimeParser {

    private static final Pattern TIME_PATTERN = Pattern.compile(".*?(\\d{1,2}):(\\d{1,2})\\s*(pm|am)*", Pattern.CASE_INSENSITIVE);
    private static final int HOUR_GROUP = 1;
    private static final int MINUTES_GROUP = 2;
    private static final int AMPM_GROUP = 3;

    public static Date convert(final String _data) {
        Assert.isValidString(_data, "Data is not valid.");

        Date result = null;
        Matcher matcher = TIME_PATTERN.matcher(_data);

        if (matcher.find()) {
            String hoursString = matcher.group(HOUR_GROUP);
            String minutesString = matcher.group(MINUTES_GROUP);
            String ampmString = matcher.group(AMPM_GROUP);

            if (hoursString.length() != 0 && minutesString.length() != 0) {
                result = createTime(hoursString, minutesString, ampmString);
            }
        }

        return result;
    }

    private static Date createTime(final String _hoursString, final String _minutesString, final String _ampmString) {
        int hours = new Integer(_hoursString);
        int minutes = new Integer(_minutesString);

        if ("pm".equalsIgnoreCase(_ampmString)) {
            hours += 12;
        }

        if ("am".equalsIgnoreCase(_ampmString) && hours == 12) {
            hours = 0;
        }

        Calendar calendar = new GregorianCalendar(0, 0, 0, hours, minutes);

        return calendar.getTime();
    }

    private TimeParser() {
        // empty
    }
}
