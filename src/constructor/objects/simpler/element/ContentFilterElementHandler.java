package constructor.objects.simpler.element;

import constructor.dom.ConstructorFactory;
import constructor.dom.ElementHandler;
import constructor.objects.simpler.configuration.SimplerConfiguration;
import constructor.objects.snippet.configuration.SnippetConfiguration;
import org.w3c.dom.Element;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 25.09.2011
 */
public class ContentFilterElementHandler implements ElementHandler {

    public void handle(final Element _element, final Object _blank, final ConstructorFactory _factory) throws ElementHandlerException {
        Assert.notNull(_element, "Element is null.");
        Assert.notNull(_blank, "Blank is null.");
        Assert.notNull(_factory, "Factory is null.");

        ((SimplerConfiguration) _blank).setAutoContentFiltering(true);
    }
}