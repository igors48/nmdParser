package constructor.objects.channel.element;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import constructor.objects.channel.configuration.ChannelConfiguration;
import util.Assert;
import util.TextTools;

/**
 * Обработчик атрибута channel.pauseBetweenRequests
 *
 * @author Igor Usenko
 *         Date: 28.06.2009
 */
public class PauseBetweenRequestsAttributeHandler implements AttributeHandler {

    public void handle(String _id, String _value, Object _blank, ConstructorFactory _factory) throws AttributeHandlerException {
        Assert.isValidString(_id, "Id is not valid.");
        Assert.isValidString(_value, "Value is not valid.");
        Assert.notNull(_blank, "Blank is null.");
        Assert.notNull(_factory, "Factory is null.");

        ((ChannelConfiguration) _blank).setPauseBetweenRequests(TextTools.parseLong(_value));
    }
}