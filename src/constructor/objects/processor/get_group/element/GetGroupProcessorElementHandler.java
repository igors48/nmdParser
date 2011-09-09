package constructor.objects.processor.get_group.element;

import constructor.dom.ElementHandler;
import constructor.dom.loader.MappedElementHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Обработчик элемента getGroup в XML файле
 *
 * @author Igor Usenko
 *         Date: 20.03.2009
 */
public class GetGroupProcessorElementHandler extends MappedElementHandler {

    private static final String OCCURRENCE_KEY = "occurrence";
    private static final String PATTERN_KEY = "pattern";
    private static final String GROUP_KEY = "group";

    public GetGroupProcessorElementHandler() {
        super();

        Map<String, ElementHandler> handlers = new HashMap<String, ElementHandler>();
        handlers.put(OCCURRENCE_KEY, new OccurrenceElementHandler());
        handlers.put(PATTERN_KEY, new PatternElementHandler());
        handlers.put(GROUP_KEY, new GroupElementHandler());

        this.elementHandlers = handlers;
    }
}
