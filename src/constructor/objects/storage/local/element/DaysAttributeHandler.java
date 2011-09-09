package constructor.objects.storage.local.element;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import constructor.objects.storage.local.configuration.LocalStorageConfiguration;
import util.Assert;

/**
 * Обработчик атрибута storage.days
 *
 * @author Igor Usenko
 *         Date: 2.08.2009
 */
public class DaysAttributeHandler implements AttributeHandler {

    public void handle(String _id, String _value, Object _blank, ConstructorFactory _factory) throws AttributeHandlerException {
        Assert.isValidString(_id, "Id is not valid.");
        Assert.isValidString(_value, "Value is not valid.");
        Assert.notNull(_blank, "Blank is null.");
        Assert.notNull(_factory, "Factory is null.");

        ((LocalStorageConfiguration) _blank).setAge(_value);
    }
}