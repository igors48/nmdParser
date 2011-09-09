package app.iui.flow.models;

import app.iui.flow.Model;
import app.iui.flow.ModelType;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 08.05.2011
 */
public class AutoUpdateTasksModel extends Model {

    private AutoUpdateTask task;

    public AutoUpdateTasksModel() {
        super(ModelType.AUTO_UPDATE_TASKS);
    }

    public AutoUpdateTask getTask() {
        return this.task;
    }

    public void setTask(final AutoUpdateTask _task) {
        Assert.notNull(_task);
        this.task = _task;
    }

    public enum AutoUpdateTask {
        START,
        EDIT
    }
}
