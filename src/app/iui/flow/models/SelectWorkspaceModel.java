package app.iui.flow.models;

import app.iui.flow.Model;
import app.iui.flow.ModelType;
import util.Assert;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 04.12.2010
 */
public class SelectWorkspaceModel extends Model {

    private final String task;
    private final String nextTask;
    private final List<String> workspaces;

    private String selected;
    private ModelType rejected;

    public SelectWorkspaceModel(final String _task, final String _nextTask, final List<String> _workspaces) {
        super(ModelType.SELECT_WORKSPACE);

        Assert.isValidString(_task, "Task is not valid");
        this.task = _task;

        Assert.isValidString(_nextTask, "Next task is not valid");
        this.nextTask = _nextTask;

        Assert.notNull(_workspaces, "Workspaces list is null");
        this.workspaces = _workspaces;

        this.selected = "";
        this.rejected = ModelType.CHOOSE_MAIN_TASK;
    }

    public ModelType getRejected() {
        return rejected;
    }

    public void setRejected(final ModelType _rejected) {
        Assert.notNull(_rejected, "Rejected is null");
        this.rejected = _rejected;
    }

    public String getNextTaskLabel() {
        return this.nextTask;
    }

    public String getTask() {
        return this.task;
    }

    public List<String> getWorkspaces() {
        return this.workspaces;
    }

    public void setSelected(final String _selected) {
        Assert.isValidString(_selected, "Selected is not valid");

        this.selected = _selected;
    }

    public String getSelected() {
        return this.selected;
    }
}
