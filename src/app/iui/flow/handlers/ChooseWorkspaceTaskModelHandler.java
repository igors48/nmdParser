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
 *         Date: 19.12.2010
 */
public class ChooseWorkspaceTaskModelHandler {

    private static final String RENAME_WORKSPACE_TASK_NAME = "iui.rename.workspace.task.name";
    private static final String RENAME_AFTER_CHOOSE_WORKSPACE_TASK_NAME = "iui.rename.after.choose.workspace.task.name";

    private static final String DELETE_WORKSPACE_TASK_NAME = "iui.delete.workspace.task.name";
    private static final String DELETE_AFTER_CHOOSE_WORKSPACE_TASK_NAME = "iui.delete.after.choose.workspace.task.name";

    private static final String CREATE_WORKSPACE_LABEL = "iui.create.workspace.label";
    private static final String CREATE_AFTER_ENTER_WORKSPACE_NAME_TASK_NAME = "iui.create.after.enter.workspace.name.task.name";

    private final Log log;

    public ChooseWorkspaceTaskModelHandler() {
        this.log = LogFactory.getLog(getClass());
    }

    public Model handle(final ChooseWorkspaceTaskModel _model, final FlowController _controller) throws ModelHandlerException {
        Assert.notNull(_model, "Model is null");
        Assert.notNull(_controller, "Controller is null");

        try {
            Model result = new NotImplementedModel();

            if (_model.isApproved()) {

                if (_model.getTask() == ChooseWorkspaceTaskModel.Task.CREATE) {
                    _controller.setMode(FlowController.Mode.CREATE_WORKSPACE);
                    result = new EditWorkspaceNameModel(_controller.getString(CREATE_WORKSPACE_LABEL), _controller.getString(CREATE_AFTER_ENTER_WORKSPACE_NAME_TASK_NAME), "", "");
                }

                if (_model.getTask() == ChooseWorkspaceTaskModel.Task.DELETE) {
                    _controller.setMode(FlowController.Mode.DELETE_WORKSPACE);
                    result = new SelectWorkspaceModel(_controller.getString(DELETE_WORKSPACE_TASK_NAME), _controller.getString(DELETE_AFTER_CHOOSE_WORKSPACE_TASK_NAME), _controller.getApi().getWorkspacesNames());
                }

                if (_model.getTask() == ChooseWorkspaceTaskModel.Task.RENAME) {
                    _controller.setMode(FlowController.Mode.RENAME_WORKSPACE);
                    result = new SelectWorkspaceModel(_controller.getString(RENAME_WORKSPACE_TASK_NAME), _controller.getString(RENAME_AFTER_CHOOSE_WORKSPACE_TASK_NAME), _controller.getApi().getWorkspacesNames());
                }
            } else {
                result = new MainTasksModel();
            }

            return result;
        } catch (Throwable e) {
            throw new ModelHandlerException(e);
        }
    }
}
