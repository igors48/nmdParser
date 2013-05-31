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
public class MainTasksModelHandler {

    private static final String UPDATE_TASK_NAME = "iui.update.task.name";
    private static final String SELECT_FEEDERS_TASK_NAME = "iui.select.feeders.for.update";

    private static final String MANAGE_TASK_NAME = "iui.manage.task.name";
    private static final String SELECT_FEEDER_ACTION_TASK_NAME = "iui.select.feeder.action";

    private final Log log;

    public MainTasksModelHandler() {
        this.log = LogFactory.getLog(getClass());
    }

    public Model handle(final MainTasksModel _model, final FlowController _controller) throws ModelHandlerException {
        Assert.notNull(_model, "Model is null");
        Assert.notNull(_controller, "Controller is null");

        try {
            Model result = new NotImplementedModel();

            if (_model.getMainTask() == MainTasksModel.MainTask.UPDATE_FEEDERS) {
                _controller.setMode(FlowController.Mode.UPDATE_FEEDERS);
                result = new SelectWorkspaceModel(_controller.getString(UPDATE_TASK_NAME), _controller.getString(SELECT_FEEDERS_TASK_NAME), _controller.getApi().getWorkspacesNames());
            }

            if (_model.getMainTask() == MainTasksModel.MainTask.MANAGE_FEEDERS) {
                _controller.setMode(FlowController.Mode.MANAGE_FEEDERS);
                result = new SelectWorkspaceModel(_controller.getString(MANAGE_TASK_NAME), _controller.getString(SELECT_FEEDER_ACTION_TASK_NAME), _controller.getApi().getWorkspacesNames());
            }

            if (_model.getMainTask() == MainTasksModel.MainTask.MANAGE_WORKSPACES) {
                result = new ChooseWorkspaceTaskModel();
            }

            if (_model.getMainTask() == MainTasksModel.MainTask.CHANGE_SETTINGS) {

                result = new ChangeSettingsModel();
            }

            return result;

        } catch (Throwable t) {
            throw new ModelHandlerException(t);
        }
    }
}
