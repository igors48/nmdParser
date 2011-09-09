package constructor.objects.processor.pack.adapter;

import constructor.objects.ConfigurationException;
import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.VariableProcessorAdapter;
import constructor.objects.processor.pack.PackProcessor;
import util.Assert;

/**
 * Адаптер процессора pack
 *
 * @author Igor Usenko
 *         Date: 21.09.2009
 */
public class PackProcessorAdapter implements VariableProcessorAdapter {

    private String in;
    private String out;

    public VariableProcessor getProcessor() throws ConfigurationException {
        return new PackProcessor(this.in, this.out);
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