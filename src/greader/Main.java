package greader;

import java.io.IOException;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 27.07.2011
 */
public class Main {

    private static final String AUTHORIZATION_KEY = "Auth";

    public static void main(String[] args) throws IOException {
//        final RequestFactory requestFactory = new RequestFactory();
//        final RestTemplate restTemplate = new RestTemplate(requestFactory);
//
//        final String password = System.getProperty("Pass");
//        final String response = restTemplate.getForObject("https://www.google.com/accounts/ClientLogin?service=reader&Email=igors48@gmail.com&Passwd=" + password, String.class);
//
//        final Properties properties = new Properties();
//        properties.load(new StringReader(response));
//
//        requestFactory.setAuthorizationToken(properties.getProperty(AUTHORIZATION_KEY));
//
//        final String subscriptionsResponse = restTemplate.getForObject("http://www.google.com/reader/api/0/subscription/list?output=json", String.class);
//        final Subscriptions subscriptions = new Gson().fromJson(subscriptionsResponse, Subscriptions.class);
//
//        final String unreadResponse = restTemplate.getForObject("http://www.google.com/reader/api/0/unread-count?&output=json", String.class);
//        final String readListResponse = restTemplate.getForObject("http://www.google.com/reader/api/0/stream/contents/user/-/state/com.google/reading-list?&output=json", String.class);
//
//        final String feedResponse = restTemplate.getForObject("http://www.google.com/reader/api/0/stream/contents/feed/http://www.popmech.ru/rss/?xt=user/-/state/com.google/read", String.class);
//        final FeedItems feedResponseItems = new Gson().fromJson(feedResponse, FeedItems.class);
//
//        final String feedAnotherResponse = restTemplate.getForObject("http://www.google.com/reader/api/0/stream/contents/feed/http://www.popmech.ru/rss/", String.class);
//        final FeedItems feedAnotherResponseItems = new Gson().fromJson(feedAnotherResponse, FeedItems.class);
//
//        final String tokenResponse = restTemplate.getForObject("http://www.google.com/reader/api/0/token", String.class);
//        System.out.println(String.format("Token [ %s ]", tokenResponse));

        //final String postData = "a=user/-/state/com.google/read&s=feed/http://www.popmech.ru/rss/&i=tag:google.com,2005:reader/item/07b725a0ac8df8ee&T=" + tokenResponse;
//        final String postData = "r=user/-/state/com.google/read&s=feed/http://www.popmech.ru/rss/&i=tag:google.com,2005:reader/item/07b725a0ac8df8ee&T=" + tokenResponse;
//        final String postUrl = "http://www.google.com/reader/api/0/edit-tag";
//
//        String editResponse = restTemplate.postForObject(postUrl, postData, String.class);
//        System.out.println(String.format("Edit response [ %s ]", editResponse));
//
//        editResponse = restTemplate.postForObject(postUrl, postData, String.class);
//        System.out.println(String.format("Edit response [ %s ]", editResponse));
//
//        editResponse = restTemplate.postForObject(postUrl, postData, String.class);
//        System.out.println(String.format("Edit response [ %s ]", editResponse));

//        String postUrl = "http://www.google.com/reader/api/0/mark-all-as-unread?client=scroll&s=feed/http://www.popmech.ru/rss/&T=" + tokenResponse;
//        String editResponse = restTemplate.postForObject(postUrl, "h", String.class);
//        System.out.println(String.format("Edit response [ %s ]", editResponse));
    }
}
