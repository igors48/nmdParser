package app.iui.flow.handlers;

import app.iui.entity.Entity;
import app.iui.flow.FlowController;
import app.iui.flow.Model;
import app.iui.flow.ModelHandlerException;
import app.iui.flow.models.ChooseFeedersModel;
import app.iui.flow.models.ChooseFetchModeModel;
import app.iui.flow.models.MainTasksModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 22.12.2010
 */
public class ChooseFetchModeModelHandler {

    private static final String STANDARD_UPDATE_TASK_NAME = "iui.standard.update.task.name";
    private static final String FORCED_UPDATE_TASK_NAME = "iui.forced.update.task.name";
    private static final String PARAMETRIZED_UPDATE_TASK_NAME = "iui.parametrized.update.task.name";

    private static final String STANDARD_AFTER_CHOOSE_FEEDERS_TASK_NAME = "iui.standard.update.after.choose.task.name";
    private static final String FORCED_AFTER_CHOOSE_FEEDERS_TASK_NAME = "iui.forced.update.after.choose.task.name";
    private static final String PARAMETRIZED_AFTER_CHOOSE_FEEDERS_TASK_NAME = "iui.parametrized.update.after.choose.task.name";

    private static final int STANDARD_UPDATE_PERIOD = -1;

    private final Log log;

    public ChooseFetchModeModelHandler() {
        this.log = LogFactory.getLog(getClass());
    }

    public Model handle(final ChooseFetchModeModel _model, final FlowController _controller) throws ModelHandlerException {
        Assert.notNull(_model, "Model is null");
        Assert.notNull(_controller, "Controller is null");

        try {
            Model result;

            if (_model.isApproved()) {
                _controller.setFetchMode(_model.getMode());
                _controller.setForcedPeriod(STANDARD_UPDATE_PERIOD);
                _controller.setContext(new HashMap<String, String>());

                result = new ChooseFeedersModel(isMultiSelectFeedersPossible(_model.getMode()),
                        filterForFetchMode(_controller.getFeeders(), _model.getMode()),
                        _controller.getWorkspace(),
                        getUpdateTaskName(_controller, _model.getMode()),
                        getNextAfterChooseFeedersTaskName(_controller, _model.getMode()));
            } else {
                result = new MainTasksModel();
            }

            return result;
        } catch (Throwable t) {
            throw new ModelHandlerException(t);
        }
    }

    private String getUpdateTaskName(final FlowController _controller, final ChooseFetchModeModel.FetchMode _fetchMode) {
        String result = "";

        switch (_fetchMode) {
            case STANDARD: {
                result = STANDARD_UPDATE_TASK_NAME;
                break;
            }
            case FORCED: {
                result = FORCED_UPDATE_TASK_NAME;
                break;
            }
            case PARAMETRIZED: {
                result = PARAMETRIZED_UPDATE_TASK_NAME;
                break;
            }
        }

        return _controller.getString(result);
    }

    private String getNextAfterChooseFeedersTaskName(final FlowController _controller, final ChooseFetchModeModel.FetchMode _fetchMode) {
        String result = "";

        switch (_fetchMode) {
            case STANDARD: {
                result = STANDARD_AFTER_CHOOSE_FEEDERS_TASK_NAME;
                break;
            }
            case FORCED: {
                result = FORCED_AFTER_CHOOSE_FEEDERS_TASK_NAME;
                break;
            }
            case PARAMETRIZED: {
                result = PARAMETRIZED_AFTER_CHOOSE_FEEDERS_TASK_NAME;
                break;
            }
        }

        return _controller.getString(result);
    }

    private boolean isMultiSelectFeedersPossible(final ChooseFetchModeModel.FetchMode _fetchMode) {
        return _fetchMode != ChooseFetchModeModel.FetchMode.PARAMETRIZED;
    }

    private List<Entity> filterForFetchMode(final List<Entity> _feeders, final ChooseFetchModeModel.FetchMode _fetchMode) {
        List<Entity> result = new ArrayList<Entity>();

        for (Entity candidate : _feeders) {

            if (_fetchMode == ChooseFetchModeModel.FetchMode.PARAMETRIZED && candidate.isPlaceholdersUsed()) {
                result.add(candidate);
            }

            if (_fetchMode != ChooseFetchModeModel.FetchMode.PARAMETRIZED && !candidate.isPlaceholdersUsed()) {
                result.add(candidate);
            }
        }

        return result;
    }

}
