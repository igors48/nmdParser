package constructor.objects.processor.xpath.element;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import constructor.dom.loader.AbstractElementHandler;
import constructor.objects.processor.xpath.adapter.XPathProcessorAdapter;
import org.w3c.dom.Node;
import util.Assert;

/**
 * Обработчик элемента xPath.expression
 *
 * @author Igor Usenko
 *         Date: 27.06.2009
 */
public class ExpressionElementHandler extends AbstractElementHandler {

    protected void handleElement(final Node _node, final Object _blank, final ConstructorFactory _factory) throws ElementHandlerException {
        Assert.notNull(_node, "Element is null.");
        Assert.notNull(_blank, "Blank is null.");
        Assert.notNull(_factory, "Factory is null.");

        String value = _node.getNodeValue();
        ((XPathProcessorAdapter) _blank).setElementExpression(value);
    }

    protected void handleAttribute(final Node _node, final Object _blank, final ConstructorFactory _factory) throws AttributeHandler.AttributeHandlerException {
        // empty
    }
}