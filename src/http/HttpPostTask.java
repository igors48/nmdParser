package http;

import http.banned.BannedList;
import org.apache.http.client.HttpClient;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 14.11.2011
 */
public class HttpPostTask extends AbstractHttpRequestTask {

    public HttpPostTask(final HttpClient _httpClient, final BannedList _bannedList, final HttpRequest _request) {
        super(_request, HttpRequestType.POST, _httpClient, _bannedList);
    }

    public HttpRequest call() throws Exception {
        return handle();
    }

}
