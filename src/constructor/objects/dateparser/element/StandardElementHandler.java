package constructor.objects.dateparser.element;

import constructor.dom.ElementHandler;
import constructor.dom.loader.MappedElementHandler;

import java.util.Map;

import static util.CollectionUtils.newHashMap;

/**
 * Обработчик элемента dateParser.standard
 *
 * @author Igor Usenko
 *         Date: 13.06.2009
 */
public class StandardElementHandler extends MappedElementHandler {

    private static final String PATTERN_KEY = "pattern";
    private static final String MONTH_KEY = "months";

    public StandardElementHandler() {
        super();

        Map<String, ElementHandler> handlers = newHashMap();

        handlers.put(PATTERN_KEY, new StandardPatternElementHandler());
        handlers.put(MONTH_KEY, new StandardMonthsElementHandler());

        this.elementHandlers = handlers;
    }
}
