package app.iui.flow.handlers;

import app.iui.flow.FlowController;
import app.iui.flow.Model;
import app.iui.flow.ModelHandlerException;
import app.iui.flow.models.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 20.12.2010
 */
public class SelectWorkspaceModelHandler {

    private static final String RENAME_WORKSPACE_LABEL = "iui.rename.rename.workspace.label";
    private static final String RENAME_WORKSPACE_NEXT = "iui.rename.workspace.next";

    private final Log log;

    public SelectWorkspaceModelHandler() {
        this.log = LogFactory.getLog(getClass());
    }

    public Model handle(final SelectWorkspaceModel _model, final FlowController _controller) throws ModelHandlerException {
        Assert.notNull(_model, "Model is null");
        Assert.notNull(_controller, "Controller is null");

        try {
            Model result = new NotImplementedModel();

            if (_model.isApproved()) {
                _controller.setWorkspace(_model.getSelected());

                if (_controller.getMode() == FlowController.Mode.RENAME_WORKSPACE) {
                    result = new EditWorkspaceNameModel(_controller.getString(RENAME_WORKSPACE_LABEL), _controller.getString(RENAME_WORKSPACE_NEXT), _controller.getWorkspace(), _controller.getWorkspace());
                }

                if (_controller.getMode() == FlowController.Mode.DELETE_WORKSPACE) {
                    result = new DeleteWorkspaceConfirmationModel(_controller.getWorkspace());
                }

                if (_controller.getMode() == FlowController.Mode.UPDATE_FEEDERS) {
                    result = new LoadingWorkspaceModel(_model.getSelected());
                }

                if (_controller.getMode() == FlowController.Mode.MANAGE_FEEDERS) {
                    result = new LoadingWorkspaceModel(_model.getSelected());
                }
            } else {

                if (_controller.getMode() == FlowController.Mode.UPDATE_FEEDERS) {
                    result = new MainTasksModel();
                }

                if (_controller.getMode() == FlowController.Mode.MANAGE_FEEDERS) {
                    result = new MainTasksModel();
                }

                if (_controller.getMode() == FlowController.Mode.RENAME_WORKSPACE) {
                    result = new ChooseWorkspaceTaskModel();
                }

                if (_controller.getMode() == FlowController.Mode.DELETE_WORKSPACE) {
                    result = new ChooseWorkspaceTaskModel();
                }
            }

            return result;
        } catch (Throwable t) {
            throw new ModelHandlerException(t);
        }
    }
}
