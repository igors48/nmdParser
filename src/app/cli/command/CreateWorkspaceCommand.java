package app.cli.command;

import app.api.ApiFacade;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

/**
 *  оманда создани€ рабочего пространства
 *
 * @author Igor Usenko
 *         Date: 19.04.2009
 */
public class CreateWorkspaceCommand implements Command {

    private final ApiFacade facade;
    private final String id;

    private final Log log;

    public CreateWorkspaceCommand(final String _id, final ApiFacade _facade) {
        Assert.isValidString(_id, "Workspace id is not valid.");
        Assert.notNull(_facade, "Api facade is null.");

        this.id = _id;
        this.facade = _facade;

        this.log = LogFactory.getLog(getClass());
    }

    public void execute() throws CommandExecutionException {

        try {
            this.facade.createWorkspace(this.id);
            this.log.info("Workspace [ " + this.id + " ] created successfully.");
        } catch (ApiFacade.FatalException e) {
            throw new CommandExecutionException(e);
        }
    }
}
