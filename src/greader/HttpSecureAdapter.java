package greader;

import html.HttpData;
import http.*;
import http.data.DataUtil;
import util.Assert;

import java.util.concurrent.Callable;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 16.11.2011
 */
public class HttpSecureAdapter {

    private final HttpRequestHandler httpRequestHandler;

    private String authorizationToken;
    
    public HttpSecureAdapter(final HttpRequestHandler _httpRequestHandler) {
        Assert.notNull(_httpRequestHandler, "Request handler is null");
        this.httpRequestHandler = _httpRequestHandler;

        this.authorizationToken = "";
    }

    public void setAuthorizationToken(final String _authorizationToken) {
        Assert.isValidString(_authorizationToken, "Authorization token is not valid");

        this.authorizationToken = _authorizationToken;
    }

    public String getForString(final String _url) throws HttpSecureAdapterException {
        Assert.isValidString(_url, "Url is not valid");

        return getForString(_url, "");
    }

    public String getForString(final String _url, final String _request) throws HttpSecureAdapterException {
        Assert.isValidString(_url, "Url is not valid");
        Assert.notNull(_request, "Request is null");

        try {
            final Callable<HttpRequest> request = createGetRequestTask(_url, _request);
            final HttpData httpData = request.call().getResult();

            return getStringFromData(httpData);
        } catch (Exception e) {
            throw new HttpSecureAdapterException(e);
        }
    }

    public String postForString(final String _url, final String _request) throws HttpSecureAdapterException {
        Assert.isValidString(_url, "Url is not valid");
        Assert.notNull(_request, "Request is null");

        return "";
    }

    private Callable<HttpRequest> createGetRequestTask(final String _url, final String _request) {
        final HttpSecureRequest httpRequest = new HttpSecureRequest(_url, _request, this.authorizationToken);

        return this.httpRequestHandler.getSecured(httpRequest);
    }

    private String getStringFromData(final HttpData _data) throws HttpSecureAdapterException, Data.DataException {

        if (_data.getResult() != Result.OK) {
            throw new HttpSecureAdapterException("Error data received");
        }

        return DataUtil.getString(_data.getData());
    }

    public class HttpSecureAdapterException extends Exception {

        public HttpSecureAdapterException(final String _message) {
            super(_message);
        }

        public HttpSecureAdapterException(final Throwable _cause) {
            super(_cause);
        }

    }

}
