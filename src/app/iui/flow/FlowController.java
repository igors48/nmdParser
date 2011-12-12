package app.iui.flow;

import app.api.ApiFacade;
import app.iui.GuiSettings;
import app.iui.StringResource;
import app.iui.entity.Entity;
import app.iui.flow.handlers.*;
import app.iui.flow.models.*;
import app.iui.tools.SwingTools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;
import util.UpdateContextTools;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 27.11.2010
 */
public class FlowController {

    private final StringResource stringResource;
    private final GuiSettings guiSettings;
    private final ApiFacade api;

    private final Session session;

    private String workspace;
    private ChooseFetchModeModel.FetchMode fetchMode;

    private List<Entity> feeders;
    private List<Entity> selectedFeeders;
    private int forcedPeriod;
    private Map<String, String> context;
    private String feederTestCandidate;

    private Mode mode;

    private final Log log;

    public FlowController(final StringResource _stringResource, GuiSettings _guiSettings, final ApiFacade _api) {
        Assert.notNull(_stringResource, "String resource is null");
        this.stringResource = _stringResource;

        Assert.notNull(_guiSettings, "GUI settings is null");
        this.guiSettings = _guiSettings;

        Assert.notNull(_api, "API is null");
        this.api = _api;

        this.session = new Session();

        this.log = LogFactory.getLog(getClass());
    }

    public Model handle() {
        this.session.resetHistory();

        return putToHistory(new MainTasksModel());
    }

    public Model handle(final NotImplementedModel _model) {
        Assert.notNull(_model, "Model is null");

        return handleBack();
    }

    public Model handleBack() {
        return back();
    }

    public Model handle(final MainTasksModel _model) {
        Assert.notNull(_model, "Model is null");

        try {
            return putToHistory(new MainTasksModelHandler().handle(_model, this));
        } catch (ModelHandlerException e) {
            this.log.error(e);
            return new ErrorModel(SwingTools.createErrorText(e), e, ModelType.CHOOSE_MAIN_TASK);
        }
    }

    public Model handle(final ExternalToolsModel _model) {
        Assert.notNull(_model, "Model is null");

        try {
            return putToHistory(new ExternalToolsSettingsModelHandler().handle(_model, this));
        } catch (ModelHandlerException e) {
            this.log.error(e);
            return new ErrorModel(SwingTools.createErrorText(e), e, ModelType.CHOOSE_MAIN_TASK);
        }
    }

    public Model handle(final FeedersTasksModel _model) {
        Assert.notNull(_model, "Model is null");

        try {
            return putToHistory(new FeedersTasksModelHandler().handle(_model, this));
        } catch (ModelHandlerException e) {
            this.log.error(e);
            return new ErrorModel(SwingTools.createErrorText(e), e, ModelType.CHOOSE_MAIN_TASK);
        }
    }

    public Model handle(final MessageModel _model) {
        Assert.notNull(_model, "Model is null");

        try {
            return putToHistory(new MessageModelHandler().handle(_model, this));
        } catch (ModelHandlerException e) {
            this.log.error(e);
            return new ErrorModel(SwingTools.createErrorText(e), e, ModelType.CHOOSE_MAIN_TASK);
        }
    }

    public Model handle(final SelectWorkspaceModel _model) {
        Assert.notNull(_model, "Model is null");

        try {
            return putToHistory(new SelectWorkspaceModelHandler().handle(_model, this));
        } catch (ModelHandlerException e) {
            this.log.error(e);
            return new ErrorModel(SwingTools.createErrorText(e), e, ModelType.CHOOSE_MAIN_TASK);
        }
    }

    public Model handle(final ChooseFetchModeModel _model) {
        Assert.notNull(_model, "Model is null");

        try {
            return putToHistory(new ChooseFetchModeModelHandler().handle(_model, this));
        } catch (ModelHandlerException e) {
            this.log.error(e);
            return new ErrorModel(SwingTools.createErrorText(e), e, ModelType.CHOOSE_MAIN_TASK);
        }
    }

    public Model handle(final ChooseFeedersModel _model) {
        Assert.notNull(_model, "Model is null");

        try {
            return putToHistory(new ChooseFeedersModelHandler().handle(_model, this));
        } catch (ModelHandlerException e) {
            this.log.error(e);
            return new ErrorModel(SwingTools.createErrorText(e), e, ModelType.CHOOSE_MAIN_TASK);
        }
    }

    public Model handle(final LoadingWorkspaceModel _model) {
        Assert.notNull(_model, "Model is null");

        try {
            return putToHistory(new LoadingWorkspaceModelHandler().handle(_model, this));
        } catch (ModelHandlerException e) {
            this.log.error(e);
            return new ErrorModel(SwingTools.createErrorText(e), e, ModelType.CHOOSE_MAIN_TASK);
        }
    }

    public Model handle(final ErrorModel _model) {
        Assert.notNull(_model, "Model is null");

        return _model.getBackModel() == null ? back() : putToHistory(createKeyModel(_model.getBackModel()));
    }

    public Model handle(final UpdateFeedersModel _model) {
        Assert.notNull(_model, "Model is null");

        return putToHistory(new UpdatedFilesModel(false, _model.getFiles(), this.guiSettings.externalFb2ViewerAvailable()));
    }

    public Model handle(final UpdateFeederModel _model) {
        Assert.notNull(_model, "Model is null");

        try {
            return putToHistory(new UpdateFeederModelHandler().handle(_model, this));
        } catch (ModelHandlerException e) {
            this.log.error(e);
            return new ErrorModel(SwingTools.createErrorText(e), e, ModelType.CHOOSE_MAIN_TASK);
        }
    }

    public Model handle(final UpdatedFilesModel _model) {
        Assert.notNull(_model, "Model is null");

        try {
            return putToHistory(new UpdatedFilesModelHandler().handle(_model, this));
        } catch (ModelHandlerException e) {
            this.log.error(e);
            return new ErrorModel(SwingTools.createErrorText(e), e, ModelType.CHOOSE_MAIN_TASK);
        }
    }

    public Model handle(final ForcedUpdatePeriodModel _model) {
        Assert.notNull(_model, "Model is null");

        Model result;

        if (_model.isApproved()) {
            this.forcedPeriod = _model.getDays();
            this.log.debug(MessageFormat.format("Forced period set to {0} days", this.forcedPeriod));

            result = createUpdateModel();
        } else {
            result = createKeyModel(ModelType.CHOOSE_FETCH_MODE);
        }

        return putToHistory(result);
    }


    public Model handle(final UpdateContextModel _model) {
        Assert.notNull(_model, "Model is null");

        Model result;

        if (_model.isApproved()) {
            this.context = _model.getContext();
            this.log.debug(MessageFormat.format("Update context is [ {0} ]", UpdateContextTools.contextToString(this.context)));

            result = createUpdateModel();
        } else {
            result = new ChooseFetchModeModel(this.workspace);
        }

        return putToHistory(result);
    }

    public Model handle(final ChooseWorkspaceTaskModel _model) {
        Assert.notNull(_model, "Model is null");

        try {
            return putToHistory(new ChooseWorkspaceTaskModelHandler().handle(_model, this));
        } catch (ModelHandlerException e) {
            this.log.error(e);
            return new ErrorModel(SwingTools.createErrorText(e), e, ModelType.CHOOSE_MAIN_TASK);
        }
    }

    public Model handle(final EditWorkspaceNameModel _model) {
        Assert.notNull(_model, "Model is null");

        try {
            return putToHistory(new EditWorkspaceNameModelHandler().handle(_model, this));
        } catch (ModelHandlerException e) {
            this.log.error(e);
            return new ErrorModel(SwingTools.createErrorText(e), e, ModelType.CHOOSE_MAIN_TASK);
        }
    }

    public Model handle(final DeleteWorkspaceConfirmationModel _model) {
        Assert.notNull(_model, "Model is null");

        try {
            return putToHistory(new DeleteWorkspaceConfirmationModelHandler().handle(_model, this));
        } catch (ModelHandlerException e) {
            this.log.error(e);
            return new ErrorModel(SwingTools.createErrorText(e), e, ModelType.CHOOSE_MAIN_TASK);
        }
    }

    public Model handle(final SimpleFeederModel _model) {
        Assert.notNull(_model, "Model is null");

        try {
            return putToHistory(new SimpleFeederModelHandler().handle(_model, this));
        } catch (ModelHandlerException e) {
            this.log.error(e);
            return new ErrorModel(SwingTools.createErrorText(e), e, ModelType.CHOOSE_MAIN_TASK);
        }
    }

    public Model handle(final DeleteSimpleFeederConfirmationModel _model) {
        Assert.notNull(_model, "Model is null");

        try {
            return putToHistory(new DeleteSimpleFeederConfirmationModelHandler().handle(_model, this));
        } catch (ModelHandlerException e) {
            this.log.error(e);
            return new ErrorModel(SwingTools.createErrorText(e), e, ModelType.CHOOSE_MAIN_TASK);
        }
    }

    public Model handle(final ChooseFeederDebugActionModel _model) {
        Assert.notNull(_model, "Model is null");

        try {
            return putToHistory(new ChooseFeederDebugActionModelHandler().handle(_model, this));
        } catch (ModelHandlerException e) {
            this.log.error(e);
            return new ErrorModel(SwingTools.createErrorText(e), e, ModelType.CHOOSE_MAIN_TASK);
        }
    }

    public Model handle(final ChangeSettingsModel _model) {
        Assert.notNull(_model, "Model is null");

        try {
            return putToHistory(new ChangeSettingsModelHandler().handle(_model, this));
        } catch (ModelHandlerException e) {
            this.log.error(e);
            return new ErrorModel(SwingTools.createErrorText(e), e, ModelType.CHOOSE_MAIN_TASK);
        }
    }

    public Model handle(final ExternalPostprocessorModel _model) {
        Assert.notNull(_model, "Model is null");

        try {
            return putToHistory(new ExternalPostprocessorModelHandler().handle(_model, this));
        } catch (ModelHandlerException e) {
            this.log.error(e);
            return new ErrorModel(SwingTools.createErrorText(e), e, ModelType.CHOOSE_MAIN_TASK);
        }
    }

    public String getString(final String _key) {
        Assert.isValidString(_key, "Key is not valid");

        return this.stringResource.getString(_key);
    }

    public ApiFacade getApi() {
        return this.api;
    }

    public GuiSettings getGuiSettings() {
        return this.guiSettings;
    }

    public void setMode(final Mode _mode) {
        this.log.debug(MessageFormat.format("Mode set to [ {0} ]", _mode));
        this.mode = _mode;
    }

    public Mode getMode() {
        return this.mode;
    }

    public String getWorkspace() {
        return this.workspace;
    }

    public void setWorkspace(final String _workspace) {
        Assert.isValidString(_workspace, "Workspace is not valid");
        this.workspace = _workspace;
        this.log.debug(MessageFormat.format("Select workspace [ {0} ]", this.workspace));
    }

    public List<Entity> getFeeders() {
        return this.feeders;
    }

    public void setFeeders(final List<Entity> _feeders) {
        Assert.notNull(_feeders, "Feeders is null");
        this.feeders = _feeders;
        this.log.debug(MessageFormat.format("Found [ {0} ] feeders", this.feeders.size()));
    }

    public ChooseFetchModeModel.FetchMode getFetchMode() {
        return this.fetchMode;
    }

    public void setFetchMode(final ChooseFetchModeModel.FetchMode _fetchMode) {
        Assert.notNull(_fetchMode, "Fetch mode is null");
        this.fetchMode = _fetchMode;
        this.log.debug(MessageFormat.format("Fetch mode set to [ {0} ]", this.fetchMode));
    }

    public Map<String, String> getContext() {
        return this.context;
    }

    public void setContext(final Map<String, String> _context) {
        Assert.notNull(_context, "Context is null");
        this.context = _context;
    }

    public int getForcedPeriod() {
        return this.forcedPeriod;
    }

    public void setForcedPeriod(final int _forcedPeriod) {
        Assert.greaterOrEqual(_forcedPeriod, -1, "Forced period < -1");
        this.forcedPeriod = _forcedPeriod;
    }

    public List<Entity> getSelectedFeeders() {
        return this.selectedFeeders;
    }

    public void setSelectedFeeders(final List<Entity> _selectedFeeders) {
        Assert.notNull(_selectedFeeders, "Selected feeders is null");
        this.selectedFeeders = _selectedFeeders;
        this.log.debug(MessageFormat.format("Selected [ {0} ] feeders", this.selectedFeeders.size()));
    }

    public void setFeederTestCandidate(final String _id) {
        Assert.isValidString(_id, "Id is not valid");

        setMode(Mode.TEST_FEEDER);
        this.feederTestCandidate = _id;
        this.log.debug(MessageFormat.format("Set test candidate id [ {0} ]", _id));
    }

    public String getFeederTestCandidate() {
        return this.feederTestCandidate;
    }

    public Model createUpdateModel() {
        return this.selectedFeeders.size() == 1 ? new UpdateFeederModel(this.selectedFeeders.get(0), this.forcedPeriod, this.context) : new UpdateFeedersModel(this.selectedFeeders, this.forcedPeriod, this.context);
    }

    public List<Entity> filterSimpleFeeders() {
        List<Entity> result = newArrayList();

        for (Entity candidate : this.feeders) {

            if (candidate.isSimpler()) {
                result.add(candidate);
            }
        }

        return result;
    }

    public Entity findForId(final String _id) {
        Entity result = null;

        Iterator<Entity> iterator = this.feeders.iterator();

        while (iterator.hasNext() && result == null) {
            Entity candidate = iterator.next();

            if (candidate.getName().equals(_id)) {
                result = candidate;
            }
        }

        return result;
    }

    private Model putToHistory(final Model _model) {
        this.session.putToHistory(_model);

        return _model;
    }

    private Model back() {
        return this.session.pollFromHistory();
    }

    private Model createKeyModel(final ModelType _type) {
        Model result = new NotImplementedModel();

        switch (_type) {
            case CHOOSE_MAIN_TASK: {
                result = new MainTasksModel();
                break;
            }
            case CHOOSE_FETCH_MODE: {
                result = new ChooseFetchModeModel(this.workspace);
                break;
            }
            case CHOOSE_WORKSPACE_TASK: {
                result = new ChooseWorkspaceTaskModel();
                break;
            }
        }

        return result;
    }

    public enum Mode {
        UPDATE_FEEDERS,
        AUTO_UPDATE_FEEDERS,
        MANAGE_FEEDERS,
        CREATE_WORKSPACE,
        RENAME_WORKSPACE,
        DELETE_WORKSPACE,
        CREATE_SIMPLE_FEEDER,
        EDIT_SIMPLE_FEEDER,
        DELETE_SIMPLE_FEEDER,
        TEST_FEEDER,
        EDIT_FEEDER_EXTERNAL,
        RESET_FEEDER
    }
}
