package app.iui.script;

import app.iui.ApiService;
import app.iui.command.Command;
import app.iui.command.CommandExecutor;
import app.iui.command.LoadFeedersCommand;
import app.iui.command.LoadWorkspaceCommand;
import app.iui.entity.Entity;
import util.Assert;

import java.text.MessageFormat;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 05.12.2010
 */
public class LoadWorkspaceAndFeedersScript implements Command, LoadWorkspaceCommand.Listener, LoadFeedersCommand.Listener {

    private final String workspace;
    private final ApiService service;
    private final CommandExecutor executor;
    private final Listener listener;

    private List<Entity> entities;

    public LoadWorkspaceAndFeedersScript(final String _workspace, final ApiService _service, final CommandExecutor _executor, final Listener _listener) {
        Assert.isValidString(_workspace, "Workspace is not valid");
        this.workspace = _workspace;

        Assert.notNull(_service, "Service is null");
        this.service = _service;

        Assert.notNull(_executor, "Executor is null");
        this.executor = _executor;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;

        this.entities = newArrayList();
    }

    public List<Entity> getEntities() {
        return this.entities;
    }

    public void execute() {
        this.executor.execute(this.service.loadWorkspace(this.workspace, this), false);
    }

    public String toString() {
        return MessageFormat.format("iui_LoadWorkspaceAndFeeders({0})", this.workspace);
    }

    public void complete(final LoadWorkspaceCommand _command) {
        Assert.notNull(_command, "Command is null");

        this.executor.execute(this.service.loadFeeders(this), false);
    }

    public void fault(final LoadWorkspaceCommand _command, final Throwable _cause) {
        Assert.notNull(_command, "Command is null");

        this.listener.fault(this, _cause);
    }

    public void complete(final LoadFeedersCommand _command) {
        Assert.notNull(_command, "Command is null");

        this.entities = _command.getEntities();

        this.listener.complete(this);
    }

    public void fault(final LoadFeedersCommand _command, final Throwable _cause) {
        Assert.notNull(_command, "Command is null");

        this.listener.fault(this, _cause);
    }

    public interface Listener {
        void complete(LoadWorkspaceAndFeedersScript _script);

        void fault(LoadWorkspaceAndFeedersScript _script, Throwable _cause);
    }
}
