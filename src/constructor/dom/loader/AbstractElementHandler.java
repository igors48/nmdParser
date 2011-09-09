package constructor.dom.loader;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import constructor.dom.ElementHandler;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import util.Assert;

/**
 * Абстрактный обработчик элемента. Определяет базовое поведение :
 * Получить список атрибутов - обработать. Получить список вложенных
 * элементов - обработать
 *
 * @author Igor Usenko
 *         Date: 14.03.2009
 */
public abstract class AbstractElementHandler implements ElementHandler {

    public void handle(final Element _element, final Object _blank, final ConstructorFactory _factory) throws ElementHandlerException {
        Assert.notNull(_element, "Element is null.");
        Assert.notNull(_blank, "Owner is null.");
        Assert.notNull(_factory, "Factory is null.");

        try {
            handleAttributes(_element.getAttributes(), _blank, _factory);

            handleElements(_element.getChildNodes(), _blank, _factory);

        } catch (AttributeHandler.AttributeHandlerException e) {
            throw new ElementHandlerException(e);
        }
    }

    protected abstract void handleElement(final Node _node, final Object _blank, final ConstructorFactory _factory) throws ElementHandlerException;

    protected abstract void handleAttribute(final Node _node, final Object _blank, final ConstructorFactory _factory) throws AttributeHandler.AttributeHandlerException;

    private void handleAttributes(final NamedNodeMap _attributes, final Object _blank, final ConstructorFactory _factory) throws AttributeHandler.AttributeHandlerException {

        for (int index = 0; index < _attributes.getLength(); ++index) {
            handleAttribute(_attributes.item(index), _blank, _factory);
        }
    }

    private void handleElements(final NodeList _elements, final Object _blank, final ConstructorFactory _factory) throws ElementHandlerException {

//        if (_elements.getLength() == 0) {
//            handleElement(null, _blank, _factory);
//        } else {

        for (int index = 0; index < _elements.getLength(); ++index) {
            handleElement(_elements.item(index), _blank, _factory);
        }
//        }
    }
}
