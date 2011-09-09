package constructor.objects.processor.decrement.adapter;

import constructor.objects.ConfigurationException;
import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.VariableProcessorAdapter;
import constructor.objects.processor.decrement.DecrementProcessor;
import util.Assert;

/**
 * Адаптер для DecrementProcessor
 *
 * @author Igor Usenko
 *         Date: 03.07.2009
 */
public class DecrementProcessorAdapter implements VariableProcessorAdapter {

    private String in;
    private String out;

    public VariableProcessor getProcessor() throws ConfigurationException {
        return new DecrementProcessor(this.in, this.out);
    }

    public void setAttributeIn(final String _in) {
        Assert.isValidString(_in, "Input is not valid.");
        this.in = _in;
    }

    public void setAttributeOut(final String _out) {
        Assert.isValidString(_out, "Output is not valid.");
        this.out = _out;
    }

}
