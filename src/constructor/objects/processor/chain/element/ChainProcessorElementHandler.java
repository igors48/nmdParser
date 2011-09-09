package constructor.objects.processor.chain.element;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import constructor.dom.ElementHandler;
import constructor.dom.constructor.OperatorFactory;
import constructor.dom.loader.AbstractElementHandler;
import constructor.objects.processor.VariableProcessorAdapter;
import constructor.objects.processor.chain.ChainProcessorAdapter;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import util.Assert;

/**
 * Обработчик элемента processor в XML файле
 *
 * @author Igor Usenko
 *         Date: 21.03.2009
 */
public class ChainProcessorElementHandler extends AbstractElementHandler {

    private final OperatorFactory operatorFactory;

    public ChainProcessorElementHandler(final OperatorFactory _operatorFactory) {
        Assert.notNull(_operatorFactory, "Operator factory is null");
        this.operatorFactory = _operatorFactory;
    }

    protected void handleElement(final Node _node, final Object _blank, final ConstructorFactory _factory) throws ElementHandlerException {
        Assert.notNull(_node, "Element is null.");
        Assert.notNull(_blank, "Blank is null.");
        Assert.notNull(_factory, "Factory is null.");

        String name = _node.getNodeName();

        if (name == null) {
            throw new ElementHandlerException("Element name is null.");
        }

        VariableProcessorAdapter adapter = this.operatorFactory.getAdapter(name);
        ElementHandler handler = this.operatorFactory.getHandler(name);

        if (handler != null && adapter != null) {
            handler.handle((Element) _node, adapter, _factory);
            ((ChainProcessorAdapter) _blank).addAdapter(adapter);
        }
    }

    protected void handleAttribute(final Node _node, final Object _blank, final ConstructorFactory _factory) throws AttributeHandler.AttributeHandlerException {
        // there is no attributes
    }
}
