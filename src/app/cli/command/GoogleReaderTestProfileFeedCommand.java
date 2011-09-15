package app.cli.command;

import app.api.ApiFacade;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 23.08.2011
 */
public class GoogleReaderTestProfileFeedCommand implements Command {

    private final ApiFacade facade;
    private final String email;
    private final String feed;

    private final Log log;

    public GoogleReaderTestProfileFeedCommand(final String _email, final String _feed, final ApiFacade _facade) {
        Assert.isValidString(_email, "Email is not valid");
        this.email = _email;

        Assert.isValidString(_feed, "Feed is not valid");
        this.feed = _feed;

        Assert.notNull(_facade, "API facade is null");
        this.facade = _facade;

        this.log = LogFactory.getLog(getClass());
    }

    public void execute() throws CommandExecutionException {

        try {
            this.facade.testGoogleReaderProfile(this.email, this.feed);
            this.log.info(String.format("Google Reader profile items for email [ %s ] and feed [ %s ] tested", this.email, this.feed));
        } catch (ApiFacade.FatalException e) {
            throw new CommandExecutionException(e);
        }
    }
}