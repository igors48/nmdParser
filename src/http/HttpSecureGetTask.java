package http;

import http.banned.BannedList;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpRequestBase;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 16.11.2011
 */
public class HttpSecureGetTask extends AbstractHttpRequestTask {
    
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String GOOGLE_LOGIN_AUTHORIZATION_PREFIX = "GoogleLogin auth=";

    private static final String CONTENT_TYPE_HEADER_NAME = "Content-type";
    private static final String CONTENT_TYPE_HEADER_VALUE = "application/x-www-form-urlencoded";

    public HttpSecureGetTask(final HttpClient _httpClient, final BannedList _bannedList, final HttpSecureRequest _request) {
        super(_request, HttpRequestType.GET, _httpClient, _bannedList);
    }

    public HttpRequest call() throws Exception {
        return execute();
    }

    @Override
    protected void setHeaders(final HttpRequestBase _request) {
        super.setHeaders(_request);

        String authorizationToken = ((HttpSecureRequest) this.request).getAutorizationToken();

        if (!authorizationToken.isEmpty()) {
            _request.setHeader(CONTENT_TYPE_HEADER_NAME, CONTENT_TYPE_HEADER_VALUE);
            _request.setHeader(AUTHORIZATION_HEADER_NAME, GOOGLE_LOGIN_AUTHORIZATION_PREFIX + authorizationToken);
        }
    }
    
}
