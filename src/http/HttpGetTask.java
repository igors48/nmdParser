package http;

import html.HttpData;
import http.cache.InMemoryCache;
import http.cache.InMemoryCacheItem;
import http.data.MemoryData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

import java.util.concurrent.Callable;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 19.09.2011
 */
public class HttpGetTask implements Callable<HttpGetRequest> {

    private final HttpClient httpClient;
    private final InMemoryCache cache;
    private final HttpGetRequest request;

    private final HttpContext context;

    private final Log log;

    public HttpGetTask(final HttpClient _httpClient, final InMemoryCache _cache, final HttpGetRequest _request) {
        Assert.notNull(_httpClient, "Http client is null");
        this.httpClient = _httpClient;

        Assert.notNull(_cache, "Cache is null");
        this.cache = _cache;

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

        try {
            this.log.debug(String.format("GET request from [ %s ]", _targetUrl));
            
            final HttpResponse response = this.httpClient.execute(httpGet, this.context);
            final HttpEntity entity = response.getEntity();

            if (entity == null) {
                this.request.setResult(HttpData.EMPTY_DATA);
            } else {
                //TODO charset
                final Data data = new MemoryData(EntityUtils.toByteArray(entity));
                final String responseUrl = getResponseUrl();
                final HttpData httpData = new HttpData(responseUrl, data, Result.OK);

                this.request.setResult(httpData);

                this.cache.put(_targetUrl, responseUrl, data);
            }

            EntityUtils.consume(entity);
        } catch (Exception ex) {
            httpGet.abort();
            this.request.setResult(HttpData.ERROR_DATA);
        }
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
        httpGet.setHeader(SimpleHttpRequestHandler.REFERER_HEADER_NAME, escapedReferer);
        httpGet.setHeader(SimpleHttpRequestHandler.ACCEPT_HEADER_NAME, SimpleHttpRequestHandler.ACCEPT_REQUEST_HEADER_VALUE);
        httpGet.setHeader(SimpleHttpRequestHandler.USER_AGENT_HEADER_NAME, SimpleHttpRequestHandler.USER_AGENT_REQUEST_HEADER_VALUE);

        /*
        httpGet.setHeader(ACCEPT_LANGUAGE_HEADER_NAME, ACCEPT_LANGUAGE_HEADER_VALUE);
        httpGet.setHeader(ACCEPT_CHARSET_HEADER_NAME, ACCEPT_CHARSET_HEADER_VALUE);

        httpGet.setHeader(SimpleHttpRequestHandler.ACCEPT_ENCODING_HEADER_NAME, SimpleHttpRequestHandler.ACCEPT_ENCODING_HEADER_VALUE);
        httpGet.setHeader(SimpleHttpRequestHandler.CACHE_CONTROL_HEADER_NAME, SimpleHttpRequestHandler.CACHE_CONTROL_HEADER_VALUE);
        
        httpGet.setHeader(CONNECTION_HEADER_NAME, CONNECTION_HEADER_VALUE);
        httpGet.setHeader(PRAGMA_HEADER_NAME, PRAGMA_HEADER_VALUE);
        */

        return httpGet;
    }

    private String getTargetUrl() {
        return this.request.getUrl() + this.request.getRequest();
    }

}
