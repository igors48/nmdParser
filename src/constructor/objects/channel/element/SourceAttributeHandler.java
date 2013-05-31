package constructor.objects.channel.element;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import constructor.objects.channel.configuration.ChannelConfiguration;
import util.Assert;

/**
 * Обработчик атрибута channel.source
 *
 * @author Igor Usenko
 *         Date: 05.04.2009
 */
public class SourceAttributeHandler implements AttributeHandler {

    public void handle(final String _id, final String _value, final Object _blank, final ConstructorFactory _factory) throws AttributeHandlerException {
        Assert.isValidString(_id, "Id is not valid.");
        Assert.isValidString(_value, "Value is not valid.");
        Assert.notNull(_blank, "Blank is null.");
        Assert.notNull(_factory, "Factory is null.");

        ((ChannelConfiguration) _blank).setSourceId(_value);
    }
}
