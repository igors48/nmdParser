package work.unit.dom;

import constructor.dom.ConstructorFactory;
import constructor.dom.ElementHandler;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 15.03.2009
 */
public class InnerElementHandler implements ElementHandler {

    public void handle(Element _element, Object _blank, ConstructorFactory _factory) throws ElementHandlerException {
        Assert.notNull(_element, "Element is null.");
        Assert.notNull(_blank, "Element is null.");
        Assert.notNull(_factory, "Element is null.");

        String value = " ";
        Node node = _element.getFirstChild();

        if (node != null) {
            value = node.getNodeValue();
        }

        ((SampleObject01) _blank).setAttributeInner(value);
    }
}
