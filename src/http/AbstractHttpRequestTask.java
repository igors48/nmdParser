package http;

import html.HttpData;
import http.banned.BannedList;
import http.data.MemoryData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import util.Assert;

import java.io.IOException;
import java.util.concurrent.Callable;

import static http.HttpTools.getHostFromMethod;
import static http.HttpTools.getUrlWithEscapedRequest;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 14.11.2011
 */
public abstract class AbstractHttpRequestTask implements Callable<HttpRequest> {

    protected static final String CONTENT_TYPE_HEADER_NAME = "Content-Type";

    protected final HttpRequestType requestType;
    protected final HttpContext context;
    protected final HttpClient httpClient;
    protected final BannedList bannedList;
    protected final HttpRequest request;

    protected final Log log;

    public AbstractHttpRequestTask(final HttpRequest _request, final HttpRequestType _requestType, final HttpClient _httpClient, final BannedList _bannedList) {
        Assert.notNull(_request, "Request is null");
        this.request = _request;

        Assert.notNull(_httpClient, "Http client is null");
        this.httpClient = _httpClient;

        Assert.notNull(_bannedList, "Banned list is null");
        this.bannedList = _bannedList;

        Assert.notNull(_requestType, "Request type is null");
        this.requestType = _requestType;

        this.context = new BasicHttpContext();

        this.log = LogFactory.getLog(getClass());
    }

    protected HttpRequest execute() {
        final String escapedUrl = getUrlWithEscapedRequest(this.request.getUrl(), this.request.getRequest());
        final HttpRequestBase method = createMethod();

        final String targetHost = getHostFromMethod(method);

        try {
            final boolean banned = this.bannedList.isBanned(targetHost);

            if (banned) {
                this.log.info(String.format("Host [ %s ] is banned", targetHost));

                this.request.setResult(HttpData.EMPTY_DATA);
            } else {
                this.log.debug(String.format("%s request to [ %s ]", this.requestType, escapedUrl));

                handle(method);
            }
        } catch (Exception e) {
            method.abort();

            this.bannedList.complain(targetHost);

            this.request.setResult(HttpData.ERROR_DATA);

            this.log.error(String.format("Error in %s request to [ %s ]", this.requestType, escapedUrl), e);
        }

        return this.request;
    }

    protected String getResponseUrl() {
        final HttpUriRequest currentRequest = (HttpUriRequest) this.context.getAttribute(ExecutionContext.HTTP_REQUEST);
        final HttpHost currentHost = (HttpHost) this.context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);

        return currentHost.toURI() + currentRequest.getURI();
    }

    private void handle(final HttpRequestBase _method) throws IOException {
        final HttpResponse response = this.httpClient.execute(_method, this.context);
        final HttpEntity entity = response.getEntity();

        if (entity == null) {
            this.request.setResult(HttpData.EMPTY_DATA);
        } else {
            handleEntity(entity, response);
        }

        EntityUtils.consume(entity);
    }

    private void handleEntity(final HttpEntity _entity, final HttpResponse _response) throws IOException {
        final Header[] headers = _response.getHeaders(CONTENT_TYPE_HEADER_NAME);
        final String charset = HttpTools.getCharset(headers);

        final Data data = charset.isEmpty() ? new MemoryData(EntityUtils.toByteArray(_entity)) : new MemoryData(EntityUtils.toByteArray(_entity), charset);

        final String responseUrl = getResponseUrl();
        final HttpData httpData = new HttpData(responseUrl, data, Result.OK);

        this.request.setResult(httpData);
    }

    private HttpRequestBase createMethod() {
        final String escapedUrl = getUrlWithEscapedRequest(this.request.getUrl(), this.request.getRequest());
        final String escapedReferer = this.request.getReferer().isEmpty() ? "" : getUrlWithEscapedRequest(this.request.getReferer(), "");

        final HttpRequestBase result = this.requestType == HttpRequestType.POST ? new HttpPost(escapedUrl) : new HttpGet(escapedUrl);

//        result.setHeader(StandardHttpRequestHandler.REFERER_HEADER_NAME, escapedReferer);
//
//        result.setHeader(StandardHttpRequestHandler.ACCEPT_HEADER_NAME, StandardHttpRequestHandler.ACCEPT_REQUEST_HEADER_VALUE);
//        result.setHeader(StandardHttpRequestHandler.USER_AGENT_HEADER_NAME, StandardHttpRequestHandler.USER_AGENT_REQUEST_HEADER_VALUE);
//        result.setHeader(StandardHttpRequestHandler.ACCEPT_CHARSET_HEADER_NAME, StandardHttpRequestHandler.ACCEPT_CHARSET_HEADER_VALUE);
//        result.setHeader(StandardHttpRequestHandler.ACCEPT_ENCODING_HEADER_NAME, StandardHttpRequestHandler.ACCEPT_ENCODING_HEADER_VALUE);

        return result;
    }

}
