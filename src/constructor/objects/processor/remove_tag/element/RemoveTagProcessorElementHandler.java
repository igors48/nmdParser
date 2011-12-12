package constructor.objects.processor.remove_tag.element;

import constructor.dom.ElementHandler;
import constructor.dom.loader.MappedElementHandler;

import java.util.Map;

import static util.CollectionUtils.newHashMap;

/**
 * ���������� �������� removeTag � XML �����
 *
 * @author Igor Usenko
 *         Date: 20.05.2009
 */
public class RemoveTagProcessorElementHandler extends MappedElementHandler {

    private static final String PATTERN_KEY = "pattern";
    private static final String OCCURRENCE_KEY = "occurrence";

    public RemoveTagProcessorElementHandler() {
        super();

        Map<String, ElementHandler> handlers = newHashMap();

        handlers.put(PATTERN_KEY, new PatternElementHandler());
        handlers.put(OCCURRENCE_KEY, new OccurrenceElementHandler());

        this.elementHandlers = handlers;
    }
}