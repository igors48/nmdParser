package app.iui.flow.handlers;

import app.iui.flow.FlowController;
import app.iui.flow.Model;
import app.iui.flow.ModelHandlerException;
import app.iui.flow.models.*;
import constructor.objects.simpler.configuration.SimplerConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 22.12.2010
 */
public class FeedersTasksModelHandler {

    private static final String EDIT_SIMPLE_FEEDER_TASK_NAME = "iui.edit.simple.feeder.task.name";
    private static final String NEXT_TO_EDIT_SIMPLE_FEEDER_TASK_NAME = "iui.next.to.edit.simple.feeder.task.name";

    private static final String DELETE_SIMPLE_FEEDER_TASK_NAME = "iui.delete.simple.feeder.task.name";
    private static final String NEXT_TO_DELETE_SIMPLE_FEEDER_TASK_NAME = "iui.next.to.delete.simple.feeder.task.name";

    private static final String EDIT_FEEDER_EXTERNAL_TASK_NAME = "iui.edit.feeder.external.task.name";
    private static final String NEXT_TO_EDIT_FEEDER_EXTERNAL_TASK_NAME = "iui.next.to.edit.feeder.external.task.name";

    private static final String RESET_FEEDER_TASK_NAME = "iui.reset.feeder.task.name";
    private static final String NEXT_TO_RESET_FEEDER_TASK_NAME = "iui.next.to.reset.feeder.task.name";

    private final Log log;

    public FeedersTasksModelHandler() {
        this.log = LogFactory.getLog(getClass());
    }

    public Model handle(final FeedersTasksModel _model, final FlowController _controller) throws ModelHandlerException {
        Assert.notNull(_model, "Model is null");
        Assert.notNull(_controller, "Controller is null");

        try {
            Model result = new NotImplementedModel();

            if (_model.isApproved()) {

                switch (_model.getTask()) {
                    case CREATE_SIMPLE_FEEDER: {
                        _controller.setMode(FlowController.Mode.CREATE_SIMPLE_FEEDER);

                        result = new SimpleFeederModel(SimplerConfiguration.getDefault(), true);

                        break;
                    }
                    case EDIT_SIMPLE_FEEDER: {
                        _controller.setMode(FlowController.Mode.EDIT_SIMPLE_FEEDER);

                        result = new ChooseFeedersModel(false, _controller.filterSimpleFeeders(), _controller.getWorkspace(), _controller.getString(EDIT_SIMPLE_FEEDER_TASK_NAME), _controller.getString(NEXT_TO_EDIT_SIMPLE_FEEDER_TASK_NAME));

                        break;
                    }
                    case DELETE_SIMPLE_FEEDER: {
                        _controller.setMode(FlowController.Mode.DELETE_SIMPLE_FEEDER);

                        result = new ChooseFeedersModel(false, _controller.filterSimpleFeeders(), _controller.getWorkspace(), _controller.getString(DELETE_SIMPLE_FEEDER_TASK_NAME), _controller.getString(NEXT_TO_DELETE_SIMPLE_FEEDER_TASK_NAME));

                        break;
                    }
                    case EDIT_FEEDER_EXTERNAL: {
                        _controller.setMode(FlowController.Mode.EDIT_FEEDER_EXTERNAL);

                        result = new ChooseFeedersModel(false, _controller.getFeeders(), _controller.getWorkspace(), _controller.getString(EDIT_FEEDER_EXTERNAL_TASK_NAME), _controller.getString(NEXT_TO_EDIT_FEEDER_EXTERNAL_TASK_NAME));

                        break;
                    }
                    case RESET_FEEDER: {
                        _controller.setMode(FlowController.Mode.RESET_FEEDER);

                        result = new ChooseFeedersModel(false, _controller.getFeeders(), _controller.getWorkspace(), _controller.getString(RESET_FEEDER_TASK_NAME), _controller.getString(NEXT_TO_RESET_FEEDER_TASK_NAME));

                        break;
                    }
                }

            } else {
                result = new MainTasksModel();
            }

            return result;
        } catch (Throwable t) {
            throw new ModelHandlerException(t);
        }
    }
}
