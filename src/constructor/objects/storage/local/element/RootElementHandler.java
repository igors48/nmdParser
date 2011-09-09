package constructor.objects.storage.local.element;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import constructor.dom.loader.AbstractElementHandler;
import constructor.objects.storage.local.configuration.LocalStorageConfiguration;
import org.w3c.dom.Node;
import util.Assert;

/**
 * Обработчик элемента storage.root
 *
 * @author Igor Usenko
 *         Date: 08.04.2009
 */
public class RootElementHandler extends AbstractElementHandler {

    protected void handleElement(Node _node, Object _blank, ConstructorFactory _factory) throws ElementHandlerException {
        Assert.notNull(_node, "Element is null.");
        Assert.notNull(_blank, "Blank is null.");
        Assert.notNull(_factory, "Factory is null.");

        String value = _node.getNodeValue();
        ((LocalStorageConfiguration) _blank).setRoot(value);
    }

    protected void handleAttribute(Node _node, Object _blank, ConstructorFactory _factory) throws AttributeHandler.AttributeHandlerException {
    }
}
