package constructor.objects.output.element;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import constructor.dom.loader.AbstractElementHandler;
import constructor.objects.output.configuration.OutputConfiguration;
import org.w3c.dom.Node;
import util.Assert;

/**
 * Обработчик элемента output.forEach
 *
 * @author Igor Usenko
 *         Date: 26.04.2009
 */
public class ForEachElementHandler extends AbstractElementHandler {

    private static final String PATH_KEY = "path";
    private static final String COMMAND_KEY = "command";
    private static final String WAIT_KEY = "wait";

    protected void handleElement(final Node _node, final Object _blank, final ConstructorFactory _factory) throws ElementHandlerException {
        // empty
    }

    protected void handleAttribute(final Node _node, final Object _blank, final ConstructorFactory _factory) throws AttributeHandler.AttributeHandlerException {
        Assert.notNull(_node, "Atribute is null.");
        Assert.notNull(_blank, "Blank is null.");
        Assert.notNull(_factory, "Factory is null.");

        String name = _node.getNodeName();
        String value = _node.getNodeValue();

        if (name == null) {
            throw new AttributeHandler.AttributeHandlerException("Attribute name is null.");
        }

        if (name.equals(PATH_KEY)) {
            ((OutputConfiguration) _blank).setForEachPath(value);
        }

        if (name.equals(COMMAND_KEY)) {
            ((OutputConfiguration) _blank).setForEachCommand(value);
        }

        if (name.equals(WAIT_KEY)) {
            ((OutputConfiguration) _blank).setForEachWait(value);
        }
    }
}