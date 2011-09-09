package constructor.objects.interpreter.element;

import constructor.dom.AttributeHandler;
import constructor.dom.ConstructorFactory;
import constructor.objects.interpreter.configuration.FragmentAnalyserConfiguration;
import util.Assert;

/**
 * Обработчик атрибута interpreter.fragment.dateCorrection
 *
 * @author Igor Usenko
 *         Date: 27.03.2009
 */
public class FragmentTimeCorrectionAttributeHandler implements AttributeHandler {

    private static final String DEFAULT_KEY = "default";

    public void handle(String _id, String _value, Object _blank, ConstructorFactory _factory) throws AttributeHandlerException {
        Assert.isValidString(_id, "Id is not valid.");
        Assert.isValidString(_value, "Value is not valid.");
        Assert.notNull(_blank, "Blank is null.");
        Assert.notNull(_factory, "Factory is null.");

        if (!_id.equals(DEFAULT_KEY)) {
            ((FragmentAnalyserConfiguration) _blank).setTimeCorrection(_value);
        }
    }
}