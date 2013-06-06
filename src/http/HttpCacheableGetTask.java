package http;

import html.HttpData;
import http.banned.BannedList;
import http.cache.InMemoryCache;
import http.cache.InMemoryCacheItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import util.Assert;

import static http.HttpTools.getUrlWithRequest;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 19.09.2011
 */
public class HttpCacheableGetTask extends AbstractHttpRequestTask {

    private final InMemoryCache cache;

    private final Log log;

    public HttpCacheableGetTask(final HttpClient _httpClient, final InMemoryCache _cache, final BannedList _bannedList, final HttpRequest _request, final String _userAgent) {
        super(_request, HttpRequestType.GET, _httpClient, _bannedList, _userAgent);

        Assert.notNull(_cache, "Cache is null");
        this.cache = _cache;

        this.log = LogFactory.getLog(getClass());
    }

    public HttpRequest call() {
        final String urlWithRequest = getUrlWithRequest(this.request.getUrl(), this.request.getRequest());

        final InMemoryCacheItem fromCache = this.cache.get(urlWithRequest);

        if (fromCache == null) {
            execute();

            if (canBeCached()) {
                this.cache.put(urlWithRequest, getResponseUrl(), this.request.getResult().getData());
            }
        } else {
            this.log.debug(String.format("Data for [ %s ] taken from cache", urlWithRequest));

            createFromCached(fromCache);
        }

        return this.request;
    }

    private boolean canBeCached() {
        return this.request.getResult().getResult() == Result.OK && this.request.getResult().getData().size() != 0;
    }

    private void createFromCached(final InMemoryCacheItem _fromCache) {
        final HttpData httpData = new HttpData(_fromCache.getResponseUrl(), _fromCache.getData(), Result.OK);

        this.request.setResult(httpData);
    }

}
