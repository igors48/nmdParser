package constructor.objects.dateparser.core;

import timeservice.TimeService;
import util.Assert;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Парсер даты в строковом формате
 *
 * @author Igor Usenko
 *         Date: 09.01.2009
 */
public class DateParser {

    private final Map<Pattern, ParsingStrategy> parsers;

    public DateParser(final Map<Pattern, ParsingStrategy> _parsers) {
        Assert.notNull(_parsers, "Parser map is null.");

        this.parsers = _parsers;
    }

    public Date parse(final String _text, final int _hourCorrection, final TimeService _timeService) {
        Assert.isValidString(_text, "Text is not valid.");
        Assert.notNull(_timeService, "Time service is null.");

        Date result = null;

        for (Pattern pattern : this.parsers.keySet()) {

            Matcher matcher = pattern.matcher(_text);

            if (matcher.matches()) {
                result = this.parsers.get(pattern).parse(_text, _timeService);

                if (result != null) {
                    break;
                }
            }
        }

        if (result != null) {
            result = applyHourCorrection(result, _hourCorrection);
        }

        return result;
    }

    private Date applyHourCorrection(final Date _date, final int _hourCorrection) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(_date);
        calendar.add(Calendar.HOUR_OF_DAY, _hourCorrection);

        return calendar.getTime();
    }
}
