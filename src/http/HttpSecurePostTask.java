package http;

import http.banned.BannedList;
import org.apache.http.client.HttpClient;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 16.11.2011
 */
public class HttpSecurePostTask extends AbstractHttpSecureRequestTask {

    public HttpSecurePostTask(final HttpClient _httpClient, final BannedList _bannedList, final HttpSecureRequest _request) {
        super(_request, HttpRequestType.POST, _httpClient, _bannedList);
    }

}