package constructor.objects.dateparser.element;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import constructor.dom.loader.AbstractElementHandler;
import constructor.objects.dateparser.configuration.DateParserConfiguration;
import constructor.objects.dateparser.core.TodayStrategy;
import org.w3c.dom.Node;
import util.Assert;

/**
 * Обработчик элемента dateParser.today
 *
 * @author Igor Usenko
 *         Date: 13.06.2009
 */
public class TodayElementHandler extends AbstractElementHandler {

    protected void handleElement(final Node _node, final Object _blank, final ConstructorFactory _factory) throws ElementHandlerException {
        Assert.notNull(_node, "Element is null.");
        Assert.notNull(_blank, "Blank is null.");
        Assert.notNull(_factory, "Factory is null.");

        String value = _node.getNodeValue();
        ((DateParserConfiguration) _blank).addStrategy(value, new TodayStrategy());
    }

    protected void handleAttribute(final Node _node, final Object _blank, final ConstructorFactory _factory) throws AttributeHandler.AttributeHandlerException {
    }
}