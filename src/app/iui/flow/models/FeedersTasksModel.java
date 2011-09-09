package app.iui.flow.models;

import app.iui.flow.Model;
import app.iui.flow.ModelType;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 04.12.2010
 */
public class FeedersTasksModel extends Model {

    private final String workspace;
    private final boolean editExternalEnabled;

    private FeederTask task;

    public FeedersTasksModel(final String _workspace, final boolean _editExternalEnabled) {
        super(ModelType.CHOOSE_FEEDERS_TASK);

        Assert.isValidString(_workspace, "Workspace is not valid");
        this.workspace = _workspace;

        this.editExternalEnabled = _editExternalEnabled;

        this.task = FeederTask.CREATE_SIMPLE_FEEDER;
    }

    public boolean isEditExternalEnabled() {
        return this.editExternalEnabled;
    }

    public String getWorkspace() {
        return this.workspace;
    }

    public FeederTask getTask() {
        return this.task;
    }

    public void setTask(final FeederTask _task) {
        Assert.notNull(_task, "Task is null");

        this.task = _task;
    }

    public enum FeederTask {
        CREATE_SIMPLE_FEEDER,
        EDIT_SIMPLE_FEEDER,
        DELETE_SIMPLE_FEEDER,
        EDIT_FEEDER_EXTERNAL,
        RESET_FEEDER,
        BACK
    }
}
