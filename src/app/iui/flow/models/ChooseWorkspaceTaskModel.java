package app.iui.flow.models;

import app.iui.flow.Model;
import app.iui.flow.ModelType;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 16.12.2010
 */
public class ChooseWorkspaceTaskModel extends Model {

    private Task task;

    public ChooseWorkspaceTaskModel() {
        super(ModelType.CHOOSE_WORKSPACE_TASK);
    }

    public Task getTask() {
        return this.task;
    }

    public void setTask(final Task _task) {
        Assert.notNull(_task, "Task is null");
        this.task = _task;
    }

    public enum Task {
        CREATE,
        RENAME,
        DELETE
    }
}
