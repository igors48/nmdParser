package app.iui.flow.models;

import app.iui.flow.Model;
import app.iui.flow.ModelType;
import util.Assert;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 16.02.2012
 */
public class GoogleReaderManageProfilesModel extends Model {

    private Task task;

    public GoogleReaderManageProfilesModel() {
        super(ModelType.GOOGLE_READER_MANAGE_PROFILES);
    }

    public Task getTask() {
        return this.task;
    }

    public void setTask(final Task _task) {
        Assert.notNull(_task, "Task is null");
        this.task = _task;
    }

    public enum Task {
        ADD_PROFILE,
        EDIT_PROFILE,
        DELETE_PROFILE,
        BACK
    }

}
