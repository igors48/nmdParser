package constructor.objects.simpler.element;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import constructor.dom.loader.AbstractElementHandler;
import constructor.objects.simpler.configuration.SimplerConfiguration;
import org.w3c.dom.Node;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 21.03.2009
 */
public class XPathElementHandler extends AbstractElementHandler {

    protected void handleElement(final Node _node, final Object _blank, final ConstructorFactory _factory) throws ElementHandlerException {
        Assert.notNull(_node, "Element is null.");
        Assert.notNull(_blank, "Blank is null.");
        Assert.notNull(_factory, "Factory is null.");

        String value = _node.getNodeValue();
        ((SimplerConfiguration) _blank).setElementXPath(value);
    }

    protected void handleAttribute(final Node _node, final Object _blank, final ConstructorFactory _factory) throws AttributeHandler.AttributeHandlerException {
    }
}