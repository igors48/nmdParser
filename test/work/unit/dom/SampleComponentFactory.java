package work.unit.dom;

import constructor.dom.AttributeHandler;
import constructor.dom.Blank;
import constructor.dom.ComponentFactory;
import constructor.dom.ElementHandler;
import constructor.dom.loader.MappedElementHandler;
import util.Assert;

import java.util.Map;

import static util.CollectionUtils.newHashMap;

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

        Map<String, ElementHandler> handlerMap = newHashMap();
        handlerMap.put("inner", inner);
        handlerMap.put("nested", nested);

        Map<String, AttributeHandler> attributeMap = newHashMap();

        return new MappedElementHandler(attributeMap, handlerMap);
    }
}
