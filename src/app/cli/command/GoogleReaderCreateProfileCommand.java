package app.cli.command;

import app.api.ApiFacade;
import util.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 23.08.2011
 */
public class GoogleReaderCreateProfileCommand  implements Command {

    private final ApiFacade facade;
    private final String email;
    private final String password;

    private final Log log;

    public GoogleReaderCreateProfileCommand(final String _email, final String _password, final ApiFacade _facade) {
        Assert.isValidString(_email, "Email is not valid");
        this.email = _email;

        Assert.isValidString(_password, "Password is not valid");
        this.password = _password;

        Assert.notNull(_facade, "API facade is null");
        this.facade = _facade;

        this.log = LogFactory.getLog(getClass());
    }

    public void execute() throws CommandExecutionException {
        
        try {
            this.facade.createGoogleReaderProfile(this.email, this.password);
            this.log.info(String.format("Google Reader profile for email [ %s ] created", this.email));
        } catch (ApiFacade.FatalException e) {
            throw new CommandExecutionException(e);
        }
    }
}
