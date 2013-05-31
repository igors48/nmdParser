package constructor.objects.snippet.element;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import constructor.dom.loader.AbstractElementHandler;
import constructor.objects.snippet.configuration.SnippetConfiguration;
import org.w3c.dom.Node;
import util.Assert;

/**
 * Обработчик элемента snippet.url в XML файле
 *
 * @author Igor Usenko
 *         Date: 12.12.2009
 */
public class UrlElementHandler extends AbstractElementHandler {

    private static String FROM_KEY = "from";
    private static String TO_KEY = "to";
    private static String STEP_KEY = "step";
    private static String MULT_KEY = "mult";
    private static String LEN_KEY = "len";
    private static String PADD_KEY = "padd";

    protected void handleElement(final Node _node, final Object _blank, final ConstructorFactory _factory) throws ElementHandlerException {
        Assert.notNull(_node, "Element is null");
        Assert.notNull(_blank, "Blank is null");
        Assert.notNull(_factory, "Factory is null");

        String value = _node.getNodeValue();
        ((SnippetConfiguration) _blank).addUrl(value);
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

        if (name.equals(FROM_KEY)) {
            ((SnippetConfiguration) _blank).setFrom(value);
        }

        if (name.equals(TO_KEY)) {
            ((SnippetConfiguration) _blank).setTo(value);
        }

        if (name.equals(STEP_KEY)) {
            ((SnippetConfiguration) _blank).setStep(value);
        }

        if (name.equals(MULT_KEY)) {
            ((SnippetConfiguration) _blank).setMult(value);
        }

        if (name.equals(LEN_KEY)) {
            ((SnippetConfiguration) _blank).setLen(value);
        }

        if (name.equals(PADD_KEY)) {
            ((SnippetConfiguration) _blank).setPadd(value);
        }
    }

}