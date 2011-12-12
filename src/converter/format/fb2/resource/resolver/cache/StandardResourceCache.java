package converter.format.fb2.resource.resolver.cache;

import http.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import timeservice.TimeService;
import util.Assert;

import java.util.*;

import static util.CollectionUtils.newArrayList;

/**
 * ��� ��������
 *
 * @author Igor Usenko
 *         Date: 03.11.2009
 */
public class StandardResourceCache implements ResourceCache {

    private final StorageAdapter adapter;
    private final TimeService timeService;
    private final long maxCacheSize;
    private final long maxItemSize;
    private final long maxAge;

    private Map<String, CacheEntry> toc;

    private int hitted;
    private long hitSize;
    private int putted;
    private long putSize;
    private int missed;
    private int stalled;

    private final Log log;

    public StandardResourceCache(final StorageAdapter _adapter, final TimeService _timeService, final long _maxAge, final long _maxCacheSize, final long _maxItemSize) {
        Assert.notNull(_adapter, "Storage adapter is null");
        this.adapter = _adapter;

        Assert.notNull(_timeService, "Time service is null");
        this.timeService = _timeService;

        Assert.greater(_maxAge, 0, "Maximum item age <= 0");
        this.maxAge = _maxAge;

        Assert.greater(_maxCacheSize, 0, "Maximum cache size <= 0");
        this.maxCacheSize = _maxCacheSize;

        Assert.greater(_maxItemSize, 0, "Maximum item size <= 0");
        Assert.less(_maxItemSize, _maxCacheSize, "Maximum item size > Maximum cache size");
        this.maxItemSize = _maxItemSize;

        this.log = LogFactory.getLog(getClass());

        prepare();
    }

    /**
     * �������� ������ � ���. ���� ����� ��� ��� ���� - ��������.
     * ������������� ������ ���� � ������� ������ �������� ���� �������� ���� �������.
     *
     * @param _url  �����
     * @param _data ������
     */
    public void put(final String _url, final Data _data) {
        Assert.isValidString(_url, "URL is not valid");
        Assert.notNull(_data, "Data is null");

        try {

            if (this.maxItemSize < _data.size()) {
                this.log.debug("Data can not be stored because its size [ " + _data.size() + " ] greater than maximum [ " + this.maxItemSize + " ] ");
            } else {

                if (this.adapter.getFreeSpace() < this.maxItemSize) {
                    reserveSpace(this.maxItemSize);
                }

                CacheEntry entry = getEntry(_url);

                if (entry != null) {
                    removeItemAndEntry(entry);
                }

                String id = storeItem(_data);
                putEntry(_url, id);

                this.putted++;
                this.putSize += _data.size();
            }
        } catch (StorageAdapter.StorageAdapterException e) {
            this.log.error("Error working with cache storage ", e);
        }
    }

    /**
     * ���������� ������ �� ����. ���� ��� ������������ - �������
     *
     * @param _url �����
     * @return ������ ��� null ���� ������ ������ � ���� ��� ��� ��� ������������
     */
    public Data get(final String _url) {
        Assert.isValidString(_url, "URL is not valid");

        Data result = null;

        try {
            CacheEntry entry = getEntry(_url);

            if (entry != null) {

                if (staled(entry)) {
                    removeItemAndEntry(entry);
                    this.stalled++;
                } else {
                    result = loadItem(entry);
                    this.hitted++;
                    this.hitSize += result.size();
                }
            } else {
                this.missed++;
            }
        } catch (StorageAdapter.StorageAdapterException e) {
            this.log.error("Error working with cache storage ", e);
        }

        return result;
    }

    public void commitToc() {

        try {
            this.log.debug("Cache TOC before commit");
            storeToc();
            this.log.debug("Cache TOC committed");
        } catch (StorageAdapter.StorageAdapterException e) {
            this.log.error("Error commiting cache TOC", e);
        }
    }

    public void stop() {

        try {
            reserveSpace(0);
            storeToc();
            this.log.debug("Resource cache stopped. Hit [ " + this.hitted + " / " + this.hitSize + " ] miss [ " + this.missed + " ] stall [ " + this.stalled + " ] put [ " + this.putted + " / " + this.putSize + " ]");
        } catch (StorageAdapter.StorageAdapterException e) {
            this.log.error("Error while storing TOC", e);
        }
    }

    private void audit() throws StorageAdapter.StorageAdapterException {
        removeOrphansStored();

        Map<String, StoredItem> storedItemsMap = this.adapter.getMap();

        for (String entryKey : this.toc.keySet()) {
            String name = this.toc.get(entryKey).getFile();

            if (storedItemsMap.get(name) == null) {
                this.toc.remove(entryKey);
            }
        }

        reserveSpace(0);
    }

    private void removeOrphansStored() throws StorageAdapter.StorageAdapterException {
        List<StoredItem> storedItems = getStoredItems();

        for (StoredItem item : storedItems) {
            CacheEntry entry = getEntryForName(item.getName());

            if (entry == null) {
                this.adapter.remove(item.getName());
            }
        }
    }

    private CacheEntry getEntry(final String _url) {
        return this.toc.get(_url);
    }

    private void putEntry(final String _url, final String _file) throws StorageAdapter.StorageAdapterException {
        CacheEntry entry = new CacheEntry();
        entry.setAddress(_url);
        entry.setFile(_file);
        entry.setCreated(this.timeService.getCurrentTime());

        this.toc.put(_url, entry);
    }

    private void removeEntry(final CacheEntry _entry) throws StorageAdapter.StorageAdapterException {
        this.toc.remove(_entry.getAddress());
    }

    private boolean staled(final CacheEntry _entry) {
        return _entry.getCreated() + this.maxAge < this.timeService.getCurrentTime();
    }

    private Data loadItem(final CacheEntry _entry) throws StorageAdapter.StorageAdapterException {
        return this.adapter.load(_entry.getFile());
    }

    private String storeItem(final Data _data) throws StorageAdapter.StorageAdapterException {
        return this.adapter.store(_data);
    }

    private void removeItem(final CacheEntry _entry) throws StorageAdapter.StorageAdapterException {
        this.adapter.remove(_entry.getFile());
    }

    private void loadToc() throws StorageAdapter.StorageAdapterException {
        this.toc = new HashMap<String, CacheEntry>(8000);
        this.toc.putAll(this.adapter.loadToc());
    }

    private void storeToc() throws StorageAdapter.StorageAdapterException {
        this.adapter.storeToc(this.toc);
    }

    private boolean reserveSpace(final long _amount) throws StorageAdapter.StorageAdapterException {
        long desiredSize = this.maxCacheSize - _amount;

        List<StoredItem> items = getStoredItems();
        Collections.sort(items, new StoredItemComparator());
        //long actualSize = calcItemsSize(items);
        long actualSize = this.adapter.getUsedSpace();
        Iterator<StoredItem> iterator = items.iterator();

        while (actualSize > desiredSize && iterator.hasNext()) {
            StoredItem item = iterator.next();
            CacheEntry entry = getEntryForName(item.getName());

            if (entry != null) {
                removeItemAndEntry(entry);
                actualSize -= item.getSize();
            }
        }

        //long currentSize = calcItemsSize(getStoredItems());
        long currentSize = this.adapter.getUsedSpace();

        return currentSize <= desiredSize;
    }

    private void removeItemAndEntry(final CacheEntry _entry) throws StorageAdapter.StorageAdapterException {
        removeItem(_entry);
        removeEntry(_entry);
    }

    private CacheEntry getEntryForName(final String _name) {
        CacheEntry result = null;

        for (String key : this.toc.keySet()) {
            CacheEntry current = this.toc.get(key);

            if (current.getFile().equals(_name)) {
                result = current;
                break;
            }
        }

        return result;
    }

    private List<StoredItem> getStoredItems() throws StorageAdapter.StorageAdapterException {
        List<StoredItem> result = newArrayList();

        result.addAll(this.adapter.getMap().values());

        return result;
    }

    private void prepare() {

        try {
            loadToc();
            audit();
            storeToc();
        } catch (Exception e) {
            this.log.error("Error preparing cache");
            this.log.debug(e);
        }
    }

}
