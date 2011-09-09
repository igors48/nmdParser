package greader;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import java.io.IOException;
import java.net.URI;

import util.Assert;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 27.07.2011
 */
public class RequestFactory extends SimpleClientHttpRequestFactory {

    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String GOOGLE_LOGIN_AUTHORIZATION_PREFIX = "GoogleLogin auth=";

    private static final String CONTENT_TYPE_HEADER_NAME = "Content-type";
    private static final String CONTENT_TYPE_HEADER_VALUE = "application/x-www-form-urlencoded";

    private String authorizationToken;

    public void setAuthorizationToken(final String _authorizationToken) {
        Assert.isValidString(_authorizationToken, "Authentication token is not valid");
        this.authorizationToken = _authorizationToken;
    }

    @Override
    public ClientHttpRequest createRequest(final URI _uri, final HttpMethod _httpMethod) throws IOException {
        Assert.notNull(_uri, "URI is null");
        Assert.notNull(_httpMethod, "Http method is null");
        
        final ClientHttpRequest request = super.createRequest(_uri, _httpMethod);

        if (authorizationToken != null) {
            request.getHeaders().set(CONTENT_TYPE_HEADER_NAME, CONTENT_TYPE_HEADER_VALUE);
            request.getHeaders().set(AUTHORIZATION_HEADER_NAME, GOOGLE_LOGIN_AUTHORIZATION_PREFIX + authorizationToken);
        }

        return request;
    }
}
