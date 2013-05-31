package constructor.objects.simpler.element;

import constructor.dom.ElementHandler;
import constructor.dom.loader.MappedElementHandler;

import java.util.Map;

import static util.CollectionUtils.newHashMap;

/**
 * @author Igor Usenko
 *         Date: 06.07.2010
 */
public class SimplerElementHandler extends MappedElementHandler {

    private static final String XPATH_KEY = "xPath";
    private static final String CONTENT_FILTER_KEY = "content-filter";

    public SimplerElementHandler() {
        super();

        Map<String, ElementHandler> handlers = newHashMap();

        handlers.put(XPATH_KEY, new XPathElementHandler());
        handlers.put(CONTENT_FILTER_KEY, new ContentFilterElementHandler());

        this.elementHandlers = handlers;
    }
}
