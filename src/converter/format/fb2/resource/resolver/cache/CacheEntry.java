package converter.format.fb2.resource.resolver.cache;

import util.Assert;

/**
 * Элемент кэша ресурсов
 *
 * @author Igor Usenko
 *         Date: 01.11.2009
 */
public class CacheEntry {
    private String address;
    private String file;
    private long created;

    public CacheEntry() {
        this.address = "";
        this.file = "";
        this.created = 0;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(final String _address) {
        Assert.isValidString(_address, "Address is not valid");
        this.address = _address;
    }

    public long getCreated() {
        return this.created;
    }

    public void setCreated(final long _created) {
        Assert.greater(_created, 0, "Creation time <= 0");
        this.created = _created;
    }

    public String getFile() {
        return this.file;
    }

    public void setFile(final String _file) {
        Assert.isValidString(_file, "File is not valid");
        this.file = _file;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CacheEntry)) return false;

        CacheEntry that = (CacheEntry) o;

        if (created != that.created) return false;
        if (!address.equals(that.address)) return false;
        if (!file.equals(that.file)) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = address.hashCode();
        result = 31 * result + file.hashCode();
        result = 31 * result + (int) (created ^ (created >>> 32));
        return result;
    }
}
