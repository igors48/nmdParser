package http.cache;

import http.Data;
import util.Assert;

import java.util.Map;

import static util.CollectionUtils.newHashMap;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 19.09.2011
 */
public class InMemoryCache {

    private final Map<String, InMemoryCacheItem> items;

    public InMemoryCache() {
        this.items = newHashMap();
    }

    public synchronized void put(final String _targetAddress, final String _responseAddress, final Data _data) {
        Assert.isValidString(_targetAddress, "Target address is not valid");
        Assert.isValidString(_responseAddress, "Response address is not valid");
        Assert.notNull(_data, "Data is null");

        final InMemoryCacheItem newItem = new InMemoryCacheItem(_responseAddress, _data);

        this.items.put(_targetAddress, newItem);
    }

    public synchronized InMemoryCacheItem get(final String _address) {
        Assert.isValidString(_address, "Address is not valid");

        return this.items.get(_address);
    }
}
