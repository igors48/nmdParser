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

        final Callable<HttpRequest> request = createGetSecureRequestTask(_url, _request);

        return execute(request);
    }

    public String postForString(final String _url, final String _request) throws HttpSecureAdapterException {
        Assert.isValidString(_url, "Url is not valid");
        Assert.notNull(_request, "Request is null");

        final Callable<HttpRequest> request = createPostSecureRequestTask(_url, _request);

        return execute(request);
    }

    private String execute(final Callable<HttpRequest> _request) throws HttpSecureAdapterException {

        try {
            final HttpData httpData = _request.call().getResult();

            return getStringFromData(httpData);
        } catch (Exception e) {
            throw new HttpSecureAdapterException(e);
        }
    }

    private Callable<HttpRequest> createGetSecureRequestTask(final String _url, final String _request) {
        final HttpSecureRequest httpRequest = new HttpSecureRequest(_url, _request, this.authorizationToken);

        return this.httpRequestHandler.getSecured(httpRequest);
    }

    private Callable<HttpRequest> createPostSecureRequestTask(final String _url, final String _request) {
        final HttpSecureRequest httpRequest = new HttpSecureRequest(_url, _request, this.authorizationToken);

        return this.httpRequestHandler.postSecured(httpRequest);
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
