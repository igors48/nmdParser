package greader;

import com.google.gson.Gson;
import greader.entities.FeedItem;
import greader.entities.FeedItems;
import greader.entities.Subscription;
import greader.entities.Subscriptions;
import greader.profile.Account;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.client.RestTemplate;
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

    private static final String OUTPUT_JSON = "output=json";

    private static final String EMAIL_PARAMETER = "email";
    private static final String PASSWORD_PARAMETER = "password";

    private static final String CLIENT_LOGIN_URL = "https://www.google.com/accounts/ClientLogin?service=reader&Email={" +
            EMAIL_PARAMETER + "}&Passwd={" +
            PASSWORD_PARAMETER + "}";

    private static final String AUTHORIZATION_KEY = "Auth";

    private static final String GET_SUBSCRIPTIONS_URL = "http://www.google.com/reader/api/0/subscription/list?" + OUTPUT_JSON;

    private static final int MAX_UNREAD_ITEMS_COUNT = 1000;

    private static final String FEED_URL_PARAMETER = "url";
    private static final String TAG_PARAMETER = "tag";
    private static final String ITEMS_COUNT_PARAMETER = "count";
    private static final String TOKEN_PARAMETER = "token";
    private static final String READ_CATEGORY_NAME = "user/-/state/com.google/read";

    private static final String GET_UNREAD_ITEMS_URL = "http://www.google.com/reader/api/0/stream/contents/feed/{" +
            FEED_URL_PARAMETER + "}/?xt=" +
            READ_CATEGORY_NAME + "&n={" +
            ITEMS_COUNT_PARAMETER + "}";

    private static final String GET_TOKEN_URL = "http://www.google.com/reader/api/0/token";

    private final String FEED_PREFIX = "feed/";

    private static final String POST_EDIT_TAG_URL = "http://www.google.com/reader/api/0/edit-tag";

    private final RequestFactory requestFactory;
    private final RestTemplate restTemplate;

    private final Log log;

    public GoogleReaderProvider() {
        this.requestFactory = new RequestFactory();
        this.restTemplate = new RestTemplate(this.requestFactory);

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
            String subscriptionsResponse = restTemplate.getForObject(GET_SUBSCRIPTIONS_URL, String.class);
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

            Map<String, String> parameters = newHashMap();
            parameters.put(FEED_URL_PARAMETER, _feedUrl);
            parameters.put(ITEMS_COUNT_PARAMETER, String.valueOf(MAX_UNREAD_ITEMS_COUNT));

            String feedResponse = restTemplate.getForObject(GET_UNREAD_ITEMS_URL, String.class, parameters);

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

    public void markFeedItemsAsRead(final Account _account, final String _feedUrl, final List<FeedItem> _feedItems) throws GoogleReaderProviderException {
        Assert.notNull(_account, "Account is null");
        Assert.isValidString(_feedUrl, "Feed Url is not valid");
        Assert.notNull(_feedItems, "Feed items is null");

        login(_account);

        try {
            final String tokenResponse = restTemplate.getForObject(GET_TOKEN_URL, String.class);

            for (FeedItem feedItem : _feedItems) {
                //final String postData = String.format("a=" + READ_CATEGORY_NAME + "&s=" + FEED_PREFIX + "%s&i=%s&T=%s", _feedUrl, feedItem.getId(), tokenResponse);
                final String postData = "a=user/-/state/com.google/read&s=feed/http://www.popmech.ru/rss/&i=tag:google.com,2005:reader/item/07b725a0ac8df8ee&T=" + tokenResponse;

                final String editResponse = restTemplate.postForObject(POST_EDIT_TAG_URL, postData, String.class);
            }
        } catch (Exception e) {
            throw new GoogleReaderProviderException(String.format("Error mark items as read from account [ %s ]", _account.getEmail()), e);
        }
    }

    private void login(final Account _account) throws GoogleReaderProviderException {

        try {
            Map<String, String> parameters = newHashMap();
            parameters.put(EMAIL_PARAMETER, _account.getEmail());
            parameters.put(PASSWORD_PARAMETER, _account.getPassword());

            String response = this.restTemplate.getForObject(CLIENT_LOGIN_URL, String.class, parameters);

            Properties properties = new Properties();
            properties.load(new StringReader(response));
            String authorizationToken = properties.getProperty(AUTHORIZATION_KEY);

            if (authorizationToken == null || authorizationToken.isEmpty()) {
                throw new GoogleReaderProviderException(String.format("Error getting authorization token for [ %s ]", _account.getEmail()));
            }

            this.requestFactory.setAuthorizationToken(authorizationToken);

            this.log.debug(String.format("Successfully logged as [ %s ]", _account.getEmail()));
        } catch (Exception e) {
            throw new GoogleReaderProviderException(String.format("Error logged as [ %s ]", _account.getEmail()), e);
        }

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
