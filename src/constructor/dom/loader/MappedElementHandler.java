package constructor.dom.loader;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import constructor.dom.ElementHandler;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import util.Assert;

import java.util.Map;

import static util.CollectionUtils.newHashMap;

/**
 * Пытается обработать аттрибуты и вложенные элементы с
 * помощью имеющихся обработчиков. Обработчики имеются в мапах класса.
 * Если в мапах обработчика не найдет - пытается применить обработчик
 * по умолчанию
 *
 * @author Igor Usenko
 *         Date: 14.03.2009
 */
public class MappedElementHandler extends AbstractElementHandler {

    protected Map<String, AttributeHandler> attributeHandlers;
    protected Map<String, ElementHandler> elementHandlers;

    private static final String IGNORABLE_TEXT = "#text";
    private static final String IGNORABLE_COMMENT = "#comment";

    public MappedElementHandler(final Map<String, AttributeHandler> _attributeHandlers, final Map<String, ElementHandler> _elementHandlers) {
        Assert.notNull(_attributeHandlers, "Attribute handlers is null.");
        Assert.notNull(_elementHandlers, "Element handlers is null.");

        this.attributeHandlers = _attributeHandlers;
        this.elementHandlers = _elementHandlers;
    }

    public MappedElementHandler(final Map<String, ElementHandler> _elementHandlers) {
        Assert.notNull(_elementHandlers, "Element handlers is null.");

        this.attributeHandlers = newHashMap();
        this.elementHandlers = _elementHandlers;
    }

    public MappedElementHandler() {
        this.attributeHandlers = newHashMap();
        this.elementHandlers = newHashMap();
    }

    protected void handleElement(final Node _node, final Object _blank, final ConstructorFactory _factory) throws ElementHandlerException {

        String nodeName = _node.getNodeName();

        if (accepted(nodeName)) {
            ElementHandler handler = getElementHandler(nodeName);

            if (handler == null) {
                throw new ElementHandlerException("Can`t find handler for element [ " + _node.getNodeName() + " ].");
            } else {
                handler.handle((Element) _node, _blank, _factory);
            }
        }
    }

    private boolean accepted(final String _nodeName) {
        return !(_nodeName.contains(IGNORABLE_TEXT) || _nodeName.contains(IGNORABLE_COMMENT));
    }

    protected void handleAttribute(final Node _node, final Object _blank, final ConstructorFactory _factory) throws AttributeHandler.AttributeHandlerException {

        AttributeHandler handler = getAttributeHandler(_node.getNodeName());

        if (handler != null) {
            handler.handle(_node.getNodeName(), _node.getNodeValue(), _blank, _factory);
        }
    }

    private AttributeHandler getAttributeHandler(final String _id) {
        AttributeHandler result = this.attributeHandlers.get(_id);

        return result == null ? new DefaultAttributeHandler() : result;
    }

    private ElementHandler getElementHandler(final String _id) {
        return this.elementHandlers.get(_id);
    }
}
