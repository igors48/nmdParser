package constructor.objects.processor.xpath.element;

import constructor.dom.ElementHandler;
import constructor.dom.loader.MappedElementHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Обработчик элемента xPath в XML файле
 *
 * @author Igor Usenko
 *         Date: 27.06.2009
 */
public class XPathProcessorElementHandler extends MappedElementHandler {

    private static final String EXPRESSION_KEY = "expression";

    public XPathProcessorElementHandler() {
        super();

        Map<String, ElementHandler> handlers = new HashMap<String, ElementHandler>();
        handlers.put(EXPRESSION_KEY, new ExpressionElementHandler());

        this.elementHandlers = handlers;
    }
}
