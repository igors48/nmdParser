package http.data;

import http.Data;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 27.09.2008
 */
public class MemoryData implements Data {

    private final byte[] data;
    private final String charset;

    public MemoryData(byte[] _data) {
        Assert.notNull(_data);

        this.data = _data;
        this.charset = null;
    }

    public MemoryData(byte[] _data, String _charset) {
        Assert.notNull(_data);
        Assert.isValidString(_charset);

        this.data = _data;
        this.charset = _charset;
    }

    public byte[] getData() throws DataException {
        return this.data;
    }

    public byte[] getData(int _from, int _to) throws DataException {
        Assert.isTrue(_to >= _from, "");
        Assert.isTrue(_from >= 0, "");

        int len = _to - _from + 1;
        byte[] result = new byte[len];
        System.arraycopy(this.data, _from, result, 0, len);

        return result;
    }

    public int size() {
        return this.data.length;
    }

    public String getCharsetName() {
        return this.charset;
    }
}
