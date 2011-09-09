package app.iui.flow.handlers;

import app.iui.flow.FlowController;
import app.iui.flow.Model;
import app.iui.flow.ModelHandlerException;
import app.iui.flow.ModelType;
import app.iui.flow.models.ChooseWorkspaceTaskModel;
import app.iui.flow.models.DeleteWorkspaceConfirmationModel;
import app.iui.flow.models.MessageModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import java.text.MessageFormat;

/**
 * @author Igor Usenko
 *         Date: 20.12.2010
 */
public class DeleteWorkspaceConfirmationModelHandler {

    private static final String TITLE = "iui.workspace.deleted.title";
    private static final String MESSAGE = "iui.workspace.deleted.message";

    private final Log log;

    public DeleteWorkspaceConfirmationModelHandler() {
        this.log = LogFactory.getLog(getClass());
    }

    public Model handle(final DeleteWorkspaceConfirmationModel _model, final FlowController _controller) throws ModelHandlerException {
        Assert.notNull(_model, "Model is null");
        Assert.notNull(_controller, "Controller is null");

        try {
            Model result;

            if (_model.isApproved()) {
                _controller.getApi().deleteWorkspace(_controller.getWorkspace());

                this.log.info(MessageFormat.format("Workspace [ {0} ] deleted", _model.getWorkspace()));

                result = new MessageModel(_controller.getString(TITLE), MessageFormat.format(_controller.getString(MESSAGE), _model.getWorkspace()), ModelType.CHOOSE_WORKSPACE_TASK);
            } else {
                result = new ChooseWorkspaceTaskModel();
            }

            return result;
        } catch (Exception e) {
            throw new ModelHandlerException(e);
        }
    }
}
