package constructor.objects.dateparser.core;

import timeservice.TimeService;

import java.util.Date;

/**
 * @author Igor Usenko
 *         Date: 09.01.2009
 */
public interface ParsingStrategy {
    Date parse(String _text, TimeService _timeService);
}
