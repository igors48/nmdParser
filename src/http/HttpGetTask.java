package http;

import html.HttpData;
import http.banned.BannedList;
import http.cache.InMemoryCache;
import http.cache.InMemoryCacheItem;
import http.data.MemoryData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import util.Assert;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 19.09.2011
 */
public class HttpGetTask implements Callable<HttpGetRequest> {

    private final HttpClient httpClient;
    private final InMemoryCache cache;
    private final BannedList bannedList;
    private final HttpGetRequest request;

    private final HttpContext context;

    private final Log log;
    private static final String CONTENT_TYPE_HEADER_NAME = "Content-Type";

    public HttpGetTask(final HttpClient _httpClient, final InMemoryCache _cache, final BannedList _bannedList, final HttpGetRequest _request) {
        Assert.notNull(_httpClient, "Http client is null");
        this.httpClient = _httpClient;

        Assert.notNull(_cache, "Cache is null");
        this.cache = _cache;

        Assert.notNull(_bannedList, "Banned list is null");
        this.bannedList = _bannedList;

        Assert.notNull(_request, "Request is null");
        this.request = _request;

        this.context = new BasicHttpContext();

        this.log = LogFactory.getLog(getClass());
    }

    public HttpGetRequest call() throws Exception {
        final String targetUrl = getTargetUrl();
      
        final InMemoryCacheItem fromCache = this.cache.get(targetUrl);

        if (fromCache == null) {
            getFromAddress(targetUrl);
        } else {
            this.log.debug(String.format("Data for [ %s ] taken from cache", targetUrl));

            createFromCached(fromCache);
        }

        return this.request;
    }

    private void getFromAddress(final String _targetUrl) {
        final HttpGet httpGet = createMethod();
        final String targetHost = getHostFromMethod(httpGet);

        try {
            final boolean banned = this.bannedList.isBanned(getHostFromMethod(httpGet));

            if (banned) {
                this.log.info(String.format("Host [ %s ] is banned", targetHost));

                this.request.setResult(HttpData.EMPTY_DATA);
            } else {
                this.log.debug(String.format("GET request to [ %s ]", _targetUrl));

                getResponse(_targetUrl, httpGet);
            }
        } catch (Exception e) {
            httpGet.abort();

            this.bannedList.complain(targetHost);

            this.request.setResult(HttpData.ERROR_DATA);

            this.log.error(String.format("Error in GET request from [ %s ]", _targetUrl), e);
        }
    }

    private void getResponse(final String _targetUrl, final HttpGet _httpGet) throws IOException {
        final HttpResponse response = this.httpClient.execute(_httpGet, this.context);
        final HttpEntity entity = response.getEntity();

        if (entity == null) {
            this.request.setResult(HttpData.EMPTY_DATA);
        } else {
            handleEntity(_targetUrl, entity, response);
        }

        EntityUtils.consume(entity);
    }

    private void handleEntity(final String _targetUrl, final HttpEntity _entity, final HttpResponse _response) throws IOException {
        final Header[] headers = _response.getHeaders(CONTENT_TYPE_HEADER_NAME);
        final String charset = HttpTools.getCharset(headers);

        final Data data = charset.isEmpty() ? new MemoryData(EntityUtils.toByteArray(_entity)) : new MemoryData(EntityUtils.toByteArray(_entity), charset);
        
        final String responseUrl = getResponseUrl();
        final HttpData httpData = new HttpData(responseUrl, data, Result.OK);

        this.request.setResult(httpData);

        this.cache.put(_targetUrl, responseUrl, data);
    }

    private String getHostFromMethod(final HttpGet _httpGet) {
        return _httpGet.getURI().getHost();
    }

    private void createFromCached(final InMemoryCacheItem _fromCache) {
        final HttpData httpData = new HttpData(_fromCache.getResponseUrl(), _fromCache.getData(), Result.OK);

        this.request.setResult(httpData);
    }

    private String getResponseUrl() {
        final HttpUriRequest currentRequest = (HttpUriRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);
        final HttpHost currentHost = (HttpHost) context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);

        return currentHost.toURI() + currentRequest.getURI();
    }

    private HttpGet createMethod() {

        final String escapedUrl = getTargetUrl();
        final String escapedReferer = this.request.getReferer();

        final HttpGet httpGet = new HttpGet(escapedUrl);
        httpGet.setHeader(StandardHttpRequestHandler.REFERER_HEADER_NAME, escapedReferer);
        httpGet.setHeader(StandardHttpRequestHandler.ACCEPT_HEADER_NAME, StandardHttpRequestHandler.ACCEPT_REQUEST_HEADER_VALUE);
        httpGet.setHeader(StandardHttpRequestHandler.USER_AGENT_HEADER_NAME, StandardHttpRequestHandler.USER_AGENT_REQUEST_HEADER_VALUE);
        httpGet.setHeader(StandardHttpRequestHandler.ACCEPT_CHARSET_HEADER_NAME, StandardHttpRequestHandler.ACCEPT_CHARSET_HEADER_VALUE);
        httpGet.setHeader(StandardHttpRequestHandler.ACCEPT_ENCODING_HEADER_NAME, StandardHttpRequestHandler.ACCEPT_ENCODING_HEADER_VALUE);

        return httpGet;
    }

    private String getTargetUrl() {
        return this.request.getUrl() + this.request.getRequest();
    }

}
