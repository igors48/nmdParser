package app.cli.command;

import app.api.ApiFacade;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

/**
 * Команда загрузки комплекта установок
 *
 * @author Igor Usenko
 *         Date: 15.04.2009
 */
public class LoadSettingsCommand implements Command {

    private final ApiFacade facade;
    private final String id;

    private final Log log;

    public LoadSettingsCommand(final String _id, final ApiFacade _facade) {
        Assert.isValidString(_id, "Settings id is not valid.");
        Assert.notNull(_facade, "Api facade is null.");

        this.id = _id;
        this.facade = _facade;

        this.log = LogFactory.getLog(getClass());
    }

    public void execute() throws CommandExecutionException {

        try {
            this.facade.loadSettings(this.id);
            this.log.info("Settings [ " + this.id + " ] successfully loaded.");
        } catch (ApiFacade.FatalException e) {
            throw new CommandExecutionException(e);
        }
    }
}
