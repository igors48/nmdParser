package app.iui.flow.models;

import app.iui.flow.Model;
import app.iui.flow.ModelType;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 05.12.2010
 */
public class ErrorModel extends Model {

    private final boolean onlyExit;
    private final String message;
    private final Throwable cause;
    private final ModelType backModel;

    public ErrorModel(final String _message, final Throwable _cause, final boolean _onlyExit) {
        super(ModelType.ERROR);

        Assert.isValidString(_message, "Message is not valid");
        this.message = _message;

        this.cause = _cause;

        this.onlyExit = _onlyExit;

        this.backModel = null;
    }

    public ErrorModel(final String _message, final Throwable _cause, final ModelType _backModel) {
        super(ModelType.ERROR);

        Assert.isValidString(_message, "Message is not valid");
        this.message = _message;

        this.cause = _cause;

        this.onlyExit = false;

        Assert.notNull(_backModel, "Back model is null");
        this.backModel = _backModel;
    }

    public Throwable getCause() {
        return this.cause;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isOnlyExit() {
        return this.onlyExit;
    }

    public ModelType getBackModel() {
        return this.backModel;
    }
}
