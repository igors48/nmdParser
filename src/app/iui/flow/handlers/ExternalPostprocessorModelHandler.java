package app.iui.flow.handlers;

import app.iui.flow.FlowController;
import app.iui.flow.Model;
import app.iui.flow.ModelHandlerException;
import app.iui.flow.ModelType;
import app.iui.flow.models.ChangeSettingsModel;
import app.iui.flow.models.ExternalPostprocessorModel;
import app.iui.flow.models.MessageModel;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 17.04.2011
 */
public class ExternalPostprocessorModelHandler {

    private static final String CHANGE_SETTINGS_STORED_CAPTION = "iui.change.settings.message.stored.caption";
    private static final String CHANGE_SETTINGS_STORED_MESSAGE = "iui.change.settings.message.stored.message";

    public Model handle(final ExternalPostprocessorModel _model, final FlowController _controller) throws ModelHandlerException {
        Assert.notNull(_model, "Model is null");
        Assert.notNull(_controller, "Controller is null");

        try {
            Model result = new ChangeSettingsModel();

            if (_model.isApproved()) {
                _controller.getGuiSettings().setExternalPostprocessorEnabled(_model.isExternalPostprocessingEnabled());
                _controller.getGuiSettings().setExternalPostprocessorPath(_model.getPathToExternalPostprocessor());
                _controller.getGuiSettings().setExternalPostprocessorCommandLinePattern(_model.getCommandLinePattern());
                _controller.getGuiSettings().setExternalPostprocessorTimeout(_model.getExternalPostprocessingTimeout());

                result = new MessageModel(_controller.getString(CHANGE_SETTINGS_STORED_CAPTION), _controller.getString(CHANGE_SETTINGS_STORED_MESSAGE), ModelType.CHOOSE_MAIN_TASK);
            }

            return result;
        } catch (Throwable e) {
            throw new ModelHandlerException(e);
        }
    }

}
