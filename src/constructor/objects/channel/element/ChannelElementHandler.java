package constructor.objects.channel.element;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import constructor.dom.ElementHandler;
import constructor.dom.loader.AbstractElementHandler;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import util.Assert;

/**
 * Обработчик элемента channel в XML файле
 *
 * @author Igor Usenko
 *         Date: 05.04.2009
 */
public class ChannelElementHandler extends AbstractElementHandler {

    private static final String SOURCE_KEY = "source";
    private static final String INTERPRETER_KEY = "interpreter";
    private static final String LAST_ITEMS_KEY = "lastitems";
    private static final String FORCED_KEY = "forced";
    private static final String HEADER_KEY = "header";
    private static final String LANG_KEY = "lang";
    private static final String GENRE_KEY = "genre";
    private static final String COVER_KEY = "cover";
    private static final String PAUSE_BETWEEN_REQUESTS_KEY = "pauseBetweenRequests";

    protected void handleElement(Node _node, Object _blank, ConstructorFactory _factory) throws ElementHandlerException {
        Assert.notNull(_node, "Element is null.");
        Assert.notNull(_blank, "Blank is null.");
        Assert.notNull(_factory, "Factory is null.");

        String name = _node.getNodeName();

        if (name == null) {
            throw new ElementHandlerException("Element name is null.");
        }

        if (name.equals(GENRE_KEY)) {
            ElementHandler handler = new GenreElementHandler();
            handler.handle((Element) _node, _blank, _factory);
        }

        if (name.equals(COVER_KEY)) {
            ElementHandler handler = new CoverElementHandler();
            handler.handle((Element) _node, _blank, _factory);
        }
    }

    protected void handleAttribute(Node _node, Object _blank, ConstructorFactory _factory) throws AttributeHandler.AttributeHandlerException {
        Assert.notNull(_node, "Atribute is null.");
        Assert.notNull(_blank, "Blank is null.");
        Assert.notNull(_factory, "Factory is null.");

        String name = _node.getNodeName();
        String value = _node.getNodeValue();

        if (name == null) {
            throw new AttributeHandler.AttributeHandlerException("Attribute name is null.");
        }

        if (name.equals(SOURCE_KEY)) {
            AttributeHandler handler = new SourceAttributeHandler();
            handler.handle(name, value, _blank, _factory);
        }

        if (name.equals(INTERPRETER_KEY)) {
            AttributeHandler handler = new InterpreterAttributeHandler();
            handler.handle(name, value, _blank, _factory);
        }

        if (name.equals(LAST_ITEMS_KEY)) {
            AttributeHandler handler = new LastItemsAttributeHandler();
            handler.handle(name, value, _blank, _factory);
        }

        if (name.equals(FORCED_KEY)) {
            AttributeHandler handler = new ForcedAttributeHandler();
            handler.handle(name, value, _blank, _factory);
        }

        if (name.equals(HEADER_KEY)) {
            AttributeHandler handler = new HeaderAttributeHandler();
            handler.handle(name, value, _blank, _factory);
        }

        if (name.equals(LANG_KEY)) {
            AttributeHandler handler = new LangAttributeHandler();
            handler.handle(name, value, _blank, _factory);
        }

        if (name.equals(PAUSE_BETWEEN_REQUESTS_KEY)) {
            AttributeHandler handler = new PauseBetweenRequestsAttributeHandler();
            handler.handle(name, value, _blank, _factory);
        }
    }
}
