package app.iui.flow.models;

import app.iui.flow.Model;
import app.iui.flow.ModelType;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 01.12.2010
 */
public class MainTasksModel extends Model {

    private MainTask mainTask;

    public MainTasksModel() {
        super(ModelType.CHOOSE_MAIN_TASK);

        this.mainTask = MainTask.EXIT;
    }

    public MainTask getMainTask() {
        return this.mainTask;
    }

    public void setMainTask(final MainTask _mainTask) {
        Assert.notNull(_mainTask, "Main task is null");

        this.mainTask = _mainTask;
    }

    public enum MainTask {
        UPDATE_FEEDERS,
        GOOGLE_READER,
        MANAGE_FEEDERS,
        MANAGE_WORKSPACES,
        CHANGE_SETTINGS,
        EXIT
    }
}
