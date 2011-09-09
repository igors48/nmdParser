package constructor.objects.processor.remove_tag.element;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import constructor.dom.loader.AbstractElementHandler;
import constructor.objects.processor.remove_tag.adapter.RemoveTagProcessorAdapter;
import org.w3c.dom.Node;
import util.Assert;

/**
 * Обработчик элемента removeTag.occurence
 *
 * @author Igor Usenko
 *         Date: 20.05.2009
 */
public class OccurrenceElementHandler extends AbstractElementHandler {

    protected void handleElement(final Node _node, final Object _blank, final ConstructorFactory _factory) throws ElementHandlerException {
        Assert.notNull(_node, "Element is null.");
        Assert.notNull(_blank, "Blank is null.");
        Assert.notNull(_factory, "Factory is null.");

        String value = _node.getNodeValue();
        ((RemoveTagProcessorAdapter) _blank).setElementOccurrence(value);
    }

    protected void handleAttribute(final Node _node, final Object _blank, final ConstructorFactory _factory) throws AttributeHandler.AttributeHandlerException {
    }
}