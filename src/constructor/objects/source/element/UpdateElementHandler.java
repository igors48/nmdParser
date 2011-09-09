package constructor.objects.source.element;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import constructor.dom.ElementHandler;
import constructor.dom.loader.AbstractElementHandler;
import constructor.objects.source.configuration.SourceConfiguration;
import org.w3c.dom.Node;
import util.Assert;

/**
 * Обработчик элемента source.update
 *
 * @author Igor Usenko
 *         Date: 26.03.2009
 */
public class UpdateElementHandler extends AbstractElementHandler {

    private static final String MODE_ATTRIBUTE_NAME = "mode";

    protected void handleElement(Node _node, Object _blank, ConstructorFactory _factory) throws ElementHandler.ElementHandlerException {
    }

    protected void handleAttribute(Node _node, Object _blank, ConstructorFactory _factory) throws AttributeHandler.AttributeHandlerException {
        Assert.notNull(_node, "Element is null.");
        Assert.notNull(_blank, "Blank is null.");
        Assert.notNull(_factory, "Factory is null.");

        String name = _node.getNodeName();
        String value = _node.getNodeValue();

        if (MODE_ATTRIBUTE_NAME.equals(name)) {
            ((SourceConfiguration) _blank).setAutoUpdate(value);
        } else {
            throw new AttributeHandler.AttributeHandlerException("Unexpected attribute [ " + name + " ] found in update element.");
        }
    }
}