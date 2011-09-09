package app.iui.flow.models;

import app.iui.entity.Entity;
import app.iui.flow.Model;
import app.iui.flow.ModelType;
import util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 05.12.2010
 */
public class ChooseFeedersModel extends Model {

    private final boolean multiSelect;
    private final List<Entity> feeders;
    private final String workspace;
    private final String taskName;
    private final String nextTaskName;

    private List<Entity> selected;

    public ChooseFeedersModel(final boolean _multiSelect, final List<Entity> _feeders, final String _workspace, final String _taskName, final String _nextTaskName) {
        super(ModelType.CHOOSE_FEEDERS);

        this.multiSelect = _multiSelect;

        Assert.notNull(_feeders, "Feeders is null");
        this.feeders = _feeders;

        Assert.isValidString(_workspace, "Workspace is not valid");
        this.workspace = _workspace;

        Assert.isValidString(_taskName, "Task name is not valid");
        this.taskName = _taskName;

        Assert.isValidString(_nextTaskName, "Next task name is not valid");
        this.nextTaskName = _nextTaskName;

        this.selected = new ArrayList<Entity>();
    }

    public boolean isMultiSelect() {
        return this.multiSelect;
    }

    public List<Entity> getFeeders() {
        return this.feeders;
    }

    public String getWorkspace() {
        return this.workspace;
    }

    public List<Entity> getSelected() {
        return this.selected;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public String getNextTaskName() {
        return this.nextTaskName;
    }

    public void setSelected(final List<Entity> _feeders) {
        Assert.notNull(_feeders, "Feeders is null");
        this.selected = _feeders;
    }
}
