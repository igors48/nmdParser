package constructor.objects.processor.load.element;

import constructor.dom.ElementHandler;
import constructor.dom.loader.MappedElementHandler;

import java.util.HashMap;
import java.util.Map;

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

        Map<String, ElementHandler> handlers = new HashMap<String, ElementHandler>();
        handlers.put(URL_KEY, new UrlElementHandler());

        this.elementHandlers = handlers;
    }
}