package constructor.objects.dateparser.element;

import constructor.dom.ElementHandler;
import constructor.dom.loader.MappedElementHandler;

import java.util.Map;

import static util.CollectionUtils.newHashMap;

/**
 * Обработчик элемента dateParser в XML файле
 *
 * @author Igor Usenko
 *         Date: 13.06.2009
 */
public class DateParserElementHandler extends MappedElementHandler {

    private static final String SECONDS_FROM_EPOCH_KEY = "secondsFromEpoch";
    private static final String LESS_THAN_MINUTE_KEY = "lessThanMinute";
    private static final String MINUTES_AGO_KEY = "minutesAgo";
    private static final String TODAY_KEY = "today";
    private static final String YESTERDAY_KEY = "yesterday";
    private static final String STANDARD_KEY = "standard";

    public DateParserElementHandler() {
        super();

        Map<String, ElementHandler> handlers = newHashMap();

        handlers.put(SECONDS_FROM_EPOCH_KEY, new SecondsFromEpochElementHandler());
        handlers.put(LESS_THAN_MINUTE_KEY, new LessThanMinuteElementHandler());
        handlers.put(MINUTES_AGO_KEY, new MinutesAgoElementHandler());
        handlers.put(TODAY_KEY, new TodayElementHandler());
        handlers.put(YESTERDAY_KEY, new YesterdayElementHandler());
        handlers.put(STANDARD_KEY, new StandardElementHandler());

        this.elementHandlers = handlers;
    }
}
