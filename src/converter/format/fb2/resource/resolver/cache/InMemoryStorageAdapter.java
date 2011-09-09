package converter.format.fb2.resource.resolver.cache;

import downloader.Data;
import util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Хранилище кэша ресурсов расположенное в памяти
 *
 * @author Igor Usenko
 *         Date: 06.11.2009
 */
public class InMemoryStorageAdapter implements StorageAdapter {

    private final Map<String, Container> items;

    private Map<String, CacheEntry> toc;

    private long freeSpace;

    public InMemoryStorageAdapter() {
        this.items = new HashMap<String, Container>();
        this.toc = new HashMap<String, CacheEntry>();

        this.freeSpace = Long.MAX_VALUE;
    }

    public void storeToc(final Map<String, CacheEntry> _toc) throws StorageAdapterException {
        Assert.notNull(_toc, "TOC is null");
        this.toc = _toc;
    }

    public Map<String, CacheEntry> loadToc() throws StorageAdapterException {
        return this.toc;
    }

    public String store(final Data _data) throws StorageAdapterException {
        Assert.notNull(_data, "Data is null");

        String name = String.valueOf(System.nanoTime());
        StoredItem item = new StoredItem(name, _data.size(), System.currentTimeMillis());
        Container container = new Container(item, _data);

        this.items.put(name, container);

        return name;
    }

    public Data load(final String _name) throws StorageAdapterException {
        Assert.isValidString(_name, "Name is not valid");

        Container container = this.items.get(_name);

        return container == null ? null : container.getData();
    }

    public void remove(final String _name) throws StorageAdapterException {
        Assert.isValidString(_name, "Name is not valid");
        this.items.remove(_name);
    }

    public Map<String, StoredItem> getMap() {
        Map<String, StoredItem> result = new HashMap<String, StoredItem>();

        for (String key : this.items.keySet()) {
            result.put(key, this.items.get(key).getItem());
        }

        return result;
    }

    public void setFreeSpace(final long _freeSpace) {
        Assert.greaterOrEqual(_freeSpace, 0, "Free space < 0");
        this.freeSpace = _freeSpace;
    }

    public long getFreeSpace() {
        return this.freeSpace;
    }

    public long getUsedSpace() {
        long result = 0;

        for (Container container : this.items.values()) {
            result += container.size();
        }

        return result;
    }

    public Map<String, Container> getItems() {
        return this.items;
    }

    public Map<String, CacheEntry> getToc() {
        return this.toc;
    }

    private class Container {
        private final StoredItem item;
        private final Data data;

        public Container(final StoredItem _item, final Data _data) {
            this.item = _item;
            this.data = _data;
        }

        public Data getData() {
            return this.data;
        }

        public StoredItem getItem() {
            return this.item;
        }

        public long size() {
            return this.data.size();
        }
    }
}
