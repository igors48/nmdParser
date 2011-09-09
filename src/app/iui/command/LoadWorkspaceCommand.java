package app.iui.command;

import app.api.ApiFacade;
import util.Assert;

import java.text.MessageFormat;

/**
 * @author Igor Usenko
 *         Date: 05.12.2010
 */
public class LoadWorkspaceCommand implements Command {

    private final ApiFacade api;
    private final String workspace;
    private final Listener listener;

    public LoadWorkspaceCommand(final ApiFacade _api, final String _workspace, final Listener _listener) {
        Assert.notNull(_api, "API is null");
        this.api = _api;

        Assert.isValidString(_workspace, "Workspace is invalid");
        this.workspace = _workspace;

        Assert.notNull(_listener, "Listener is null");
        this.listener = _listener;
    }

    public String toString() {
        return MessageFormat.format("iui_LoadWorkspace({0})", this.workspace);
    }

    public void execute() {

        try {
            this.api.loadWorkspace(this.workspace);

            this.listener.complete(this);
        } catch (ApiFacade.FatalException e) {
            this.listener.fault(this, e);
        }
    }

    public interface Listener {
        void complete(LoadWorkspaceCommand _command);

        void fault(LoadWorkspaceCommand _command, Throwable _cause);
    }
}
