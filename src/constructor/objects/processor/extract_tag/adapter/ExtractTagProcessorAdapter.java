package constructor.objects.processor.extract_tag.adapter;

import constructor.objects.ConfigurationException;
import constructor.objects.processor.OccurrenceSet;
import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.VariableProcessorAdapter;
import constructor.objects.processor.extract_tag.ExtractTagProcessor;
import util.Assert;

/**
 * Адаптер для ExtractTagProcessor
 *
 * @author Igor Usenko
 *         Date: 29.04.2009
 */
public class ExtractTagProcessorAdapter implements VariableProcessorAdapter {

    private final OccurrenceSet occurrences;

    private String pattern;
    private String in;
    private String out;

    public ExtractTagProcessorAdapter() {
        this.occurrences = new OccurrenceSet();
    }

    public VariableProcessor getProcessor() throws ConfigurationException {
        return new ExtractTagProcessor(this.in, this.pattern, this.occurrences, this.out);
    }

    public void setAttributeIn(String _value) {
        Assert.notNull(_value, "Extract tag : in value is null.");
        this.in = _value;
    }

    public void setAttributeOut(String _value) {
        Assert.notNull(_value, "Extract tag : out value is null.");
        this.out = _value;
    }

    public void setElementPattern(final String _pattern) {
        Assert.isValidString(_pattern, "Extract tag : pattern is not valid.");

        this.pattern = _pattern;
    }

    public void setElementOccurrence(final String _occurrence) {
        Assert.isValidString(_occurrence, "Extract tag : occurence is not valid.");

        try {
            this.occurrences.add(Integer.valueOf(_occurrence));
        } catch (Exception e) {
            // empty
        }
    }

}
