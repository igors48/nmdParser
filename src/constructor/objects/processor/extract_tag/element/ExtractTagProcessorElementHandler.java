package constructor.objects.processor.extract_tag.element;

import constructor.dom.ElementHandler;
import constructor.dom.loader.MappedElementHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Обработчик элемента extractTag в XML файле
 *
 * @author Igor Usenko
 *         Date: 29.04.2009
 */
public class ExtractTagProcessorElementHandler extends MappedElementHandler {

    private static final String PATTERN_KEY = "pattern";
    private static final String OCCURRENCE_KEY = "occurrence";

    public ExtractTagProcessorElementHandler() {
        super();

        Map<String, ElementHandler> handlers = new HashMap<String, ElementHandler>();
        handlers.put(PATTERN_KEY, new PatternElementHandler());
        handlers.put(OCCURRENCE_KEY, new OccurrenceElementHandler());

        this.elementHandlers = handlers;
    }
}