package work.unit.constructor.processor.mock;

import app.controller.Controller;
import html.HttpData;
import http.BatchLoader;
import http.Result;
import http.data.MemoryData;
import util.Assert;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import static util.CollectionUtils.newHashMap;

/**
 * @author Igor Usenko
 *         Date: 04.08.2009
 */
public class PageLoaderMock implements BatchLoader {

    private final byte[] data;
    private String referer;

    public PageLoaderMock(final String _data) throws UnsupportedEncodingException {
        Assert.isValidString(_data, "Data is not valid");
        this.data = _data.getBytes("UTF-8");
    }

    public Map<String, HttpData> loadUrls(final List<String> _urls, final long _pauseBetweenRequests, final Controller _controller) {
        Assert.notNull(_urls, "Urls is null");

        Map<String, HttpData> result = newHashMap();
        result.put(_urls.get(0), new HttpData(_urls.get(0), new MemoryData(this.data, "UTF-8"), Result.OK));

        return result;
    }

    public HttpData loadUrlWithReferer(final String _url, final String _referer) {
        this.referer = _referer;

        return new HttpData(_url, new MemoryData(this.data, "UTF-8"), Result.OK);
    }

    public HttpData loadUrl(String _url) {
        return null;
    }

    public String getReferer() {
        return this.referer;
    }

    public void cancel() {
        // empty
    }
}
