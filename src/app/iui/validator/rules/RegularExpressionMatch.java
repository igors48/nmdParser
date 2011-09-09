package app.iui.validator.rules;

import app.iui.validator.AbstractRule;
import util.Assert;

import java.util.regex.Pattern;

/**
 * @author Igor Usenko
 *         Date: 03.11.2010
 */
public class RegularExpressionMatch extends AbstractRule {

    private final Pattern pattern;

    public RegularExpressionMatch(final String _expression) {
        Assert.isValidString(_expression, "Expression is not valid");
        this.pattern = Pattern.compile(_expression);
    }

    protected boolean validate(final String _data) {
        boolean result = false;

        if (_data != null) {
            return this.pattern.matcher(_data).matches();
        }

        return result;
    }
}
