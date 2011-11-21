package app.cli.command;

import app.api.ApiFacade;
import greader.profile.FeedConfiguration;
import greader.profile.Profile;
import greader.profile.Profiles;
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
            Profiles profiles = this.facade.getRegisteredGoogleReaderProfiles();

            this.log.info(String.format("There is(are) [ %d ] registered Google Reader profile(s) found", profiles.getProfiles().size()));

            for (Profile current : profiles.getProfiles()) {
                dumpProfile(current);
            }

            this.log.info("Google Reader registered profiles dumped");
        } catch (ApiFacade.FatalException e) {
            throw new CommandExecutionException(e);
        }
    }

    private void dumpProfile(final Profile _profile) {
        this.log.info(" ");
        this.log.info(String.format("Profile email : [ %s ] feed(s) count : [ %d ]", _profile.getAccount().getEmail(), _profile.getFeedConfigurations().size()));

        for (FeedConfiguration feedConfiguration : _profile.getFeedConfigurations()) {
            this.log.info(" ");
            this.log.info(String.format("Feed URL : [ %s ]", feedConfiguration.getUrl()));
            this.log.info(String.format("Cover image URL :  [ %s ]", feedConfiguration.getCoverUrl()));
            this.log.info(String.format("Auto content filtering is : [ %s ]", String.valueOf(feedConfiguration.isAutoContentFiltering())));
            this.log.info(String.format("Content filtering criterions : [ %s ]", feedConfiguration.getCriterions()));
            this.log.info(String.format("Stored in branch : [ %s ]", feedConfiguration.getBranch()));
            this.log.info(String.format("Output file name : [ %s ]", feedConfiguration.getName()));
            this.log.info(String.format("Rewrite mode is : [ %s ]", feedConfiguration.isRewrite()));
        }

        this.log.info(" ");
    }

}