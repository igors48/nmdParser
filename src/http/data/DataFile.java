package http.data;

import http.Data;
import util.Assert;
import util.IOTools;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * @author Igor Usenko
 *         Date: 27.09.2008
 */
public class DataFile implements Data {

    private final String address;
    private final File file;
    private final String charset;

    public DataFile(final String _address) {
        Assert.isValidString(_address, "File address is not valid.");

        this.address = _address;
        this.charset = null;
        this.file = new File(_address);

        Assert.isTrue(this.file.exists(), "File for address [ " + _address + " ] does not exists.");
    }

    public DataFile(final String _address, final String _charset) {
        Assert.notNull(_address);

        this.address = _address;
        this.file = new File(_address);
        this.charset = _charset;

        Assert.isTrue(this.file.exists(), "File for address [ " + _address + " ] does not exists.");
    }

    public byte[] getData() throws DataException {
        RandomAccessFile dataFile = null;

        try {
            dataFile = new RandomAccessFile(this.file, "r");

            byte[] result = new byte[(int) dataFile.length()];
            dataFile.readFully(result);

            return result;
        } catch (Exception e) {
            throw new DataException(e);
        } finally {
            IOTools.close(dataFile);
        }
    }

    public byte[] getData(final int _from, final int _to) throws DataException {
        Assert.isTrue(_to >= _from, "");
        Assert.isTrue(_from >= 0, "");

        RandomAccessFile dataFile = null;

        try {
            dataFile = new RandomAccessFile(this.file, "r");

            int len = _to - _from + 1;
            byte[] result = new byte[len];
            int readed = dataFile.read(result, _from, len);

            if (readed != len) {
                throw new DataException("Expected [ " + len + " ] but [ " + readed + " ] bytes readed.");
            }

            return result;
        } catch (Exception e) {
            throw new DataException(e);
        } finally {
            IOTools.close(dataFile);
        }
    }

    public String getCharsetName() {
        return this.charset;
    }

    public int size() {
        return (int) this.file.length();
    }

    public String getAddress() {
        return this.address;
    }

    public File getFile() {
        return this.file;
    }
}
