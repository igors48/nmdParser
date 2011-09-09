package constructor.objects.processor.append.adapter;

import constructor.objects.ConfigurationException;
import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.VariableProcessorAdapter;
import constructor.objects.processor.append.AppendProcessor;
import util.Assert;

/**
 * Адаптер для append процессора
 *
 * @author Igor Usenko
 *         Date: 21.09.2009
 */
public class AppendProcessorAdapter implements VariableProcessorAdapter {

    private String in;
    private String out;
    private String first;
    private String second;

    public AppendProcessorAdapter() {
        this.first = "";
        this.second = "";
    }

    public VariableProcessor getProcessor() throws ConfigurationException {
        return new AppendProcessor(this.in, this.first, this.second, this.out);
    }

    public void setAttributeFirst(final String _first) {
        Assert.isValidString(_first, "First is not valid.");
        this.first = _first;
    }

    public void setAttributeIn(final String _in) {
        Assert.isValidString(_in, "Input is not valid.");
        this.in = _in;
    }

    public void setAttributeOut(final String _out) {
        Assert.isValidString(_out, "Output is not valid.");
        this.out = _out;
    }

    public void setAttributeSecond(final String _second) {
        Assert.isValidString(_second, "Second is not valid.");
        this.second = _second;
    }
}

