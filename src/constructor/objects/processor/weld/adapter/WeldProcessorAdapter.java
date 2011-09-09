package constructor.objects.processor.weld.adapter;

import constructor.objects.ConfigurationException;
import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.VariableProcessorAdapter;
import constructor.objects.processor.weld.WeldProcessor;
import util.Assert;

/**
 * Адаптер процессора weld
 *
 * @author Igor Usenko
 *         Date: 21.09.2009
 */
public class WeldProcessorAdapter implements VariableProcessorAdapter {

    private String in;
    private String out;
    private String divider;

    public WeldProcessorAdapter() {
        this.divider = "";
    }

    public VariableProcessor getProcessor() throws ConfigurationException {
        return new WeldProcessor(this.in, this.divider, this.out);
    }

    public void setAttributeIn(final String _in) {
        Assert.isValidString(_in, "Input is not valid.");
        this.in = _in;
    }

    public void setAttributeOut(final String _out) {
        Assert.isValidString(_out, "Output is not valid.");
        this.out = _out;
    }

    public void setAttributeDivider(final String _divider) {
        Assert.isValidString(_divider, "Divider is not valid.");
        this.divider = _divider;
    }
}