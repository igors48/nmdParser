package constructor.objects.processor.setter.adapter;

import constructor.objects.ConfigurationException;
import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.VariableProcessorAdapter;
import constructor.objects.processor.setter.SetterProcessor;
import util.Assert;

/**
 * Адаптер для SetterProcessor
 *
 * @author Igor Usenko
 *         Date: 04.06.2009
 */
public class SetterProcessorAdapter implements VariableProcessorAdapter {

    private String in;
    private String out;
    private String value;
    private String index;

    public VariableProcessor getProcessor() throws ConfigurationException {
        return new SetterProcessor(this.in, this.value, this.index, this.out);
    }

    public void setAttributeIn(final String _in) {
        Assert.isValidString(_in, "Input is not valid.");
        this.in = _in;
    }

    public void setAttributeOut(final String _out) {
        Assert.isValidString(_out, "Output is not valid.");
        this.out = _out;
    }

    public void setAttributeValue(final String _value) {
        Assert.isValidString(_value, "Value is not valid.");
        this.value = _value;
    }

    public void setAttributeIndex(final String _index) {
        Assert.isValidString(_index, "Index is not valid.");
        this.index = _index;
    }

}
