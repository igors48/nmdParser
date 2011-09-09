package constructor.objects.source.element;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import constructor.dom.ElementHandler;
import constructor.dom.loader.AbstractElementHandler;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import util.Assert;

/**
 * Обработчик элемента source в XML файле
 *
 * @author Igor Usenko
 *         Date: 21.03.2009
 */
public class SourceElementHandler extends AbstractElementHandler {
    private static final String RSS_ELEMENT_ID = "rss";
    private static final String URL_ELEMENT_ID = "url";
    private static final String CUSTOM_ELEMENT_ID = "custom";
    private static final String STORE_ELEMENT_ID = "store";
    private static final String UPDATE_ELEMENT_ID = "update";
    private static final String PROCESSOR_ELEMENT_ID = "processor";

    protected void handleElement(Node _node, Object _blank, ConstructorFactory _factory) throws ElementHandlerException {
        Assert.notNull(_node, "Element is null.");
        Assert.notNull(_blank, "Blank is null.");
        Assert.notNull(_factory, "Factory is null.");

        String name = _node.getNodeName();

        if (name == null) {
            throw new ElementHandlerException("Element name is null.");
        }

        if (name.equals(RSS_ELEMENT_ID)) {
            ElementHandler handler = new RssElementHandler();
            handler.handle((Element) _node, _blank, _factory);
        }

        if (name.equals(URL_ELEMENT_ID)) {
            ElementHandler handler = new UrlElementHandler();
            handler.handle((Element) _node, _blank, _factory);
        }

        if (name.equals(CUSTOM_ELEMENT_ID)) {
            ElementHandler handler = new CustomElementHandler();
            handler.handle((Element) _node, _blank, _factory);
        }

        if (name.equals(STORE_ELEMENT_ID)) {
            ElementHandler handler = new StoreElementHandler();
            handler.handle((Element) _node, _blank, _factory);
        }

        if (name.equals(UPDATE_ELEMENT_ID)) {
            ElementHandler handler = new UpdateElementHandler();
            handler.handle((Element) _node, _blank, _factory);
        }

        if (name.equals(PROCESSOR_ELEMENT_ID)) {
            ElementHandler handler = new ProcessorElementHandler();
            handler.handle((Element) _node, _blank, _factory);
        }

    }

    protected void handleAttribute(Node _node, Object _blank, ConstructorFactory _factory) throws AttributeHandler.AttributeHandlerException {
    }
}
