package app.cli.command;

import app.api.ApiFacade;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 23.08.2011
 */
public class GoogleReaderUpdateProfileCommand implements Command {

    private final ApiFacade facade;
    private final String email;

    private final Log log;

    public GoogleReaderUpdateProfileCommand(final String _email, final ApiFacade _facade) {
        Assert.isValidString(_email, "Email is not valid");
        this.email = _email;

        Assert.notNull(_facade, "API facade is null");
        this.facade = _facade;

        this.log = LogFactory.getLog(getClass());
    }

    public void execute() throws CommandExecutionException {

        try {
            this.facade.updateGoogleReaderProfile(this.email);
            this.log.info(String.format("Google Reader profile items for email [ %s ] updated", this.email));
        } catch (ApiFacade.FatalException e) {
            throw new CommandExecutionException(e);
        }
    }
    
}