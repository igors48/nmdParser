package downloader.cache;

import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 15.01.2009
 */
public class CacheItem {
    private final String address;
    // for feed proxies
    private final String responseAddress;
    private final String file;
    private final String charset;
    private final long time;

    public CacheItem(final String _address, final String _responseAddress, final String _file, final String _charset, long _time) {
        Assert.isValidString(_address, "Address is not valid");
        Assert.isValidString(_responseAddress, "Response address is not valid");
        Assert.isValidString(_file, "File is not valid");
        Assert.notNull(_charset, "Charset is null");

        this.address = _address;
        this.responseAddress = _responseAddress;
        this.file = _file;
        this.time = _time;
        this.charset = _charset;
    }

    public String getAddress() {
        return this.address;
    }

    public String getResponseAddress() {
        return this.responseAddress;
    }

    public String getFileName() {
        return this.file;
    }

    public long getTime() {
        return this.time;
    }

    public String getCharset() {
        return this.charset;
    }
}
