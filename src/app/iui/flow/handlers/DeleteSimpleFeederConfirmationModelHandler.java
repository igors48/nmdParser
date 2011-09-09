package app.iui.flow.handlers;

import app.iui.flow.FlowController;
import app.iui.flow.Model;
import app.iui.flow.ModelHandlerException;
import app.iui.flow.ModelType;
import app.iui.flow.models.DeleteSimpleFeederConfirmationModel;
import app.iui.flow.models.LoadingWorkspaceModel;
import app.iui.flow.models.MessageModel;
import constructor.objects.simpler.configuration.SimplerConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import java.text.MessageFormat;

/**
 * @author Igor Usenko
 *         Date: 22.12.2010
 */
public class DeleteSimpleFeederConfirmationModelHandler {

    private static final String TITLE = "iui.simple.feeder.deleted.title";
    private static final String MESSAGE = "iui.simple.feeder.deleted.message";

    private final Log log;

    public DeleteSimpleFeederConfirmationModelHandler() {
        this.log = LogFactory.getLog(getClass());
    }

    public Model handle(final DeleteSimpleFeederConfirmationModel _model, final FlowController _controller) throws ModelHandlerException {
        Assert.notNull(_model, "Model is null");
        Assert.notNull(_controller, "Controller is null");

        try {
            Model result;

            if (_model.isApproved()) {
                _controller.getApi().removeSimplerConfiguration((SimplerConfiguration) _model.getFeeder().getBlank());

                this.log.info(MessageFormat.format("Simple feeder [ {0} ] deleted", _model.getFeeder().getName()));

                _controller.setMode(FlowController.Mode.MANAGE_FEEDERS);
                result = new MessageModel(_controller.getString(TITLE), MessageFormat.format(_controller.getString(MESSAGE), _model.getFeeder().getName()), ModelType.LOADING_WORKSPACE);
            } else {
                _controller.setMode(FlowController.Mode.MANAGE_FEEDERS);
                result = new LoadingWorkspaceModel(_controller.getWorkspace());
            }

            return result;
        } catch (Exception t) {
            throw new ModelHandlerException(t);
        }
    }
}
