package app.cli.command;

import app.api.ApiFacade;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

/**
 *  оманда загрузки рабочего пространства
 *
 * @author Igor Usenko
 *         Date: 15.04.2009
 */
public class LoadWorkspaceCommand implements Command {

    private final ApiFacade facade;
    private final String id;

    private final Log log;

    public LoadWorkspaceCommand(final String _id, final ApiFacade _facade) {
        Assert.isValidString(_id, "Workspace id is not valid.");
        Assert.notNull(_facade, "Api facade is null.");

        this.id = _id;
        this.facade = _facade;

        this.log = LogFactory.getLog(getClass());
    }

    public void execute() throws CommandExecutionException {

        try {
            this.facade.loadWorkspace(this.id);
            this.log.info("Workspace [ " + this.id + " ] successfully loaded.");
        } catch (ApiFacade.FatalException e) {
            throw new CommandExecutionException(e);
        }
    }
    
}

