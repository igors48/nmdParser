package constructor.objects.dateparser.core;

import timeservice.TimeService;
import util.Assert;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Стратегия "секунд от начала Epoch"
 *
 * @author Igor Usenko
 *         Date: 13.06.2009
 */
public class SecondsFromEpochStrategy implements ParsingStrategy {

    public static final String PATTERN_STRING = ".*?(\\d+).*";

    private static final Pattern PATTERN = Pattern.compile(PATTERN_STRING);

    private static final int GROUP = 1;
    private static final int MSECONDS = 1000;

    public Date parse(final String _text, final TimeService _timeService) {
        Assert.isValidString(_text, "Text is not valid.");
        Assert.notNull(_timeService, "Time service is null.");

        Date result = null;

        Matcher matcher = PATTERN.matcher(_text);

        if (matcher.find()) {
            String value = matcher.group(GROUP);

            if (!value.isEmpty()) {
                int seconds = Integer.valueOf(value);
                Calendar calendar = new GregorianCalendar();
                calendar.set(1969, 11, 31, 23, 0, 0);
                calendar.add(Calendar.SECOND, seconds);

                result = calendar.getTime();
            }

        }

        return result;
    }
}
