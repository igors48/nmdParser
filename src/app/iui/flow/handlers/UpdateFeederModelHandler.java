package app.iui.flow.handlers;

import app.iui.flow.FlowController;
import app.iui.flow.Model;
import app.iui.flow.ModelHandlerException;
import app.iui.flow.ModelType;
import app.iui.flow.models.ErrorModel;
import app.iui.flow.models.UpdateFeederModel;
import app.iui.flow.models.UpdatedFilesModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 23.12.2010
 */
public class UpdateFeederModelHandler {

    private static final String ERROR = "iui.error.update.feeder";

    private final Log log;

    public UpdateFeederModelHandler() {
        this.log = LogFactory.getLog(getClass());
    }

    public Model handle(final UpdateFeederModel _model, final FlowController _controller) throws ModelHandlerException {
        Assert.notNull(_model, "Model is null");
        Assert.notNull(_controller, "Controller is null");

        try {
            Model result;

            if (_model.isApproved()) {
                result = new UpdatedFilesModel(_controller.getMode() == FlowController.Mode.TEST_FEEDER, _model.getFiles(), _controller.getGuiSettings().externalFb2ViewerAvailable());
            } else {
                result = new ErrorModel(_controller.getString(ERROR), _model.getCause(), ModelType.CHOOSE_FEEDERS);
            }

            return result;
        } catch (Exception e) {
            throw new ModelHandlerException(e);
        }
    }

}
