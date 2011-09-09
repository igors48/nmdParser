package constructor.objects.interpreter.element;

import constructor.dom.AttributeHandler;
import constructor.dom.Constructor;
import constructor.dom.ConstructorFactory;
import constructor.dom.ObjectType;
import constructor.objects.interpreter.configuration.InterpreterConfiguration;
import constructor.objects.processor.chain.adapter.StandardChainProcessorAdapter;
import util.Assert;

/**
 * Обработчик атрибута interpreter.pages
 *
 * @author Igor Usenko
 *         Date: 27.03.2009
 */
public class PagesAttributeHandler implements AttributeHandler {

    private static final String DEFAULT_KEY = "default";

    public void handle(String _id, String _value, Object _blank, ConstructorFactory _factory) throws AttributeHandlerException {
        Assert.isValidString(_id, "Id is not valid.");
        Assert.isValidString(_value, "Value is not valid.");
        Assert.notNull(_blank, "Blank is null.");
        Assert.notNull(_factory, "Factory is null.");

        try {

            if (!_id.equals(DEFAULT_KEY)) {
                Constructor constructor = _factory.getConstructor();
                StandardChainProcessorAdapter processor = (StandardChainProcessorAdapter) constructor.create(_value, ObjectType.PROCESSOR);
                ((InterpreterConfiguration) _blank).setPageListAnalyser(processor);
            }

        } catch (Constructor.ConstructorException e) {
            throw new AttributeHandlerException(e);
        }
    }
}
