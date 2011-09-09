package constructor.objects.snippet.element;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import constructor.dom.loader.AbstractElementHandler;
import constructor.objects.snippet.configuration.SnippetConfiguration;
import org.w3c.dom.Node;
import util.Assert;

/**
 * Обработчик элемента snippet.forEach в XML файле
 *
 * @author Igor Usenko
 *         Date: 11.12.2009
 */
public class ForEachElementHandler extends AbstractElementHandler {

    private static final String PATH_KEY = "path";
    private static final String COMMAND_KEY = "command";
    private static final String WAIT_KEY = "wait";

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

        if (name.equals(PATH_KEY)) {
            ((SnippetConfiguration) _blank).setPath(value);
        }

        if (name.equals(COMMAND_KEY)) {
            ((SnippetConfiguration) _blank).setCommand(value);
        }

        if (name.equals(WAIT_KEY)) {
            ((SnippetConfiguration) _blank).setWait(value);
        }
    }
}
