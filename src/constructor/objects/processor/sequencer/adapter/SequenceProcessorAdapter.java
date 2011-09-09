package constructor.objects.processor.sequencer.adapter;

import constructor.objects.ConfigurationException;
import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.VariableProcessorAdapter;
import constructor.objects.processor.sequencer.SequenceProcessor;
import util.Assert;

/**
 * Адаптер для SequenceProcessor
 *
 * @author Igor Usenko
 *         Date: 04.06.2009
 */
public class SequenceProcessorAdapter implements VariableProcessorAdapter {

    private String in;
    private String out;
    private String pattern;
    private String start;
    private String stop;
    private String step;
    private String mult;
    private String len;
    private String padd;

    public SequenceProcessorAdapter() {
        this.len = "";
        this.padd = "";
    }

    public VariableProcessor getProcessor() throws ConfigurationException {
        return new SequenceProcessor(this.in, this.pattern, this.start, this.stop, this.step, this.mult, this.out, this.len, this.padd);
    }

    public void setAttributeIn(final String _in) {
        Assert.isValidString(_in, "Input is not valid.");
        this.in = _in;
    }

    public void setAttributeOut(final String _out) {
        Assert.isValidString(_out, "Output is not valid.");
        this.out = _out;
    }

    public void setAttributePattern(final String _pattern) {
        Assert.isValidString(_pattern, "Pattern is not valid.");
        this.pattern = _pattern;
    }

    public void setAttributeStart(final String _start) {
        Assert.isValidString(_start, "Start is not valid.");
        this.start = _start;
    }

    public void setAttributeStep(final String _step) {
        Assert.isValidString(_step, "Step is not valid.");
        this.step = _step;
    }

    public void setAttributeStop(final String _stop) {
        Assert.isValidString(_stop, "Stop is not valid.");
        this.stop = _stop;
    }

    public void setAttributeMult(final String _mult) {
        Assert.isValidString(_mult, "Mult is not valid.");
        this.mult = _mult;
    }

    public void setAttributeLen(final String _len) {
        Assert.isValidString(_len, "Len is not valid.");
        this.len = _len;
    }

    public void setAttributePadd(final String _padd) {
        Assert.isValidString(_padd, "Padd is not valid.");
        this.padd = _padd;
    }
}
