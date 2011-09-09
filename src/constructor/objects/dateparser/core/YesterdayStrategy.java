package constructor.objects.dateparser.core;

import timeservice.TimeService;
import util.Assert;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Стратегия "вчера"
 *
 * @author Igor Usenko
 *         Date: 11.01.2009
 */
public class YesterdayStrategy implements ParsingStrategy {

    public Date parse(final String _text, final TimeService _timeService) {
        Assert.isValidString(_text, "Text is not valid.");
        Assert.notNull(_timeService, "Time service is null.");

        Date result = TimeParser.convert(_text);

        if (result != null) {
            Calendar calendar = new GregorianCalendar();
            calendar.setTimeInMillis(_timeService.getCurrentTime());
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            calendar.set(Calendar.HOUR_OF_DAY, DateParserTools.getHour(result));
            calendar.set(Calendar.MINUTE, DateParserTools.getMinute(result));
            calendar.set(Calendar.SECOND, 0);

            result = calendar.getTime();
        }

        return result;
    }
}
