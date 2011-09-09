package constructor.objects.output.element;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import constructor.dom.loader.AbstractElementHandler;
import constructor.objects.output.configuration.OutputConfiguration;
import org.w3c.dom.Node;
import util.Assert;

/**
 * Обработчик элемента output.many-to-one
 *
 * @author Igor Usenko
 *         Date: 26.04.2009
 */
public class ManyToOneElementHandler extends AbstractElementHandler {

    private static final String NAME_KEY = "name";

    protected void handleElement(Node _node, Object _blank, ConstructorFactory _factory) throws ElementHandlerException {
    }

    protected void handleAttribute(Node _node, Object _blank, ConstructorFactory _factory) throws AttributeHandler.AttributeHandlerException {
        Assert.notNull(_node, "Atribute is null.");
        Assert.notNull(_blank, "Blank is null.");
        Assert.notNull(_factory, "Factory is null.");

        String name = _node.getNodeName();
        String value = _node.getNodeValue();

        if (name == null) {
            throw new AttributeHandler.AttributeHandlerException("Attribute name is null.");
        }

        if (name.equals(NAME_KEY)) {
            ((OutputConfiguration) _blank).setName(value);
        }
    }
}
