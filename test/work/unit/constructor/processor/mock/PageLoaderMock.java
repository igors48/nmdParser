package work.unit.constructor.processor.mock;

import downloader.BatchLoader;
import downloader.Result;
import downloader.RequestList;
import downloader.BatchLoaderFuture;
import downloader.data.MemoryData;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.io.UnsupportedEncodingException;

import util.Assert;
import html.HttpData;

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

    public Map<String, HttpData> loadUrls(final List<String> _urls, final long _pauseBetweenRequests) {
        Assert.notNull(_urls, "Urls is null");
        
        Map<String, HttpData> result = new HashMap<String, HttpData>();
        result.put(_urls.get(0), new HttpData(_urls.get(0), new MemoryData(this.data, "UTF-8"), Result.OK));

        return result;
    }

    public Map<RequestList, HttpData> load(List<RequestList> _requests) {
        return null; 
    }

    public HttpData loadUrlWithReferer(final String _url, final String _referer) {
        this.referer = _referer;

        return new HttpData(_url, new MemoryData(this.data, "UTF-8"), Result.OK);
    }

    public String getReferer() {
        return this.referer;
    }

    public boolean cancelled() {
        return false;
    }
}
