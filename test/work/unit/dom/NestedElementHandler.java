package work.unit.dom;

import constructor.dom.Constructor;
import constructor.dom.ConstructorFactory;
import constructor.dom.ElementHandler;
import constructor.dom.ObjectType;
import org.w3c.dom.Element;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 15.03.2009
 */
public class NestedElementHandler implements ElementHandler {

    public void handle(Element _element, Object _blank, ConstructorFactory _factory) throws ElementHandlerException {
        Assert.notNull(_element, "Element is null.");
        Assert.notNull(_blank, "Element is null.");
        Assert.notNull(_factory, "Element is null.");

        try {
            String id = _element.getFirstChild().getNodeValue();
            Constructor constructor = _factory.getConstructor();
            Object inner = constructor.create(id, ObjectType.SAMPLE_OBJECT_01);
            ((SampleObject01) _blank).setAttributeObject(inner);
        } catch (Constructor.ConstructorException e) {
            throw new ElementHandlerException(e);
        }
    }

}
