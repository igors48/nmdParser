package app.cli.command;

import app.api.ApiFacade;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 23.08.2011
 */
public class GoogleReaderChangeProfilePasswordCommand implements Command {

    private final ApiFacade facade;
    private final String email;
    private final String newPassword;

    private final Log log;

    public GoogleReaderChangeProfilePasswordCommand(final String _email, final String _newPassword, final ApiFacade _facade) {
        Assert.isValidString(_email, "Email is not valid");
        this.email = _email;

        Assert.isValidString(_newPassword, "Password is not valid");
        this.newPassword = _newPassword;

        Assert.notNull(_facade, "API facade is null");
        this.facade = _facade;

        this.log = LogFactory.getLog(getClass());
    }

    public void execute() throws CommandExecutionException {

        try {
            this.facade.changeGoogleReaderProfilePassword(this.email, this.newPassword);
            this.log.info(String.format("Google Reader profile password for email [ %s ] changed", this.email));
        } catch (ApiFacade.FatalException e) {
            throw new CommandExecutionException(e);
        }
    }
    
}