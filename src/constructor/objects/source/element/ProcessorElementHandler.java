package constructor.objects.source.element;

import constructor.dom.*;
import constructor.dom.loader.AbstractElementHandler;
import constructor.objects.source.configuration.SourceConfiguration;
import org.w3c.dom.Node;
import util.Assert;

/**
 * Обработчик элемента source.processor
 *
 * @author Igor Usenko
 *         Date: 26.03.2009
 */
public class ProcessorElementHandler extends AbstractElementHandler {

    private static final String ID_ATTRIBUTE_NAME = "id";

    protected void handleElement(Node _node, Object _blank, ConstructorFactory _factory) throws ElementHandler.ElementHandlerException {
    }

    protected void handleAttribute(Node _node, Object _blank, ConstructorFactory _factory) throws AttributeHandler.AttributeHandlerException {
        Assert.notNull(_node, "Element is null.");
        Assert.notNull(_blank, "Blank is null.");
        Assert.notNull(_factory, "Factory is null.");

        try {
            String name = _node.getNodeName();
            String value = _node.getNodeValue();

            if (ID_ATTRIBUTE_NAME.equals(name)) {
                Constructor constructor = _factory.getConstructor();
                Object processor = constructor.create(value, ObjectType.PROCESSOR);
                ((SourceConfiguration) _blank).setProcessor(processor);
            } else {
                throw new AttributeHandler.AttributeHandlerException("Unexpected attribute [ " + name + " ] found in processor element.");
            }

        } catch (Constructor.ConstructorException e) {
            throw new AttributeHandler.AttributeHandlerException(e);
        }
    }
}
