package constructor.objects.processor.filter.adapter;

import constructor.objects.ConfigurationException;
import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.VariableProcessorAdapter;
import constructor.objects.processor.filter.FilterProcessor;
import util.Assert;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 15.09.2011
 */
public class FilterProcessorAdapter implements VariableProcessorAdapter {

    private String in;
    private String out;

    public FilterProcessorAdapter() {
        setAttributeIn("");
        setAttributeOut("");
    }

    public VariableProcessor getProcessor() throws ConfigurationException {
        return new FilterProcessor(this.in, this.out);
    }

    public void setAttributeIn(final String _value) {
        Assert.notNull(_value, "Filter : in value is null");
        this.in = _value;
    }

    public void setAttributeOut(final String _value) {
        Assert.notNull(_value, "Filter : out value is null");
        this.out = _value;
    }
}
