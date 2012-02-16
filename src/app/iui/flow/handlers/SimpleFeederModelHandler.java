package app.iui.flow.handlers;

import app.iui.flow.FlowController;
import app.iui.flow.Model;
import app.iui.flow.ModelHandlerException;
import app.iui.flow.models.FeedersTasksModel;
import app.iui.flow.models.LoadingWorkspaceModel;
import app.iui.flow.models.SimpleFeederModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 22.12.2010
 */
public class SimpleFeederModelHandler {

    private final Log log;

    public SimpleFeederModelHandler() {
        this.log = LogFactory.getLog(getClass());
    }

    public Model handle(final SimpleFeederModel _model, final FlowController _controller) throws ModelHandlerException {
        Assert.notNull(_model, "Model is null");
        Assert.notNull(_controller, "Controller is null");

        try {
            Model result = new FeedersTasksModel(_controller.getWorkspace(), _controller.getGuiSettings().externalEditorAvailable());

            if (_model.isApproved()) {
                _controller.getApi().storeSimplerConfiguration(_model.getConfiguration());
                _controller.setFeederTestCandidate(_model.getConfiguration().getId());

                result = new LoadingWorkspaceModel(_controller.getWorkspace());
            }

            return result;
        } catch (Exception e) {
            throw new ModelHandlerException(e);
        }
    }
}
