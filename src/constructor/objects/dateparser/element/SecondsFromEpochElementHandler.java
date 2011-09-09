package constructor.objects.dateparser.element;

import constructor.dom.ConstructorFactory;
import constructor.dom.ElementHandler;
import constructor.objects.dateparser.configuration.DateParserConfiguration;
import constructor.objects.dateparser.core.SecondsFromEpochStrategy;
import org.w3c.dom.Element;
import util.Assert;

/**
 * Обработчик элемента dateParser.secondsFromEpoch
 *
 * @author Igor Usenko
 *         Date: 13.06.2009
 */
public class SecondsFromEpochElementHandler implements ElementHandler {

    public void handle(final Element _element, final Object _blank, final ConstructorFactory _factory) throws ElementHandlerException {
        Assert.notNull(_element, "Element is null.");
        Assert.notNull(_blank, "Blank is null.");
        Assert.notNull(_factory, "Factory is null.");

        ((DateParserConfiguration) _blank).addStrategy(SecondsFromEpochStrategy.PATTERN_STRING, new SecondsFromEpochStrategy());
    }
}