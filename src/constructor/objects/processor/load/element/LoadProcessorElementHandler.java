package constructor.objects.processor.load.element;

import constructor.dom.ElementHandler;
import constructor.dom.loader.MappedElementHandler;

import java.util.Map;

import static util.CollectionUtils.newHashMap;

/**
 * Обработчик элемента load в XML файле
 *
 * @author Igor Usenko
 *         Date: 20.03.2009
 */
public class LoadProcessorElementHandler extends MappedElementHandler {

    private static final String URL_KEY = "url";

    public LoadProcessorElementHandler() {
        super();

        Map<String, ElementHandler> handlers = newHashMap();

        handlers.put(URL_KEY, new UrlElementHandler());

        this.elementHandlers = handlers;
    }
}