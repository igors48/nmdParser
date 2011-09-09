package app.iui.flow.models;

import app.iui.flow.Model;
import app.iui.flow.ModelType;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 20.12.2010
 */
public class DeleteWorkspaceConfirmationModel extends Model {

    private final String workspace;

    public DeleteWorkspaceConfirmationModel(final String _workspace) {
        super(ModelType.DELETE_WORKSPACE_CONFIRMATION);

        Assert.isValidString(_workspace, "Workspace is not valid");
        this.workspace = _workspace;
    }

    public String getWorkspace() {
        return this.workspace;
    }
}
