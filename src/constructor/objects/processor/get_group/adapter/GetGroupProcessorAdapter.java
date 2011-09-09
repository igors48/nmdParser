package constructor.objects.processor.get_group.adapter;

import constructor.objects.ConfigurationException;
import constructor.objects.processor.OccurrenceSet;
import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.VariableProcessorAdapter;
import constructor.objects.processor.get_group.GetGroupProcessor;
import util.Assert;

import java.util.regex.Pattern;

/**
 * Адаптер для getGroup процессора
 *
 * @author Igor Usenko
 *         Date: 20.03.2009
 */
public class GetGroupProcessorAdapter implements VariableProcessorAdapter {

    private final OccurrenceSet occurrences;

    private String in;
    private String out;
    private String pattern;

    private int group;

    public GetGroupProcessorAdapter() {
        this.occurrences = new OccurrenceSet();
        this.group = 1;
    }

    public void setAttributeIn(String _value) {
        Assert.notNull(_value, "Get group : in value is null.");
        this.in = _value;
    }

    public void setAttributeOut(String _value) {
        Assert.notNull(_value, "Get group : out value is null.");
        this.out = _value;
    }

    public void setElementOccurrence(String _value) {
        Assert.isValidString(_value, "Get group : incorrect occurrence id value.");
        this.occurrences.add(Integer.valueOf(_value));
    }

    public void setElementPattern(String _value) {
        Assert.isValidString(_value, "Get group : incorrect pattern value.");
        this.pattern = _value;
    }

    public void setElementGroup(String _value) {
        Assert.isValidString(_value, "Get group : incorrect group value.");
        this.group = Integer.valueOf(_value);
    }

    public VariableProcessor getProcessor() throws ConfigurationException {
        validate();

        //todo выбивается из общей схемы, но на первый раз - оставим
        return new GetGroupProcessor(this.in, this.pattern, this.occurrences, this.group, this.out);
    }

    private void validate() throws ConfigurationException {

        try {
            //Integer.valueOf(this.occurrences);
            Pattern.compile(this.pattern, Pattern.CASE_INSENSITIVE);
        } catch (Exception e) {
            throw new ConfigurationException(e);
        }
    }
}
