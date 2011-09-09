package constructor.objects.dateparser.core;

import timeservice.TimeService;
import util.Assert;

import java.util.Date;

/**
 * Стратегия "меньше минуты"
 *
 * @author Igor Usenko
 *         Date: 31.01.2009
 */
public class LessThanMinuteStrategy implements ParsingStrategy {

    public Date parse(final String _text, final TimeService _timeService) {
        Assert.isValidString(_text, "Text is not valid.");
        Assert.notNull(_timeService, "Time service is null.");

        return new Date(_timeService.getCurrentTime());
    }
}
