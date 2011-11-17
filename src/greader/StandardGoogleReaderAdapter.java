package greader;

import app.api.ApiFacade;
import app.cli.blitz.BlitzRequestHandler;
import app.cli.blitz.request.BlitzRequest;
import dated.item.modification.Modification;
import greader.entities.FeedItem;
import greader.entities.Subscription;
import greader.profile.*;
import greader.tools.GoogleReaderAdapterTools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 23.08.2011
 */
public class StandardGoogleReaderAdapter implements GoogleReaderAdapter {

    private final GoogleReaderProvider provider;
    private final ProfilesStorage profilesStorage;

    private final Log log;

    public StandardGoogleReaderAdapter(final GoogleReaderProvider _provider, final ProfilesStorage _profilesStorage) {
        Assert.notNull(_provider, "Provider is null");
        this.provider = _provider;

        Assert.notNull(_profilesStorage, "Profiles storage is null");
        this.profilesStorage = _profilesStorage;

        this.log = LogFactory.getLog(getClass());
    }

    public void createProfile(final String _email, final String _password) throws GoogleReaderAdapterException {
        Assert.isValidString(_email, "Email is not valid");
        Assert.isValidString(_password, "Password is not valid");

        try {
            Profiles profiles = this.profilesStorage.load();

            validate(_email, _password);

            if (profiles.profileExists(_email)) {
                throw new GoogleReaderAdapterException(String.format("Account [ %s ] already exists", _email));
            } else {
                Profile profile = profiles.addProfile(_email, _password);
                List<Subscription> subscriptions = this.provider.getSubscriptions(profile.getAccount());

                GoogleReaderAdapterTools.synchronize(profile.getFeedConfigurations(), subscriptions);
            }

            this.profilesStorage.store(profiles);

        } catch (ProfilesStorage.ProfilesStorageException e) {
            throw new GoogleReaderAdapterException(e);
        } catch (GoogleReaderProvider.GoogleReaderProviderException e) {
            throw new GoogleReaderAdapterException(e);
        }
    }

    public void removeProfile(final String _email) throws GoogleReaderAdapterException {
        Assert.isValidString(_email, "Email is not valid");

        try {
            Profiles profiles = this.profilesStorage.load();

            profiles.removeProfile(_email);

            this.profilesStorage.store(profiles);
        } catch (ProfilesStorage.ProfilesStorageException e) {
            throw new GoogleReaderAdapterException(e);
        }
    }

    public void updateProfile(final String _email, final BlitzRequestHandler _handler) throws GoogleReaderAdapterException {
        Assert.isValidString(_email, "Email is not valid");
        Assert.notNull(_handler, "Handler is null");

        Profile profile = synchronizeProfile(_email);

        validate(profile.getAccount().getEmail(), profile.getAccount().getPassword());

        try {

            for (FeedConfiguration configuration : profile.getFeedConfigurations()) {
                this.log.info(String.format("Processing feed [ %s ] from profile [ %s ]", configuration.getUrl(), _email));

                updateFeed(_handler, profile, configuration);

                this.provider.markAllFeedItemsAsRead(profile.getAccount(), configuration.getUrl());
            }

        } catch (GoogleReaderProvider.GoogleReaderProviderException e) {
            throw new GoogleReaderAdapterException(e);
        } catch (ApiFacade.FatalException e) {
            throw new GoogleReaderAdapterException(e);
        }
    }

    public void testProfileFeed(final String _email, final String _feedUrl, final BlitzRequestHandler _handler) throws GoogleReaderAdapterException {
        Assert.isValidString(_email, "Email is not valid");
        Assert.isValidString(_feedUrl, "Feed Url is not valid");
        Assert.notNull(_handler, "Handler is null");

        Profile profile = synchronizeProfile(_email);

        validate(profile.getAccount().getEmail(), profile.getAccount().getPassword());

        try {
            FeedConfiguration configuration = profile.findForFeedUrl(_feedUrl);

            if (configuration == null) {
                throw new GoogleReaderAdapterException(String.format("Can not find feed [ %s ] in profile [ %s ]", _feedUrl, _email));
            }

            this.log.info(String.format("Testing feed [ %s ] from profile [ %s ]", configuration.getUrl(), _email));
            updateFeed(_handler, profile, configuration);
        } catch (GoogleReaderProvider.GoogleReaderProviderException e) {
            throw new GoogleReaderAdapterException(e);
        } catch (ApiFacade.FatalException e) {
            throw new GoogleReaderAdapterException(e);
        }
    }

    public void changeProfilePassword(final String _email, final String _newPassword) throws GoogleReaderAdapterException {
        Assert.isValidString(_email, "Email is not valid");
        Assert.isValidString(_newPassword, "New password is not valid");

        try {
            Profiles profiles = this.profilesStorage.load();

            boolean changed = profiles.changeProfilePassword(_email, _newPassword);

            if (!changed) {
                throw new GoogleReaderAdapterException(String.format("Error change profile [ %s ] password", _email));
            }

            this.profilesStorage.store(profiles);
        } catch (ProfilesStorage.ProfilesStorageException e) {
            throw new GoogleReaderAdapterException(e);
        }
    }

    private void updateFeed(final BlitzRequestHandler _handler, final Profile profile, final FeedConfiguration configuration) throws GoogleReaderProvider.GoogleReaderProviderException, ApiFacade.FatalException {
        List<FeedItem> items = this.provider.getUnreadFeedItems(profile.getAccount(), configuration.getUrl());

        List<Modification> modifications = newArrayList();

        for (FeedItem item : items) {
            Modification modification = Modification.createModification(item);

            modifications.add(modification);
        }

        BlitzRequest request = GoogleReaderAdapterTools.createBlitzRequest(configuration, modifications);

        _handler.processBlitzRequest(request, 0);
    }

    private Profile synchronizeProfile(final String _email) throws GoogleReaderAdapterException {
        Assert.isValidString(_email, "Email is not valid");

        try {
            Profiles profiles = this.profilesStorage.load();

            Profile profile = profiles.find(_email);

            if (profile == null) {
                throw new GoogleReaderAdapterException(String.format("Can not find profile [ %s ]", _email));
            }

            List<Subscription> subscriptions = this.provider.getSubscriptions(profile.getAccount());

            GoogleReaderAdapterTools.synchronize(profile.getFeedConfigurations(), subscriptions);

            this.profilesStorage.store(profiles);
            
            return profile;
        } catch (GoogleReaderProvider.GoogleReaderProviderException e) {
            throw new GoogleReaderAdapterException(e);
        } catch (ProfilesStorage.ProfilesStorageException e) {
            throw new GoogleReaderAdapterException(e);
        }
    }

    private Account validate(final String _email, final String _password) throws GoogleReaderAdapterException {
        Account account = new Account(_email, _password);

        if (this.provider.accountValid(account)) {
            this.log.info(String.format("Google Reader profile for email [ %s ] is valid", _email));
        } else {
            throw new GoogleReaderAdapterException(String.format("Account [ %s ] is not valid", _email));
        }

        return account;
    }

}
