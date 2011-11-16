package http;

import util.Assert;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 16.09.2011
 */
public class HttpSecureRequest extends HttpRequest {

    private final String autorizationToken;

    public HttpSecureRequest(final String _url, final String _request, final String _autorizationToken) {
        super(_url, _request, "");

        Assert.notNull(_autorizationToken, "Autorization token is null");
        this.autorizationToken = _autorizationToken;
    }

    public String getAutorizationToken() {
        return this.autorizationToken;
    }

}