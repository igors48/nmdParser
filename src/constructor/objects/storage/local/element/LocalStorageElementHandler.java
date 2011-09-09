package constructor.objects.storage.local.element;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import constructor.dom.ElementHandler;
import constructor.dom.loader.AbstractElementHandler;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import util.Assert;

/**
 * Обработчик элемента storage в XML файле
 *
 * @author Igor Usenko
 *         Date: 08.04.2009
 */
public class LocalStorageElementHandler extends AbstractElementHandler {

    private static final String ROOT_ELEMENT_ID = "root";

    private static final String DAYS_KEY = "days";

    protected void handleElement(final Node _node, final Object _blank, final ConstructorFactory _factory) throws ElementHandlerException {
        Assert.notNull(_node, "Element is null.");
        Assert.notNull(_blank, "Blank is null.");
        Assert.notNull(_factory, "Factory is null.");

        String name = _node.getNodeName();

        if (name == null) {
            throw new ElementHandlerException("Element name is null.");
        }

        if (name.equals(ROOT_ELEMENT_ID)) {
            ElementHandler handler = new RootElementHandler();
            handler.handle((Element) _node, _blank, _factory);
        }

    }

    protected void handleAttribute(final Node _node, final Object _blank, final ConstructorFactory _factory) throws AttributeHandler.AttributeHandlerException {
        Assert.notNull(_node, "Atribute is null.");
        Assert.notNull(_blank, "Blank is null.");
        Assert.notNull(_factory, "Factory is null.");

        String name = _node.getNodeName();
        String value = _node.getNodeValue();

        if (name == null) {
            throw new AttributeHandler.AttributeHandlerException("Attribute name is null.");
        }

        if (name.equals(DAYS_KEY)) {
            AttributeHandler handler = new DaysAttributeHandler();
            handler.handle(name, value, _blank, _factory);
        }
    }
}
