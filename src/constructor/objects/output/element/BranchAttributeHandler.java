package constructor.objects.output.element;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import constructor.objects.output.configuration.OutputConfiguration;
import util.Assert;

/**
 * Обработчик атрибута output.branch
 *
 * @author Igor Usenko
 *         Date: 06.04.2009
 */
public class BranchAttributeHandler implements AttributeHandler {

    public void handle(final String _id, final String _value, final Object _blank, final ConstructorFactory _factory) throws AttributeHandlerException {
        Assert.isValidString(_id, "Id is not valid.");
        Assert.isValidString(_value, "Value is not valid.");
        Assert.notNull(_blank, "Blank is null.");
        Assert.notNull(_factory, "Factory is null.");

        ((OutputConfiguration) _blank).setBranch(_value);
    }

}