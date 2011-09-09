package app.iui.validator.rules;

import app.iui.validator.AbstractRule;
import util.Assert;

import java.text.MessageFormat;

/**
 * @author Igor Usenko
 *         Date: 03.11.2010
 */
public class IntegerInRange extends AbstractRule {

    private final long min;
    private final long max;

    public IntegerInRange(final long _min, final long _max) {
        Assert.greaterOrEqual(_max, _min, "Min > Max");

        this.min = _min;
        this.max = _max;
    }

    public String formatErrorMessage(final String _template) {
        Assert.isValidString(_template, "Template is not valid");

        return MessageFormat.format(_template, this.min, this.max);
    }

    protected boolean validate(final String _data) {
        boolean result = false;

        try {
            final long value = Long.valueOf(_data);

            result = value >= this.min && value <= this.max;
        } catch (Exception e) {
            // empty
        }

        return result;
    }
}
