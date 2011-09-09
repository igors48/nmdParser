package app.iui.flow.models;

import app.iui.flow.Model;
import app.iui.flow.ModelType;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 04.12.2010
 */
public class MessageModel extends Model {

    private final String title;
    private final String message;
    private final ModelType nextModel;

    public MessageModel(final String _title, final String _message, final ModelType _nextModel) {
        super(ModelType.MESSAGE);

        Assert.isValidString(_title, "Title is not valid");
        this.title = _title;

        Assert.isValidString(_message, "Message is not valid");
        this.message = _message;

        Assert.notNull(_nextModel, "Next model is null");
        this.nextModel = _nextModel;
    }

    public String getMessage() {
        return this.message;
    }

    public String getTitle() {
        return this.title;
    }

    public ModelType getNextModel() {
        return this.nextModel;
    }
}
