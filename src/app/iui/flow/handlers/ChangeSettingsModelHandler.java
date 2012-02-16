package app.iui.flow.handlers;

import app.iui.flow.FlowController;
import app.iui.flow.Model;
import app.iui.flow.ModelHandlerException;
import app.iui.flow.models.ChangeSettingsModel;
import app.iui.flow.models.ExternalPostprocessorModel;
import app.iui.flow.models.ExternalToolsModel;
import app.iui.flow.models.MainTasksModel;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 16.04.2011
 */
public class ChangeSettingsModelHandler {

    public Model handle(final ChangeSettingsModel _model, final FlowController _controller) throws ModelHandlerException {
        Assert.notNull(_model, "Model is null");
        Assert.notNull(_controller, "Controller is null");

        try {
            Model result = new MainTasksModel();

            if (_model.isApproved()) {

                if (_model.getSettings() == ChangeSettingsModel.Settings.TOOLS) {

                    result = new ExternalToolsModel(_controller.getGuiSettings().getPathToExternalEditor(),
                            _controller.getGuiSettings().getOneEditorPerOneFileMode(),
                            _controller.getGuiSettings().getPathToExternalFb2Viewer(),
                            _controller.getGuiSettings().getOneFb2ViewerPerOneFileMode());
                } else {

                    result = new ExternalPostprocessorModel(_controller.getGuiSettings().isExternalPostprocessorEnabled(),
                            _controller.getGuiSettings().getExternalPostprocessorPath(),
                            _controller.getGuiSettings().getExternalPostprocessorCommandLinePattern(),
                            _controller.getGuiSettings().getExternalPostprocessorTimeout());
                }

            }

            return result;
        } catch (Exception e) {
            throw new ModelHandlerException(e);
        }
    }

}
