package constructor.objects.processor.chain.element;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import constructor.dom.constructor.OperatorFactory;
import constructor.objects.processor.chain.adapter.FirstOneProcessorAdapter;
import org.w3c.dom.Node;
import util.Assert;

/**
 * Обработчик элемента firstOne в XML файле
 *
 * @author Igor Usenko
 *         Date: 27.06.2010
 */
public class FirstOneProcessorElementHandler extends ChainProcessorElementHandler {

    private static final String OUT_KEY = "out";

    public FirstOneProcessorElementHandler(final OperatorFactory _operatorFactory) {
        super(_operatorFactory);
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

        if (name.equals(OUT_KEY)) {
            ((FirstOneProcessorAdapter) _blank).setOut(value);
        }
    }
}
