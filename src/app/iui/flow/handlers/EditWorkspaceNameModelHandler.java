package app.iui.flow.handlers;

import app.iui.flow.FlowController;
import app.iui.flow.Model;
import app.iui.flow.ModelHandlerException;
import app.iui.flow.ModelType;
import app.iui.flow.models.ChooseWorkspaceTaskModel;
import app.iui.flow.models.EditWorkspaceNameModel;
import app.iui.flow.models.MessageModel;
import app.iui.flow.models.NotImplementedModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import java.text.MessageFormat;

/**
 * @author Igor Usenko
 *         Date: 18.12.2010
 */
public class EditWorkspaceNameModelHandler {

    private static final String CREATED_TITLE = "iui.workspace.created.title";
    private static final String RENAMED_TITLE = "iui.workspace.renamed.title";
    private static final String CREATED_MESSAGE = "iui.workspace.created.message";
    private static final String RENAMED_MESSAGE = "iui.workspace.renamed.message";

    private final Log log;

    public EditWorkspaceNameModelHandler() {
        this.log = LogFactory.getLog(getClass());
    }

    public Model handle(final EditWorkspaceNameModel _model, final FlowController _controller) throws ModelHandlerException {
        Assert.notNull(_model, "Model is null");
        Assert.notNull(_controller, "Controller is null");

        try {
            Model result = new NotImplementedModel();

            if (_model.isApproved()) {

                if (_controller.getMode() == FlowController.Mode.RENAME_WORKSPACE) {
                    _controller.getApi().renameWorkspace(_model.getOldName(), _model.getName());

                    result = new MessageModel(_controller.getString(RENAMED_TITLE), MessageFormat.format(_controller.getString(RENAMED_MESSAGE), _model.getOldName(), _model.getName()), ModelType.CHOOSE_WORKSPACE_TASK);

                    this.log.info(MessageFormat.format("Workspace [ {0} ] renamed to [ {1} ]", _model.getOldName(), _model.getName()));
                }

                if (_controller.getMode() == FlowController.Mode.CREATE_WORKSPACE) {
                    _controller.getApi().createWorkspace(_model.getName());

                    result = new MessageModel(_controller.getString(CREATED_TITLE), MessageFormat.format(_controller.getString(CREATED_MESSAGE), _model.getName()), ModelType.CHOOSE_WORKSPACE_TASK);

                    this.log.info(MessageFormat.format("Workspace [ {0} ] created", _model.getName()));
                }
            } else {
                result = new ChooseWorkspaceTaskModel();
            }

            return result;
        } catch (Exception e) {
            throw new ModelHandlerException(e);
        }
    }
}
