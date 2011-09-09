package app.iui.flow.handlers;

import app.iui.flow.FlowController;
import app.iui.flow.Model;
import app.iui.flow.ModelHandlerException;
import app.iui.flow.models.AutoUpdateFeedersModel;
import app.iui.flow.models.NotImplementedModel;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 08.05.2011
 */
public class AutoUpdateFeedersModelHandler {

    public Model handle(final AutoUpdateFeedersModel _model, final FlowController _controller) throws ModelHandlerException {
        Assert.notNull(_model, "Model is null");
        Assert.notNull(_controller, "Controller is null");

        return new NotImplementedModel();
    }
}
