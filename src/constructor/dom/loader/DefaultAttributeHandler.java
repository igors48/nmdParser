package constructor.dom.loader;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import util.Assert;
import util.ReflecTools;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * ќбработчик атрибута по умолчанию. »щет у владельца метод setAttribute[Id](String _value)
 * и зовет его с параметром _value, если найдет конечно...
 *
 * @author Igor Usenko
 *         Date: 14.03.2009
 */
public class DefaultAttributeHandler implements AttributeHandler {

    private static final String SETTER_PREFIX = "setAttribute";

    public void handle(final String _id, final String _value, final Object _blank, final ConstructorFactory _factory) throws AttributeHandlerException {
        Assert.isValidString(_id, "Incorrect attribute id.");
        Assert.notNull(_value, "Incorrect attribute value.");
        Assert.notNull(_blank, "Blank is null.");

        try {
            Method method = ReflecTools.findMethod(_blank, SETTER_PREFIX, _id);
            method.invoke(_blank, _value);
        } catch (NoSuchMethodException e) {
            throw new AttributeHandlerException("Can`t find setter for attribute [ " + _id + " ].", e);
        } catch (InvocationTargetException e) {
            throw new AttributeHandlerException("Can`t set attribute [ " + _id + " ] value to [ " + _value + " ].", e);
        } catch (IllegalAccessException e) {
            throw new AttributeHandlerException("Can`t set attribute [ " + _id + " ] value to [ " + _value + " ].", e);
        }
    }
}
