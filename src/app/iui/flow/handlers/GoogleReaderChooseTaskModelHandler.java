package app.iui.flow.handlers;

import app.iui.flow.FlowController;
import app.iui.flow.Model;
import app.iui.flow.ModelHandlerException;
import app.iui.flow.models.GoogleReaderChooseTaskModel;
import app.iui.flow.models.NotImplementedModel;
import util.Assert;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 16.02.2012
 */
public class GoogleReaderChooseTaskModelHandler {

    public Model handle(final GoogleReaderChooseTaskModel _model, final FlowController _controller) throws ModelHandlerException {
        Assert.notNull(_model, "Model is null");
        Assert.notNull(_controller, "Controller is null");

        try {
            Model result = new NotImplementedModel();


            return result;

        } catch (Exception e) {
            throw new ModelHandlerException(e);
        }
    }

}
