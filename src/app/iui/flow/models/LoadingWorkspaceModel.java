package app.iui.flow.models;

import app.iui.entity.Entity;
import app.iui.flow.Model;
import app.iui.flow.ModelType;
import util.Assert;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 08.12.2010
 */
public class LoadingWorkspaceModel extends Model {

    private final String workspaceName;

    private List<Entity> entities;
    private Throwable cause;

    public LoadingWorkspaceModel(final String _workspaceName) {
        super(ModelType.LOADING_WORKSPACE);

        Assert.isValidString(_workspaceName, "Workspace name is not valid");
        this.workspaceName = _workspaceName;
    }

    public String getWorkspaceName() {
        return this.workspaceName;
    }

    public List<Entity> getEntities() {
        return this.entities;
    }

    public void setEntities(final List<Entity> _entities) {
        Assert.notNull(_entities, "Entities list is null");
        this.entities = _entities;
    }

    public Throwable getCause() {
        return this.cause;
    }

    public void setCause(final Throwable _cause) {
        this.cause = _cause;
    }
}
