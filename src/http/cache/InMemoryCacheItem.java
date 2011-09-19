package http.cache;

import http.Data;
import util.Assert;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 19.09.2011
 */
public class InMemoryCacheItem {

    private final String responseUrl;
    private final Data data;

    public InMemoryCacheItem(final String _responseUrl, final Data _data) {
        Assert.isValidString(_responseUrl, "Url is not valid");
        this.responseUrl = _responseUrl;

        Assert.notNull(_data, "Data is null");
        this.data = _data;
    }

    public String getResponseUrl() {
        return this.responseUrl;
    }

    public Data getData() {
        return this.data;
    }
    
}
