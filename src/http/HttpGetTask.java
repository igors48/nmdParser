package http;

import util.Assert;

import java.util.concurrent.Callable;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 16.09.2011
 */
public class HttpGetTask implements Callable<HttpGetRequest> {

    private final HttpGetRequest request;
    private final HttpRequestHandler requestHandler;

    public HttpGetTask(final HttpGetRequest _request, final HttpRequestHandler _requestHandler) {
        Assert.notNull(_request, "Request is null");
        this.request = _request;

        Assert.notNull(_requestHandler, "Request handler is null");
        this.requestHandler = _requestHandler;
    }

    public HttpGetRequest call() throws Exception {
        this.requestHandler.get(this.request);

        return this.request;
    }

}
