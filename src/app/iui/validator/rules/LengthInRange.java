package app.iui.validator.rules;

import app.iui.validator.AbstractRule;
import util.Assert;

import java.text.MessageFormat;

/**
 * @author Igor Usenko
 *         Date: 14.11.2010
 */
public class LengthInRange extends AbstractRule {

    private final long min;
    private final long max;

    public LengthInRange(final long _min, final long _max) {
        super();

        Assert.greaterOrEqual(_max, _min, "Min > Max");

        this.min = _min;
        this.max = _max;
    }

    public String formatErrorMessage(final String _template) {
        Assert.isValidString(_template, "Template is not valid");

        return MessageFormat.format(_template, this.min, this.max);
    }

    protected boolean validate(final String _data) {
        return (_data.length() >= this.min) && (_data.length() <= this.max);
    }
}
