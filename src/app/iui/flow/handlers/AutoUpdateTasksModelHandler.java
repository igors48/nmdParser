package app.iui.flow.handlers;

import app.iui.flow.FlowController;
import app.iui.flow.Model;
import app.iui.flow.ModelHandlerException;
import app.iui.flow.models.AutoUpdateFeedersModel;
import app.iui.flow.models.AutoUpdateTasksModel;
import app.iui.flow.models.NotImplementedModel;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 08.05.2011
 */
public class AutoUpdateTasksModelHandler {

    public Model handle(final AutoUpdateTasksModel _model, final FlowController _controller) throws ModelHandlerException {
        Assert.notNull(_model, "Model is null");
        Assert.notNull(_controller, "Controller is null");

        try {
            Model result = new NotImplementedModel();

            if (_model.getTask() == AutoUpdateTasksModel.AutoUpdateTask.START) {
                result = new AutoUpdateFeedersModel(_controller.getScheduleAdapter());
            }

            return result;
        } catch (Exception t) {
            throw new ModelHandlerException(t);
        }
    }
}
