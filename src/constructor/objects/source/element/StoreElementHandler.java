package constructor.objects.source.element;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import constructor.dom.ElementHandler;
import constructor.dom.loader.AbstractElementHandler;
import constructor.objects.source.configuration.SourceConfiguration;
import org.w3c.dom.Node;
import util.Assert;

/**
 * Обработчик элемента source.store
 *
 * @author Igor Usenko
 *         Date: 26.03.2009
 */
public class StoreElementHandler extends AbstractElementHandler {

    private static final String DAYS_ATTRIBUTE_NAME = "days";

    protected void handleElement(Node _node, Object _blank, ConstructorFactory _factory) throws ElementHandler.ElementHandlerException {
    }

    protected void handleAttribute(Node _node, Object _blank, ConstructorFactory _factory) throws AttributeHandler.AttributeHandlerException {
        Assert.notNull(_node, "Element is null.");
        Assert.notNull(_blank, "Blank is null.");
        Assert.notNull(_factory, "Factory is null.");

        String name = _node.getNodeName();
        String value = _node.getNodeValue();

        if (DAYS_ATTRIBUTE_NAME.equals(name)) {
            ((SourceConfiguration) _blank).setStoreDays(value);
        } else {
            throw new AttributeHandler.AttributeHandlerException("Unexpected attribute [ " + name + " ] found in store element.");
        }
    }
}
