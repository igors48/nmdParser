package app.iui.flow.handlers;

import app.iui.flow.FlowController;
import app.iui.flow.Model;
import app.iui.flow.ModelHandlerException;
import app.iui.flow.ModelType;
import app.iui.flow.models.*;
import app.iui.tools.ExternalToolsUtils;
import constructor.dom.locator.Mask;
import constructor.objects.simpler.configuration.SimplerConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 22.12.2010
 */
public class ChooseFeedersModelHandler {

    private static final String RESET_CAPTION = "iui.reset.feeder.message.caption";
    private static final String RESET_LABEL = "iui.reset.feeder.message.label";

    private final Log log;

    public ChooseFeedersModelHandler() {
        this.log = LogFactory.getLog(getClass());
    }

    public Model handle(final ChooseFeedersModel _model, final FlowController _controller) throws ModelHandlerException {
        Assert.notNull(_model, "Model is null");
        Assert.notNull(_controller, "Controller is null");

        try {
            Model result = new NotImplementedModel();

            if (_model.isApproved()) {
                _controller.setSelectedFeeders(_model.getSelected());

                if (_controller.getMode() == FlowController.Mode.UPDATE_FEEDERS) {
                    if (ChooseFetchModeModel.FetchMode.STANDARD == _controller.getFetchMode()) {
                        result = _controller.createUpdateModel();
                    } else if (ChooseFetchModeModel.FetchMode.FORCED == _controller.getFetchMode()) {
                        result = new ForcedUpdatePeriodModel();
                    } else {
                        result = new UpdateContextModel(_controller.getSelectedFeeders().get(0).getName(), _controller.getSelectedFeeders().get(0).getPlaceHolders());
                    }
                }

                if (_controller.getMode() == FlowController.Mode.EDIT_SIMPLE_FEEDER) {
                    result = new SimpleFeederModel((SimplerConfiguration) _controller.getSelectedFeeders().get(0).getBlank(), false);
                }

                if (_controller.getMode() == FlowController.Mode.DELETE_SIMPLE_FEEDER) {
                    result = new DeleteSimpleFeederConfirmationModel(_controller.getSelectedFeeders().get(0));
                }

                if (_controller.getMode() == FlowController.Mode.EDIT_FEEDER_EXTERNAL) {
                    List<String> files = _controller.getSelectedFeeders().get(0).getSourceFiles();

                    if (_controller.getGuiSettings().getOneEditorPerOneFileMode()) {
                        ExternalToolsUtils.startOneProcessPerFile(_controller.getGuiSettings().getPathToExternalEditor(), files);
                    } else {
                        ExternalToolsUtils.startOneProcessForAllFiles(_controller.getGuiSettings().getPathToExternalEditor(), files);
                    }

                    result = new FeedersTasksModel(_controller.getWorkspace(), _controller.getGuiSettings().externalEditorAvailable());
                }

                if (_controller.getMode() == FlowController.Mode.RESET_FEEDER) {
                    _controller.getApi().removeServiceFiles(new Mask(_controller.getSelectedFeeders().get(0).getName()));

                    result = new MessageModel(_controller.getString(RESET_CAPTION), _controller.getString(RESET_LABEL), ModelType.CHOOSE_FEEDERS_TASK);
                }
            } else {

                if (_controller.getMode() == FlowController.Mode.UPDATE_FEEDERS) {
                    result = new ChooseFetchModeModel(_controller.getWorkspace());
                }

                if (_controller.getMode() == FlowController.Mode.EDIT_SIMPLE_FEEDER) {
                    result = new FeedersTasksModel(_controller.getWorkspace(), _controller.getGuiSettings().externalEditorAvailable());
                }

                if (_controller.getMode() == FlowController.Mode.DELETE_SIMPLE_FEEDER) {
                    result = new FeedersTasksModel(_controller.getWorkspace(), _controller.getGuiSettings().externalEditorAvailable());
                }

                if (_controller.getMode() == FlowController.Mode.EDIT_FEEDER_EXTERNAL) {
                    result = new FeedersTasksModel(_controller.getWorkspace(), _controller.getGuiSettings().externalEditorAvailable());
                }

                if (_controller.getMode() == FlowController.Mode.RESET_FEEDER) {
                    result = new FeedersTasksModel(_controller.getWorkspace(), _controller.getGuiSettings().externalEditorAvailable());
                }
            }

            return result;
        } catch (Exception e) {
            throw new ModelHandlerException(e);
        }
    }
}
