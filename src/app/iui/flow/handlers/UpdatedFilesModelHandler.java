package app.iui.flow.handlers;

import app.iui.entity.Entity;
import app.iui.flow.FlowController;
import app.iui.flow.Model;
import app.iui.flow.ModelHandlerException;
import app.iui.flow.ModelType;
import app.iui.flow.models.MainTasksModel;
import app.iui.flow.models.MessageModel;
import app.iui.flow.models.SimpleFeederModel;
import app.iui.flow.models.UpdatedFilesModel;
import constructor.objects.simpler.configuration.SimplerConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 23.12.2010
 */
public class UpdatedFilesModelHandler {

    private final Log log;

    public UpdatedFilesModelHandler() {
        this.log = LogFactory.getLog(getClass());
    }

    public Model handle(final UpdatedFilesModel _model, final FlowController _controller) throws ModelHandlerException {
        Assert.notNull(_model, "Model is null");
        Assert.notNull(_controller, "Controller is null");

        try {
            Model result = new MainTasksModel();

            switch (_model.getReturnModel()) {
                case CHOOSE_MAIN_TASK: {
                    result = new MainTasksModel();
                    break;
                }
                case SIMPLE_FEEDER: {
                    String testId = _controller.getFeederTestCandidate();
                    Entity testEntity = _controller.findForId(testId);

                    if (testEntity == null) {
                        //no exception - no error model
                        result = new MessageModel(_controller.getString(LoadingWorkspaceModelHandler.TEST_ENTITY_NOT_FOUND_CAPTION), _controller.getString(LoadingWorkspaceModelHandler.TEST_ENTITY_NOT_FOUND_LABEL), ModelType.CHOOSE_MAIN_TASK);
                    } else {
                        result = new SimpleFeederModel((SimplerConfiguration) testEntity.getBlank(), false);
                    }
                    break;
                }
            }

            return result;
        } catch (Exception e) {
            throw new ModelHandlerException(e);
        }
    }
}