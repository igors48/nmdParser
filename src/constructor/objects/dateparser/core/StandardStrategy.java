package constructor.objects.dateparser.core;

import timeservice.TimeService;
import util.Assert;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Стандартная стратегия
 *
 * @author Igor Usenko
 *         Date: 11.01.2009
 */
public class StandardStrategy implements ParsingStrategy {

    private final DateFormat format;

    public StandardStrategy(final String _pattern, final DateFormatSymbols _symbols) {
        Assert.isValidString(_pattern);
        Assert.notNull(_symbols);

        this.format = new SimpleDateFormat(_pattern, _symbols);
    }

    public Date parse(final String _text, final TimeService _timeService) {
        Assert.isValidString(_text, "Text is not valid.");
        Assert.notNull(_timeService, "Time service is null.");

        return this.format.parse(DateParserTools.removeEnglishDayPostfixes(_text), new ParsePosition(0));
    }
}
