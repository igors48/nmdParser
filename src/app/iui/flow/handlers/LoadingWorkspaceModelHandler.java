package app.iui.flow.handlers;

import app.iui.entity.Entity;
import app.iui.flow.FlowController;
import app.iui.flow.Model;
import app.iui.flow.ModelHandlerException;
import app.iui.flow.ModelType;
import app.iui.flow.models.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 21.12.2010
 */
public class LoadingWorkspaceModelHandler {

    public static final String TEST_ENTITY_NOT_FOUND_CAPTION = "iui.test.entity.not.found.caption";
    public static final String TEST_ENTITY_NOT_FOUND_LABEL = "iui.test.entity.not.found.label";

    private final Log log;

    public LoadingWorkspaceModelHandler() {
        this.log = LogFactory.getLog(getClass());
    }

    public Model handle(final LoadingWorkspaceModel _model, final FlowController _controller) throws ModelHandlerException {
        Assert.notNull(_model, "Model is null");
        Assert.notNull(_controller, "Controller is null");

        try {
            Model result = new NotImplementedModel();

            if (_model.isApproved()) {
                _controller.setFeeders(_model.getEntities());

                if (_controller.getMode() == FlowController.Mode.UPDATE_FEEDERS) {
                    result = new ChooseFetchModeModel(_controller.getWorkspace());
                }

                if (_controller.getMode() == FlowController.Mode.MANAGE_FEEDERS) {
                    result = new FeedersTasksModel(_controller.getWorkspace(), _controller.getGuiSettings().externalEditorAvailable());
                }

                if (_controller.getMode() == FlowController.Mode.TEST_FEEDER) {
                    String testId = _controller.getFeederTestCandidate();
                    Entity testEntity = _controller.findForId(testId);

                    if (testEntity == null) {
                        //no exception - no error model
                        result = new MessageModel(_controller.getString(LoadingWorkspaceModelHandler.TEST_ENTITY_NOT_FOUND_CAPTION), _controller.getString(LoadingWorkspaceModelHandler.TEST_ENTITY_NOT_FOUND_LABEL), ModelType.CHOOSE_MAIN_TASK);
                    } else {
                        result = new ChooseFeederDebugActionModel(testEntity);
                    }
                }
            } else {
                throw new ModelHandlerException(_model.getCause());
            }

            return result;
        } catch (Throwable t) {
            throw new ModelHandlerException(t);
        }
    }
}

/*
        Assert.notNull(_model, "Model is null");

        Model result;

        if (_model.isApproved()) {
            this.feeders = _model.getEntities();
            this.log.debug(MessageFormat.format("Found [ {0} ] feeders", this.feeders.size()));

            result = createKeyModel(ModelType.CHOOSE_FETCH_MODE);
        } else {
            result = new ErrorModel(MessageFormat.format(this.stringResource.getString(ERROR_LOADING_WORKSPACE), _model.getWorkspaceName()), _model.getCause(), false);
            this.log.error("Error loading workspace", _model.getCause());
        }

        return putToHistory(result);

 */
