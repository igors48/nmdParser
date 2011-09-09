package constructor.objects.processor.remove_tag.adapter;

import constructor.objects.ConfigurationException;
import constructor.objects.processor.OccurrenceSet;
import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.VariableProcessorAdapter;
import constructor.objects.processor.remove_tag.RemoveTagProcessor;
import util.Assert;

/**
 * Адаптер для RemoveTagProcessor
 *
 * @author Igor Usenko
 *         Date: 20.05.2009
 */
public class RemoveTagProcessorAdapter implements VariableProcessorAdapter {

    private final OccurrenceSet occurrences;

    private String pattern;
    private String in;
    private String out;

    public RemoveTagProcessorAdapter() {
        this.occurrences = new OccurrenceSet();
    }

    public VariableProcessor getProcessor() throws ConfigurationException {
        return new RemoveTagProcessor(this.in, this.pattern, this.occurrences, this.out);
    }

    public void setAttributeIn(String _value) {
        Assert.notNull(_value, "Remove tag : in value is null.");
        this.in = _value;
    }

    public void setAttributeOut(String _value) {
        Assert.notNull(_value, "Remove tag : out value is null.");
        this.out = _value;
    }

    public void setElementPattern(final String _pattern) {
        Assert.isValidString(_pattern, "Remove tag : pattern is not valid.");

        this.pattern = _pattern;
    }

    public void setElementOccurrence(final String _occurrence) {
        Assert.isValidString(_occurrence, "Remove tag : occurence is not valid.");

        try {
            this.occurrences.add(Integer.valueOf(_occurrence));
        } catch (Exception e) {
            // empty
        }
    }

}
