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
import static http.HttpTools.getUrlWithRequest;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 14.11.2011
 */
public abstract class AbstractHttpRequestTask implements Callable<HttpRequest> {

    protected static final String CONTENT_TYPE_HEADER_NAME = "Content-Type";

    private static final String REFERER_HEADER_NAME = "Referer";
    private static final String ACCEPT_HEADER_NAME = "Accept";
    private static final String ACCEPT_REQUEST_HEADER_VALUE = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    private static final String USER_AGENT_HEADER_NAME = "User-Agent";

    private static final String ACCEPT_CHARSET_HEADER_NAME = "Accept-Charset";
    private static final String ACCEPT_CHARSET_HEADER_VALUE = "windows-1251,utf-8;q=0.7,*;q=0.3";

    /*
    private static final String ACCEPT_LANGUAGE_HEADER_NAME = "Accept-Language";
    private static final String ACCEPT_LANGUAGE_HEADER_VALUE = "ru-RU,ru;q=0.8,en-US;q=0.6,en;q=0.4";
    */

    private static final String ACCEPT_ENCODING_HEADER_NAME = "Accept-Encoding";
    private static final String ACCEPT_ENCODING_HEADER_VALUE = "gzip,deflate";

    protected final HttpRequest request;

    private final HttpRequestType requestType;
    private final HttpContext context;
    private final HttpClient httpClient;
    private final BannedList bannedList;
    private final String userAgent;

    protected final Log log;

    public AbstractHttpRequestTask(final HttpRequest _request, final HttpRequestType _requestType, final HttpClient _httpClient, final BannedList _bannedList, final String _userAgent) {
        Assert.notNull(_request, "Request is null");
        this.request = _request;

        Assert.notNull(_httpClient, "Http client is null");
        this.httpClient = _httpClient;

        Assert.notNull(_bannedList, "Banned list is null");
        this.bannedList = _bannedList;

        Assert.notNull(_requestType, "Request type is null");
        this.requestType = _requestType;

        Assert.notNull(_userAgent, "User agent is null");
        this.userAgent = _userAgent;

        this.context = new BasicHttpContext();

        this.log = LogFactory.getLog(getClass());
    }

    protected HttpRequest execute() {
        final String urlWithRequest = getUrlWithRequest(this.request.getUrl(), this.request.getRequest());
        final HttpRequestBase method = createMethod();

        final String targetHost = getHostFromMethod(method);

        try {
            final boolean banned = this.bannedList.isBanned(targetHost);

            if (banned) {
                this.log.info(String.format("Host [ %s ] is banned", targetHost));

                this.request.setResult(HttpData.EMPTY_DATA);
            } else {
                this.log.debug(String.format("%s request to [ %s ] started", this.requestType, urlWithRequest));

                handle(method);

                this.log.debug(String.format("%s request to [ %s ] completed with status [ %s ]", this.requestType, urlWithRequest, this.request.getResult().getResult()));
            }
        } catch (Exception e) {
            method.abort();

            this.bannedList.complain(targetHost);

            this.request.setResult(HttpData.ERROR_DATA);

            this.log.error(String.format("Error in %s request to [ %s ]", this.requestType, urlWithRequest), e);
        }

        return this.request;
    }

    protected String getResponseUrl() {
        final HttpUriRequest currentRequest = (HttpUriRequest) this.context.getAttribute(ExecutionContext.HTTP_REQUEST);
        final HttpHost currentHost = (HttpHost) this.context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);

        return currentHost.toURI() + currentRequest.getURI();
    }

    protected void setHeaders(final HttpRequestBase _request) {
        Assert.notNull(_request, "Request is null");

        final String urlWithRequest = this.request.getReferer().isEmpty() ? "" : getUrlWithRequest(this.request.getReferer(), "");

        _request.setHeader(REFERER_HEADER_NAME, urlWithRequest);

        _request.setHeader(ACCEPT_HEADER_NAME, ACCEPT_REQUEST_HEADER_VALUE);
        _request.setHeader(USER_AGENT_HEADER_NAME, this.userAgent);
        _request.setHeader(ACCEPT_CHARSET_HEADER_NAME, ACCEPT_CHARSET_HEADER_VALUE);
        _request.setHeader(ACCEPT_ENCODING_HEADER_NAME, ACCEPT_ENCODING_HEADER_VALUE);
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
        final String urlWithRequest = getUrlWithRequest(this.request.getUrl(), this.request.getRequest());

        final HttpRequestBase result = this.requestType == HttpRequestType.POST ? new HttpPost(urlWithRequest) : new HttpGet(urlWithRequest);

        setHeaders(result);

        return result;
    }

}
