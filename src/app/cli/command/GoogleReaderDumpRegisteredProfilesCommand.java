package app.cli.command;

import app.api.ApiFacade;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 23.08.2011
 */
public class GoogleReaderDumpRegisteredProfilesCommand implements Command {

    private final ApiFacade facade;

    private final Log log;

    public GoogleReaderDumpRegisteredProfilesCommand(final ApiFacade _facade) {
        Assert.notNull(_facade, "API facade is null");
        this.facade = _facade;

        this.log = LogFactory.getLog(getClass());
    }

    public void execute() throws CommandExecutionException {

        try {
            this.facade.dumpRegisteredGoogleReaderProfile();
            this.log.info("Google Reader registered profiles dumped");
        } catch (ApiFacade.FatalException e) {
            throw new CommandExecutionException(e);
        }
    }
}