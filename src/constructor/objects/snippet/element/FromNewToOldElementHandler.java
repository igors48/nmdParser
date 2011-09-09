package constructor.objects.snippet.element;

import constructor.dom.ConstructorFactory;
import constructor.dom.ElementHandler;
import constructor.objects.output.configuration.DocumentItemsSortMode;
import constructor.objects.snippet.configuration.SnippetConfiguration;
import org.w3c.dom.Element;
import util.Assert;

/**
 * Обработчик элемента snippet.fromNewToOld в XML файле
 *
 * @author Igor Usenko
 *         Date: 09.12.2009
 */
public class FromNewToOldElementHandler implements ElementHandler {

    public void handle(final Element _element, final Object _blank, final ConstructorFactory _factory) throws ElementHandlerException {
        Assert.notNull(_element, "Element is null.");
        Assert.notNull(_blank, "Blank is null.");
        Assert.notNull(_factory, "Factory is null.");

        ((SnippetConfiguration) _blank).setDocumentItemsSortMode(DocumentItemsSortMode.FROM_NEW_TO_OLD);
    }
}