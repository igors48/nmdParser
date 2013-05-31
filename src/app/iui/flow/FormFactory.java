package app.iui.flow;

import app.iui.ApiService;
import app.iui.StringResource;
import app.iui.command.CommandExecutor;
import app.iui.flow.forms.*;
import app.iui.flow.models.*;
import app.iui.validator.ValidatorFactory;
import util.Assert;

import javax.swing.*;

/**
 * @author Igor Usenko
 *         Date: 27.11.2010
 */
public class FormFactory {

    private static final int FORM_WIDTH = 500;
    private static final int FORM_HEIGHT = 510;

    private final ValidatorFactory validatorFactory;
    private final StringResource stringResource;
    private final ApiService apiService;
    private final CommandExecutor commandExecutor;

    private JFrame owner;

    public FormFactory(final ApiService _apiService, final CommandExecutor _commandExecutor, final ValidatorFactory _validatorFactory, final StringResource _stringResource) {
        Assert.notNull(_apiService, "API service is null");
        this.apiService = _apiService;

        Assert.notNull(_commandExecutor, "Command executor is null");
        this.commandExecutor = _commandExecutor;

        Assert.notNull(_validatorFactory, "Validator factory is null");
        this.validatorFactory = _validatorFactory;

        Assert.notNull(_stringResource, "String resourceis null");
        this.stringResource = _stringResource;
    }

    public void setOwner(final JFrame _owner) {
        Assert.notNull(_owner, "Owner is null");
        this.owner = _owner;
    }

    public AbstractForm create(final Model _model, final Object _listener) {
        Assert.notNull(_listener, "Listener is null");

        AbstractForm result = new NotImplementedForm(this.owner, FORM_WIDTH, FORM_HEIGHT, this.stringResource, new NotImplementedModel(), (NotImplementedForm.Listener) _listener);

        if (ModelType.CHOOSE_MAIN_TASK == _model.getType()) {
            result = new MainTasksForm(this.owner, FORM_WIDTH, FORM_HEIGHT, this.stringResource, (MainTasksModel) _model, (MainTasksForm.Listener) _listener);
        }

        if (ModelType.SELECT_WORKSPACE == _model.getType()) {
            result = new SelectWorkspaceForm(this.owner, FORM_WIDTH, FORM_HEIGHT, this.stringResource, (SelectWorkspaceModel) _model, (SelectWorkspaceForm.Listener) _listener);
        }

        if (ModelType.MESSAGE == _model.getType()) {
            result = new MessageForm(this.owner, FORM_WIDTH, FORM_HEIGHT, this.stringResource, (MessageModel) _model, (MessageForm.Listener) _listener);
        }

        if (ModelType.ERROR == _model.getType()) {
            result = new ErrorForm(this.owner, FORM_WIDTH, FORM_HEIGHT, this.stringResource, (ErrorModel) _model, (ErrorForm.Listener) _listener);
        }

        if (ModelType.CHOOSE_FETCH_MODE == _model.getType()) {
            result = new ChooseFetchModeForm(this.owner, FORM_WIDTH, FORM_HEIGHT, this.stringResource, (ChooseFetchModeModel) _model, (ChooseFetchModeForm.Listener) _listener);
        }

        if (ModelType.CHOOSE_FEEDERS == _model.getType()) {
            result = new ChooseFeedersForm(this.owner, FORM_WIDTH, FORM_HEIGHT, this.stringResource, (ChooseFeedersModel) _model, (ChooseFeedersForm.Listener) _listener);
        }

        if (ModelType.CHOOSE_FEEDERS_TASK == _model.getType()) {
            result = new FeedersTasksForm(this.owner, FORM_WIDTH, FORM_HEIGHT, this.stringResource, (FeedersTasksModel) _model, (FeedersTasksForm.Listener) _listener);
        }

        if (ModelType.UPDATE_FEEDER == _model.getType()) {
            result = new UpdateFeederForm(this.owner, FORM_WIDTH, FORM_HEIGHT, this.stringResource, (UpdateFeederModel) _model, this.apiService, this.commandExecutor, (UpdateFeederForm.Listener) _listener);
        }

        if (ModelType.UPDATE_FEEDERS == _model.getType()) {
            result = new UpdateFeedersForm(this.owner, FORM_WIDTH, FORM_HEIGHT, this.stringResource, (UpdateFeedersModel) _model, this.apiService, this.commandExecutor, (UpdateFeedersForm.Listener) _listener);
        }

        if (ModelType.LOADING_WORKSPACE == _model.getType()) {
            result = new LoadingWorkspaceForm(this.owner, FORM_WIDTH, FORM_HEIGHT, this.stringResource, (LoadingWorkspaceModel) _model, this.apiService, this.commandExecutor, (LoadingWorkspaceForm.Listener) _listener);
        }

        if (ModelType.UPDATED_FILES == _model.getType()) {
            result = new UpdatedFilesForm(this.owner, FORM_WIDTH, FORM_HEIGHT, this.stringResource, (UpdatedFilesModel) _model, this.apiService, this.commandExecutor, (UpdatedFilesForm.Listener) _listener);
        }

        if (ModelType.FORCED_UPDATE_PERIOD == _model.getType()) {
            result = new ForcedUpdatePeriodForm(this.owner, this.validatorFactory, FORM_WIDTH, FORM_HEIGHT, this.stringResource, (ForcedUpdatePeriodModel) _model, (ForcedUpdatePeriodForm.Listener) _listener);
        }

        if (ModelType.UPDATE_CONTEXT == _model.getType()) {
            result = new UpdateContextForm(this.owner, FORM_WIDTH, FORM_HEIGHT, this.stringResource, (UpdateContextModel) _model, (UpdateContextForm.Listener) _listener);
        }

        if (ModelType.CHOOSE_WORKSPACE_TASK == _model.getType()) {
            result = new ChooseWorkspaceTaskForm(this.owner, FORM_WIDTH, FORM_HEIGHT, this.stringResource, (ChooseWorkspaceTaskModel) _model, (ChooseWorkspaceTaskForm.Listener) _listener);
        }

        if (ModelType.EDIT_WORKSPACE_NAME == _model.getType()) {
            result = new EditWorkspaceNameForm(this.owner, this.validatorFactory, FORM_WIDTH, FORM_HEIGHT, this.stringResource, (EditWorkspaceNameModel) _model, (EditWorkspaceNameForm.Listener) _listener);
        }

        if (ModelType.DELETE_WORKSPACE_CONFIRMATION == _model.getType()) {
            result = new DeleteWorkspaceConfirmationForm(this.owner, FORM_WIDTH, FORM_HEIGHT, this.stringResource, (DeleteWorkspaceConfirmationModel) _model, (DeleteWorkspaceConfirmationForm.Listener) _listener);
        }

        if (ModelType.SIMPLE_FEEDER == _model.getType()) {
            result = new SimpleFeederForm(this.owner, this.validatorFactory, FORM_WIDTH, FORM_HEIGHT, this.stringResource, (SimpleFeederModel) _model, (SimpleFeederForm.Listener) _listener);
        }

        if (ModelType.DELETE_SIMPLE_FEEDER_CONFIRMATION == _model.getType()) {
            result = new DeleteSimpleFeederConfirmationForm(this.owner, FORM_WIDTH, FORM_HEIGHT, this.stringResource, (DeleteSimpleFeederConfirmationModel) _model, (DeleteSimpleFeederConfirmationForm.Listener) _listener);
        }

        if (ModelType.CHOOSE_FEEDER_DEBUG_ACTION == _model.getType()) {
            result = new ChooseFeederDebugActionForm(this.owner, FORM_WIDTH, FORM_HEIGHT, this.stringResource, (ChooseFeederDebugActionModel) _model, (ChooseFeederDebugActionForm.Listener) _listener);
        }

        if (ModelType.CHANGE_SETTINGS == _model.getType()) {
            result = new ChangeSettingsForm(this.owner, FORM_WIDTH, FORM_HEIGHT, this.stringResource, (ChangeSettingsModel) _model, (ChangeSettingsForm.Listener) _listener);
        }

        if (ModelType.EXTERNAL_TOOLS_SETTINGS == _model.getType()) {
            result = new ExternalToolsSettingsForm(this.owner, this.validatorFactory, FORM_WIDTH, FORM_HEIGHT, this.stringResource, (ExternalToolsModel) _model, (ExternalToolsSettingsForm.Listener) _listener);
        }

        if (ModelType.EXTERNAL_POSTPROCESSOR_SETTINGS == _model.getType()) {
            result = new ExternalPostprocessingSettingsForm(this.owner, this.validatorFactory, FORM_WIDTH, FORM_HEIGHT, this.stringResource, (ExternalPostprocessorModel) _model, (ExternalPostprocessingSettingsForm.Listener) _listener);
        }

        return result;
    }

}
