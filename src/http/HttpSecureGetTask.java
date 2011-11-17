package http;

import http.banned.BannedList;
import org.apache.http.client.HttpClient;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 16.11.2011
 */
public class HttpSecureGetTask extends AbstractHttpSecureRequestTask {

    public HttpSecureGetTask(final HttpClient _httpClient, final BannedList _bannedList, final HttpSecureRequest _request, final String _userAgent) {
        super(_request, HttpRequestType.GET, _httpClient, _bannedList, _userAgent);
    }

}
