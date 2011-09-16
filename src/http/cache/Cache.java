package http.cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import timeservice.TimeService;
import util.Assert;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Igor Usenko
 *         Date: 15.01.2009
 */
public class Cache {

    private final TimeService timeService;
    private final String path;
    private final long storageTime;
    private final Map<String, CacheItem> items;

    private final Log log;

    public Cache(final String _path, final long _storageTime, final TimeService _timeService) throws CacheException {
        Assert.isValidString(_path, "Cache path is not valid");
        Assert.notNull(_timeService, "Time service is null");

        this.path = _path;
        this.storageTime = _storageTime;
        this.timeService = _timeService;

        this.items = new HashMap<String, CacheItem>();

        this.log = LogFactory.getLog(getClass());

        preparePath();
    }

    public synchronized void clear() {

        for (String key : this.items.keySet()) {
            removeCacheFile(this.items.get(key));
        }

        this.items.clear();
    }

    public synchronized void put(final String _address, final String _responseAddress, final String _file, final String _charset) {
        Assert.isValidString(_address, "Address is not valid");
        Assert.isValidString(_responseAddress, "Response address is not valid");
        Assert.isValidString(_file, "File name is not valid");

        this.items.put(_address, new CacheItem(_address, _responseAddress, _file, _charset, this.timeService.getCurrentTime()));

        removeOldItems();
    }

    public synchronized CacheItem get(final String _address) {
        Assert.isValidString(_address, "Address is not valid");

        return this.items.get(_address);
    }

    private void preparePath() throws CacheException {
        File path = new File(this.path);

        if (!path.exists()) {

            if (!path.mkdirs()) {
                throw new CacheException("Can not create cache path [ " + this.path + " ].");
            }
        }
    }

    private void removeOldItems() {
        int index = 0;

        while (index < this.items.size()) {
            String[] keys = this.items.keySet().toArray(new String[this.items.size()]);

            CacheItem item = this.items.get(keys[index]);

            if (itemExpired(item)) {
                removeCacheFile(this.items.get(keys[index]));
                this.items.remove(keys[index]);

                index = -1;
            }

            ++index;
        }
    }

    private boolean itemExpired(final CacheItem _item) {

        return _item.getTime() + this.storageTime < this.timeService.getCurrentTime();
    }

    private void removeCacheFile(final CacheItem _cacheItem) {
        File file = new File(_cacheItem.getFileName());

        if (file.exists()) {

            if (!file.delete()) {
                this.log.debug("Error deleting cache file [ " + _cacheItem.getFileName() + " ].");
            }
        } else {
            this.log.debug("Can not find cache file to delete [ " + _cacheItem.getFileName() + " ].");
        }
    }

    public static class CacheException extends Exception {

        public CacheException() {
            super();
        }

        public CacheException(final String _s) {
            super(_s);
        }

        public CacheException(final String _s, final Throwable _throwable) {
            super(_s, _throwable);
        }

        public CacheException(final Throwable _throwable) {
            super(_throwable);
        }
    }
}
