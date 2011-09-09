package app.iui.validator;

import util.Assert;

import javax.swing.*;

/**
 * @author Igor Usenko
 *         Date: 03.11.2010
 */
public class ValidationError {

    private final JTextField target;
    private final String message;

    public ValidationError(final JTextField _target, final String _message) {
        Assert.notNull(_target, "Target is null");
        this.target = _target;

        Assert.isValidString(_message, "Message is not valid");
        this.message = _message;
    }

    public JTextField getTarget() {
        return this.target;
    }

    public String getMessage() {
        return this.message;
    }
}
