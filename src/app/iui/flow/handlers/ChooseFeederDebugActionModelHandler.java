package app.iui.flow.handlers;

import app.iui.flow.FlowController;
import app.iui.flow.Model;
import app.iui.flow.ModelHandlerException;
import app.iui.flow.models.*;
import constructor.objects.simpler.configuration.SimplerConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import java.util.HashMap;

/**
 * @author Igor Usenko
 *         Date: 23.12.2010
 */
public class ChooseFeederDebugActionModelHandler {

    private final Log log;

    public ChooseFeederDebugActionModelHandler() {
        this.log = LogFactory.getLog(getClass());
    }

    public Model handle(final ChooseFeederDebugActionModel _model, final FlowController _controller) throws ModelHandlerException {
        Assert.notNull(_model, "Model is null");
        Assert.notNull(_controller, "Controller is null");

        try {
            Model result = new NotImplementedModel();

            if (_model.isApproved()) {

                switch (_model.getAction()) {
                    case TEST: {
                        result = new UpdateFeederModel(_model.getEntity(), 0, new HashMap<String, String>());
                        break;
                    }
                    case RETURN: {
                        result = new FeedersTasksModel(_controller.getWorkspace(), _controller.getGuiSettings().externalEditorAvailable());
                        break;
                    }
                }

            } else {
                _controller.setMode(FlowController.Mode.EDIT_SIMPLE_FEEDER);
                result = new SimpleFeederModel((SimplerConfiguration) _model.getEntity().getBlank(), false);
            }

            return result;
        } catch (Throwable t) {
            throw new ModelHandlerException(t);
        }
    }
}
