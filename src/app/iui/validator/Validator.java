package app.iui.validator;

import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 31.10.2010
 */
public class Validator {

    private final Rule rule;
    private final String message;

    public Validator(final Rule _rule, final String _message) {
        Assert.notNull(_rule, "Rule is null");
        this.rule = _rule;

        Assert.isValidString(_message, "Message is not valid");
        this.message = _message;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean valid(final String _data) {
        return this.rule.valid(_data);
    }
}
