package app.cli.command;

import app.api.ApiFacade;
import app.cli.blitz.request.BlitzRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

/**
 * Команда обработки блиц запроса
 *
 * @author Igor Usenko
 *         Date: 31.10.2009
 */
public class BlitzRequestCommand implements Command {
    private final BlitzRequest request;
    private final ApiFacade facade;
    private final int forcedDays;

    private final Log log;

    public BlitzRequestCommand(final BlitzRequest _request, final int _forcedDays, final ApiFacade _facade) {
        Assert.notNull(_request, "Request is null");
        this.request = _request;

        Assert.notNull(_facade, "Facade is null");
        this.facade = _facade;

        this.forcedDays = _forcedDays;

        this.log = LogFactory.getLog(getClass());
    }

    public void execute() throws CommandExecutionException {

        try {
            this.facade.processBlitzRequest(this.request, this.forcedDays);
            this.log.info("Blitz request completed");
        } catch (ApiFacade.FatalException e) {
            throw new CommandExecutionException(e);
        }
    }

}
