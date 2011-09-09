package constructor.objects.interpreter.element;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import constructor.dom.ElementHandler;
import constructor.dom.loader.AbstractElementHandler;
import constructor.objects.interpreter.configuration.FragmentAnalyserConfiguration;
import constructor.objects.interpreter.configuration.InterpreterConfiguration;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import util.Assert;

/**
 * Обработчик элемента Interpreter в XML файле
 *
 * @author Igor Usenko
 *         Date: 27.03.2009
 */
public class InterpreterElementHandler extends AbstractElementHandler {
    private static final String PAGES_KEY = "pages";
    private static final String FRAGMENTS_KEY = "fragments";
    private static final String FRAGMENT_KEY = "fragment";

    protected void handleElement(Node _node, Object _blank, ConstructorFactory _factory) throws ElementHandlerException {
        Assert.notNull(_node, "Node is null.");
        Assert.notNull(_blank, "Blank is null.");
        Assert.notNull(_factory, "Factory is null.");

        String name = _node.getNodeName();

        if (name == null) {
            throw new ElementHandlerException("Element name is null.");
        }

        if (name.equals(FRAGMENT_KEY)) {
            ElementHandler handler = new FragmentElementHandler();
            FragmentAnalyserConfiguration blank = new FragmentAnalyserConfiguration();
            handler.handle((Element) _node, blank, _factory);
            ((InterpreterConfiguration) _blank).setFragmentAnalyserConfiguration(blank);
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

        if (name.equals(PAGES_KEY)) {
            AttributeHandler handler = new PagesAttributeHandler();
            handler.handle(name, value, _blank, _factory);
        }

        if (name.equals(FRAGMENTS_KEY)) {
            AttributeHandler handler = new FragmentsAttributeHandler();
            handler.handle(name, value, _blank, _factory);
        }
    }
}
