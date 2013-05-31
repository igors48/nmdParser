package app.cli.command;

import app.api.ApiFacade;
import constructor.dom.locator.Mask;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

/**
 * Команда даления служебных файлов настроек
 *
 * @author Igor Usenko
 *         Date: 16.11.2009
 */
public class RemoveServiceFilesCommand implements Command {

    private final Mask mask;
    private final ApiFacade facade;

    private final Log log;

    public RemoveServiceFilesCommand(final Mask _mask, final ApiFacade _facade) {
        Assert.notNull(_mask, "Mask is null");
        this.mask = _mask;

        Assert.notNull(_facade, "Facade is null");
        this.facade = _facade;

        this.log = LogFactory.getLog(getClass());
    }

    public void execute() throws CommandExecutionException {

        try {
            this.facade.removeServiceFiles(this.mask);
            this.log.info("Service files removed successfully");
        } catch (ApiFacade.FatalException e) {
            throw new CommandExecutionException(e);
        }
    }
}