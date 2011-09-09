package constructor.objects.source.element;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import constructor.dom.loader.AbstractElementHandler;
import constructor.objects.source.configuration.SourceConfiguration;
import org.w3c.dom.Node;
import util.Assert;

/**
 * Обработчик элемента source.rss в XML файле
 *
 * @author Igor Usenko
 *         Date: 21.03.2009
 */
public class RssElementHandler extends AbstractElementHandler {

    protected void handleElement(final Node _node, final Object _blank, final ConstructorFactory _factory) throws ElementHandlerException {
        Assert.notNull(_node, "Element is null");
        Assert.notNull(_blank, "Blank is null");
        Assert.notNull(_factory, "Factory is null");

        String value = _node.getNodeValue();
        ((SourceConfiguration) _blank).setElementRssUrl(value);
    }

    protected void handleAttribute(final Node _node, final Object _blank, final ConstructorFactory _factory) throws AttributeHandler.AttributeHandlerException {
        // empty
    }
}                     
