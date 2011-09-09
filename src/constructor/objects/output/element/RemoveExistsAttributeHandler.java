package constructor.objects.output.element;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import constructor.objects.output.configuration.OutputConfiguration;
import util.Assert;

/**
 * Обработчик атрибута output.removeExists
 *
 * @author Igor Usenko
 *         Date: 06.04.2009
 */
public class RemoveExistsAttributeHandler implements AttributeHandler {

    public void handle(String _id, String _value, Object _blank, ConstructorFactory _factory) throws AttributeHandlerException {
        Assert.isValidString(_id, "Id is not valid.");
        Assert.isValidString(_value, "Value is not valid.");
        Assert.notNull(_blank, "Blank is null.");
        Assert.notNull(_factory, "Factory is null.");

        ((OutputConfiguration) _blank).setRemoveExists(_value);
    }
}