package constructor.objects.snippet.element;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import constructor.dom.loader.AbstractElementHandler;
import constructor.objects.snippet.configuration.SnippetConfiguration;
import org.w3c.dom.Node;
import util.Assert;

/**
 * ���������� �������� snippet.regExp � XML �����
 *
 * @author Igor Usenko
 *         Date: 12.12.2009
 */
public class RegExpElementHandler extends AbstractElementHandler {

    protected void handleElement(final Node _node, final Object _blank, final ConstructorFactory _factory) throws ElementHandlerException {
        Assert.notNull(_node, "Element is null");
        Assert.notNull(_blank, "Blank is null");
        Assert.notNull(_factory, "Factory is null");

        String value = _node.getNodeValue();
        ((SnippetConfiguration) _blank).setRegExp(value);
    }

    protected void handleAttribute(final Node _node, final Object _blank, final ConstructorFactory _factory) throws AttributeHandler.AttributeHandlerException {
        // empty
    }
}