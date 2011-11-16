package greader;

import com.google.gson.Gson;
import greader.entities.FeedItem;
import greader.entities.FeedItems;
import greader.entities.Subscription;
import greader.entities.Subscriptions;
import greader.profile.Account;
import html.HttpData;
import http.BatchLoader;
import http.data.DataUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;
import util.CollectionUtils;
import static util.CollectionUtils.newArrayList;
import static util.CollectionUtils.newHashMap;
import util.JsonCodec;

import java.io.StringReader;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 28.07.2011
 */
public class GoogleReaderProvider {

    private static final String CLIENT_LOGIN_URL = "https://www.google.com/accounts/ClientLogin?";
    private static final String CLIENT_LOGIN_REQUEST = "service=reader&Email=%s&Passwd=%s";

    private static final String AUTHORIZATION_KEY = "Auth";

    private static final String GET_SUBSCRIPTIONS_URL = "http://www.google.com/reader/api/0/subscription/list?output=json";

    private static final String GET_UNREAD_ITEMS_URL = "http://www.google.com/reader/api/0/stream/contents/feed/%s?";
    private static final String GET_UNREAD_ITEMS_REQUEST ="xt=user/-/state/com.google/read&n=1000";

    private static final String GET_TOKEN_URL = "http://www.google.com/reader/api/0/token";

    private static final String FEED_URL_PARAMETER = "url";
    private static final String TOKEN_PARAMETER = "token";
    private static final String MARK_ALL_AS_READ_URL = "http://www.google.com/reader/api/0/mark-all-as-read?client=scroll&s=feed/{" +
            FEED_URL_PARAMETER + "}&T={" +
            TOKEN_PARAMETER + "}";

    private final String FEED_PREFIX = "feed/";

    private final HttpSecureAdapter httpSecureAdapter;
    
    private final Log log;

    public GoogleReaderProvider(final HttpSecureAdapter _httpSecureAdapter) {
        Assert.notNull(_httpSecureAdapter, "Batch loader is null");
        this.httpSecureAdapter =_httpSecureAdapter;
        
        this.log = LogFactory.getLog(getClass());
    }

    public boolean accountValid(final Account _account) {
        Assert.notNull(_account, "Account is null");

        try {
            this.log.debug(String.format("Try to validate account [ %s ]", _account.getEmail()));

            login(_account);

            this.log.debug(String.format("Account [ %s ] is valid", _account.getEmail()));

            return true;
        } catch (GoogleReaderProviderException e) {
            this.log.error(String.format("Error validating account [ %s ]", _account.getEmail()));

            return false;
        }
    }

    public List<Subscription> getSubscriptions(final Account _account) throws GoogleReaderProviderException {
        Assert.notNull(_account, "Account is null");

        login(_account);

        try {
            String subscriptionsResponse = this.httpSecureAdapter.getForString(GET_SUBSCRIPTIONS_URL);
            Subscriptions subscriptions = JsonCodec.fromJson(subscriptionsResponse, Subscriptions.class);

            List<Subscription> result = newArrayList();

            for (Subscription subscription : subscriptions.getSubscriptions()) {
                subscription.setId(subscription.getId().substring(FEED_PREFIX.length()));
                result.add(subscription);
            }

            return result;
        } catch (Exception e) {
            throw new GoogleReaderProviderException(String.format("Error getting subscriptions list from account [ %s ]", _account.getEmail()), e);
        }
    }

    public List<FeedItem> getUnreadFeedItems(final Account _account, final String _feedUrl) throws GoogleReaderProviderException {
        Assert.notNull(_account, "Account is null");
        Assert.isValidString(_feedUrl, "Feed URL is not valid");

        login(_account);

        try {
            this.log.debug(String.format("Try to get unread items for feed [ %s ]", _feedUrl));

            final String url = String.format(GET_UNREAD_ITEMS_URL, _feedUrl);

            String feedResponse = this.httpSecureAdapter.getForString(url, GET_UNREAD_ITEMS_REQUEST);

            FeedItems feedResponseItems = new Gson().fromJson(feedResponse, FeedItems.class);

            this.log.debug(String.format("Get [ %d ] unread items for feed [ %s ]", feedResponseItems.getItems().length, _feedUrl));

            List<FeedItem> result = CollectionUtils.newArrayList();

            for (FeedItems.Item current : feedResponseItems.getItems()) {
                FeedItem item = FeedItem.create(current);

                result.add(item);
            }

            return result;
        } catch (Exception e) {
            throw new GoogleReaderProviderException(String.format("Error getting unreaded items list from account [ %s ]", _account.getEmail()), e);
        }
    }

    public void markAllFeedItemsAsRead(final Account _account, final String _feedUrl) throws GoogleReaderProviderException {
        Assert.notNull(_account, "Account is null");
        Assert.isValidString(_feedUrl, "Feed Url is not valid");

        login(_account);

        try {
            String tokenResponse = "restTemplate.getForObject(GET_TOKEN_URL, String.class)";

            Map<String, String> parameters = newHashMap();
            parameters.put(FEED_URL_PARAMETER, escapeUrl(_feedUrl));
            parameters.put(TOKEN_PARAMETER, tokenResponse);

            //restTemplate.postForObject(MARK_ALL_AS_READ_URL, "h", String.class, parameters);

        } catch (Exception e) {
            throw new GoogleReaderProviderException(String.format("Error mark items as read from account [ %s ]", _account.getEmail()), e);
        }
    }

    private void login(final Account _account) throws GoogleReaderProviderException {

        try {
            String request = String.format(CLIENT_LOGIN_REQUEST, _account.getEmail(), _account.getPassword());

            String response = this.httpSecureAdapter.getForString(CLIENT_LOGIN_URL, request);

            Properties properties = new Properties();
            properties.load(new StringReader(response));
            String authorizationToken = properties.getProperty(AUTHORIZATION_KEY);

            if (authorizationToken == null || authorizationToken.isEmpty()) {
                throw new GoogleReaderProviderException(String.format("Error getting authorization token for [ %s ]", _account.getEmail()));
            }

            this.httpSecureAdapter.setAuthorizationToken(authorizationToken);

            this.log.debug(String.format("Successfully logged as [ %s ]", _account.getEmail()));
        } catch (Exception e) {
            throw new GoogleReaderProviderException(String.format("Error logged as [ %s ]", _account.getEmail()), e);
        }

    }

    private String escapeUrl(final String _url) {
        
        //try {
        //    return new URI(_url, false).toString();
        //} catch (URIException e) {
            return _url;
        //}
    }

    public class GoogleReaderProviderException extends Exception {

        public GoogleReaderProviderException(String message) {
            super(message);
        }

        public GoogleReaderProviderException(String message, Throwable cause) {
            super(message, cause);
        }
    }
    
}
