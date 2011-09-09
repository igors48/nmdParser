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
public class MessageModelHandler {

    private final Log log;

    public MessageModelHandler() {
        this.log = LogFactory.getLog(getClass());
    }

    public Model handle(final MessageModel _model, final FlowController _controller) throws ModelHandlerException {
        Assert.notNull(_model, "Model is null");
        Assert.notNull(_controller, "Controller is null");

        try {
            Model result = new NotImplementedModel();

            switch (_model.getNextModel()) {
                case CHOOSE_MAIN_TASK: {
                    result = new MainTasksModel();
                    break;
                }
                case CHOOSE_WORKSPACE_TASK: {
                    result = new ChooseWorkspaceTaskModel();
                    break;
                }
                case LOADING_WORKSPACE: {
                    result = new LoadingWorkspaceModel(_controller.getWorkspace());
                    break;
                }
                case CHOOSE_FEEDERS_TASK: {
                    result = new FeedersTasksModel(_controller.getWorkspace(), _controller.getGuiSettings().externalEditorAvailable());
                    break;
                }
            }

            return result;
        } catch (Throwable t) {
            throw new ModelHandlerException(t);
        }
    }
}
