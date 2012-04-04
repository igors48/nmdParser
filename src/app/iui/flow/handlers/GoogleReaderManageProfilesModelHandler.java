package app.iui.flow.handlers;

import app.iui.flow.FlowController;
import app.iui.flow.Model;
import app.iui.flow.ModelHandlerException;
import app.iui.flow.models.GoogleReaderChooseTaskModel;
import app.iui.flow.models.GoogleReaderManageProfilesModel;
import app.iui.flow.models.NotImplementedModel;
import util.Assert;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 04.04.12
 */
public class GoogleReaderManageProfilesModelHandler {

    public Model handle(final GoogleReaderManageProfilesModel _model, final FlowController _controller) throws ModelHandlerException {
        Assert.notNull(_model, "Model is null");
        Assert.notNull(_controller, "Controller is null");

        try {
            Model result = new NotImplementedModel();

            if (_model.isApproved()) {

                switch (_model.getTask()) {
                    case ADD_PROFILE: {
                        break;
                    }
                    case EDIT_PROFILE: {
                        break;
                    }
                    case DELETE_PROFILE: {
                        break;
                    }
                }
            } else {
                result = new GoogleReaderChooseTaskModel();
            }

            return result;

        } catch (Exception e) {
            throw new ModelHandlerException(e);
        }
    }

}
