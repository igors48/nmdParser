package app.iui.flow.handlers;

import app.iui.flow.FlowController;
import app.iui.flow.Model;
import app.iui.flow.ModelHandlerException;
import app.iui.flow.ModelType;
import app.iui.flow.models.ChangeSettingsModel;
import app.iui.flow.models.ExternalToolsModel;
import app.iui.flow.models.MessageModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 21.12.2010
 */
public class ExternalToolsSettingsModelHandler {

    private static final String CHANGE_SETTINGS_STORED_CAPTION = "iui.change.settings.message.stored.caption";
    private static final String CHANGE_SETTINGS_STORED_MESSAGE = "iui.change.settings.message.stored.message";

    private final Log log;

    public ExternalToolsSettingsModelHandler() {
        this.log = LogFactory.getLog(getClass());
    }

    public Model handle(final ExternalToolsModel _model, final FlowController _controller) throws ModelHandlerException {
        Assert.notNull(_model, "Model is null");
        Assert.notNull(_controller, "Controller is null");

        try {
            Model result = new ChangeSettingsModel();

            if (_model.isApproved()) {
                _controller.getGuiSettings().setPathToExternalEditor(_model.getExternalEditor());
                _controller.getGuiSettings().setOneEditorPerOneFileMode(_model.isOneExternalEditorPerFile());

                _controller.getGuiSettings().setPathToExternalFb2Viewer(_model.getExternalFb2Viewer());
                _controller.getGuiSettings().setOneFb2ViewerPerOneFileMode(_model.isOneExternalFb2ViewerPerFile());

                result = new MessageModel(_controller.getString(CHANGE_SETTINGS_STORED_CAPTION), _controller.getString(CHANGE_SETTINGS_STORED_MESSAGE), ModelType.CHOOSE_MAIN_TASK);
            }

            return result;
        } catch (Exception e) {
            throw new ModelHandlerException(e);
        }
    }
}
