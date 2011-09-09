package constructor.objects.snippet.element;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import constructor.dom.loader.AbstractElementHandler;
import constructor.objects.snippet.configuration.SnippetConfiguration;
import org.w3c.dom.Node;
import util.Assert;

/**
 * Обработчик элемента snippet.storage в XML файле
 *
 * @author Igor Usenko
 *         Date: 11.12.2009
 */
public class StorageElementHandler extends AbstractElementHandler {

    private static final String NAME_KEY = "name";

    protected void handleElement(final Node _node, final Object _blank, final ConstructorFactory _factory) throws ElementHandlerException {
        // empty
    }

    protected void handleAttribute(final Node _node, final Object _blank, final ConstructorFactory _factory) throws AttributeHandler.AttributeHandlerException {
        Assert.notNull(_node, "Attribute is null.");
        Assert.notNull(_blank, "Blank is null.");
        Assert.notNull(_factory, "Factory is null.");

        String name = _node.getNodeName();
        String value = _node.getNodeValue();

        if (name == null) {
            throw new AttributeHandler.AttributeHandlerException("Attribute name is null.");
        }

        if (name.equals(NAME_KEY)) {
            ((SnippetConfiguration) _blank).setStorage(value);
        }
    }
}