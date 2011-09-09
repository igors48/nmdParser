package app.iui.validator;

import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 05.11.2010
 */
public abstract class AbstractRule implements Rule {

    @SuppressWarnings({"SimplifiableConditionalExpression"})
    public boolean valid(final String _data) {
        return (_data == null || _data.isEmpty()) ? true : validate(_data);
    }

    public String formatErrorMessage(final String _template) {
        Assert.isValidString(_template, "Template is not valid");

        return _template;
    }

    protected abstract boolean validate(final String _data);
}
