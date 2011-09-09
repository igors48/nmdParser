package app.iui.validator.rules;

import app.iui.validator.Rule;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 03.11.2010
 */
public class NotEmpty implements Rule {

    public boolean valid(final String _data) {
        return _data != null && !_data.isEmpty();
    }

    public String formatErrorMessage(final String _template) {
        Assert.isValidString(_template, "Template is not valid");

        return _template;
    }
}
