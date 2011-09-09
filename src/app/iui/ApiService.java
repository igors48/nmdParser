package app.iui;

import app.api.ApiFacade;
import app.iui.command.*;
import app.iui.entity.Entity;
import app.iui.script.LoadWorkspaceAndFeedersScript;
import util.Assert;

import java.util.List;
import java.util.Map;

/**
 * @author Igor Usenko
 *         Date: 05.12.2010
 */
public class ApiService {

    private final ApiFacade api;
    private final CommandExecutor executor;
    private final GuiSettings settings;

    public ApiService(final ApiFacade _api, final CommandExecutor _executor, final GuiSettings _settings) {
        Assert.notNull(_api, "API is null");
        this.api = _api;

        Assert.notNull(_executor, "Executor is null");
        this.executor = _executor;

        Assert.notNull(_settings, "Settings is null");
        this.settings = _settings;
    }

    public LoadWorkspaceCommand loadWorkspace(final String _workspace, final LoadWorkspaceCommand.Listener _listener) {
        Assert.isValidString(_workspace, "Workspace is not valid");
        Assert.notNull(_listener, "Listener is null");

        return new LoadWorkspaceCommand(this.api, _workspace, _listener);
    }

    public LoadFeedersCommand loadFeeders(final LoadFeedersCommand.Listener _listener) {
        Assert.notNull(_listener, "Listener is null");

        return new LoadFeedersCommand(this.api, _listener);
    }

    public LoadWorkspaceAndFeedersScript loadWorkspaceAndFeeders(final String _workspace, final LoadWorkspaceAndFeedersScript.Listener _listener) {
        Assert.isValidString(_workspace, "Workspace is not valid");
        Assert.notNull(_listener, "Listener is null");

        return new LoadWorkspaceAndFeedersScript(_workspace, this, executor, _listener);
    }

    public UpdateFeederCommand updateFeeder(final Entity _entity, final int _forcedPeriod, final Map<String, String> _context, final UpdateFeederCommand.Listener _listener) {
        Assert.notNull(_entity, "Entity is null");
        Assert.notNull(_listener, "Listener is null");
        Assert.greaterOrEqual(_forcedPeriod, -1, "Forced period < -1");

        ExternalConverterContext externalConverterContext = this.settings.externalPostprocessorAvailable() ?
                new ExternalConverterContext(this.settings.getExternalPostprocessorPath(), this.settings.getExternalPostprocessorCommandLinePattern(), this.settings.getExternalPostprocessorTimeout()) :
                new ExternalConverterContext();

        return new UpdateFeederCommand(this.api, _entity, _forcedPeriod, _context, externalConverterContext, _listener);
    }

    public ViewFilesExternalCommand viewFilesExternal(final List<String> _files, final ViewFilesExternalCommand.Listener _listener) {
        Assert.notNull(_files, "Files is null");
        Assert.notNull(_listener, "Listener is null");

        return new ViewFilesExternalCommand(_files, this.settings.getPathToExternalFb2Viewer(), this.settings.getOneFb2ViewerPerOneFileMode(), _listener);
    }
}
