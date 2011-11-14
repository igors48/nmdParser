package http;

import html.HttpData;
import http.banned.BannedList;
import http.cache.InMemoryCache;
import http.cache.InMemoryCacheItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import util.Assert;

import static http.HttpTools.getUrlWithEscapedRequest;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 19.09.2011
 */
public class HttpGetTask extends AbstractHttpRequestTask {

    private final InMemoryCache cache;

    private final Log log;

    public HttpGetTask(final HttpClient _httpClient, final InMemoryCache _cache, final BannedList _bannedList, final HttpRequest _request) {
        super(_request, HttpRequestType.GET, _httpClient, _bannedList);

        Assert.notNull(_cache, "Cache is null");
        this.cache = _cache;

        this.log = LogFactory.getLog(getClass());
    }

    public HttpRequest call() throws Exception {
        final String targetUrl = getUrlWithEscapedRequest(this.request.getUrl(), this.request.getRequest());

        final InMemoryCacheItem fromCache = this.cache.get(targetUrl);

        if (fromCache == null) {
            handle();
            this.cache.put(targetUrl, getResponseUrl(), this.request.getResult().getData());
        } else {
            this.log.debug(String.format("Data for [ %s ] taken from cache", targetUrl));

            createFromCached(fromCache);
        }

        return this.request;
    }

    private void createFromCached(final InMemoryCacheItem _fromCache) {
        final HttpData httpData = new HttpData(_fromCache.getResponseUrl(), _fromCache.getData(), Result.OK);

        this.request.setResult(httpData);
    }

}
