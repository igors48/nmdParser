package constructor.objects.dateparser.core;

import timeservice.TimeService;
import util.Assert;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Стратегия "минут назад"
 *
 * @author Igor Usenko
 *         Date: 09.01.2009
 */
public class MinutesAgoStrategy implements ParsingStrategy {

    private static final Pattern PATTERN = Pattern.compile("(\\d{1,2})");
    private static final int GROUP = 1;

    public Date parse(final String _text, final TimeService _timeService) {
        Assert.isValidString(_text, "Text is not valid.");
        Assert.notNull(_timeService, "Time service is null.");

        Date result = null;

        Matcher matcher = PATTERN.matcher(_text);

        if (matcher.find()) {
            String value = matcher.group(GROUP);

            if (value.length() > 0) {
                int minutes = new Integer(value);
                Calendar calendar = new GregorianCalendar();
                calendar.setTimeInMillis(_timeService.getCurrentTime());
                calendar.add(Calendar.MINUTE, -minutes);

                result = calendar.getTime();
            }

        }

        return result;
    }
}
