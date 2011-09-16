package http;

import html.HttpData;
import util.Assert;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 16.09.2011
 */
public class HttpGetRequest {

    private final String url;
    private final String request;
    private final String referer;

    private HttpData result;

    public HttpGetRequest(final String _url, final String _request, final String _referer) {
        Assert.isValidString(_url, "Url is not valid");
        this.url = _url;

        Assert.notNull(_request, "Request is null");
        this.request = _request;

        Assert.notNull(_referer, "Referer is null");
        this.referer = _referer;

        this.result = HttpData.EMPTY_DATA;
    }

    public String getUrl() {
        return this.url;
    }

    public String getRequest() {
        return this.request;
    }

    public String getReferer() {
        return this.referer;
    }

    public void setResult(final HttpData _result) {
        Assert.notNull(_result, "Result is null");
        this.result = _result;
    }

    public HttpData getResult() {
        return this.result;
    }
    
}
