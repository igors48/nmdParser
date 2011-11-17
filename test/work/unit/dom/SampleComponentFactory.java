package work.unit.dom;

import constructor.dom.AttributeHandler;
import constructor.dom.Blank;
import constructor.dom.ComponentFactory;
import constructor.dom.ElementHandler;
import constructor.dom.loader.MappedElementHandler;
import util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Igor Usenko
 *         Date: 15.03.2009
 */
public class SampleComponentFactory implements ComponentFactory {

    public Blank getBlank(String _id) {
        Assert.notNull(_id, "Id is null.");

        Blank result = null;

        if (_id.equals("sample01")) {
            result = new SampleObject01();
        }

        return result;
    }

    public ElementHandler getHandler(String _id) {
        Assert.notNull(_id, "Id is null.");

        ElementHandler inner = new InnerElementHandler();
        ElementHandler nested = new NestedElementHandler();

        Map<String, ElementHandler> handlerMap = new HashMap<String, ElementHandler>();
        handlerMap.put("inner", inner);
        handlerMap.put("nested", nested);

        Map<String, AttributeHandler> attributeMap = new HashMap<String, AttributeHandler>();

        ElementHandler result = new MappedElementHandler(attributeMap, handlerMap);

        return result;
    }
}
