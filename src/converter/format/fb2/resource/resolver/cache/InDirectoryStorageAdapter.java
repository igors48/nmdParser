package converter.format.fb2.resource.resolver.cache;

import http.Data;
import http.data.DataFile;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;
import util.FileTools;
import util.IOTools;
import util.PathTools;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * ��������� ������ ���������� ���� ������������� � ��������
 *
 * @author Igor Usenko
 *         Date: 08.11.2009
 */
public class InDirectoryStorageAdapter implements StorageAdapter {

    private static final String TOC_NAME = "toc";
    private static final String DATA_FILE_PREFIX = "data";
    private static final FileFilter DATA_FILE_FILTER = new Filter();

    private final String root;
    private final File rootDirectory;

    private final Log log;

    public InDirectoryStorageAdapter(final String _root) {
        Assert.isValidString(_root, "Cache storage root is not valid");
        Assert.isTrue(new File(_root).exists(), "Cache storage root does not exists");
        this.root = PathTools.normalize(_root);
        this.rootDirectory = new File(this.root);

        this.log = LogFactory.getLog(getClass());
    }

    public void storeToc(final Map<String, CacheEntry> _entries) throws StorageAdapterException {
        Assert.notNull(_entries, "TOC is null");

        OutputStream stream = null;
        XMLEncoder encoder = null;

        try {
            stream = new FileOutputStream(getTocName());
            encoder = new XMLEncoder(stream);
            encoder.writeObject(_entries);
        } catch (FileNotFoundException e) {
            throw new StorageAdapterException(e);
        } finally {
            closeEncoder(encoder);
            IOTools.close(stream);
        }
    }

    public Map<String, CacheEntry> loadToc() throws StorageAdapterException {
        Map<String, CacheEntry> result = null;
        InputStream stream = null;
        XMLDecoder decoder = null;

        try {
            stream = new FileInputStream(getTocName());
            decoder = new XMLDecoder(stream);
            result = (Map<String, CacheEntry>) decoder.readObject();
            decoder.close();
            stream.close();
        } catch (FileNotFoundException e) {
            result = new HashMap<String, CacheEntry>();
        } catch (IOException e) {
            throw new StorageAdapterException(e);
        } finally {
            closeDecoder(decoder);
            IOTools.close(stream);
        }

        return result;
    }

    public String store(final Data _data) throws StorageAdapterException {
        Assert.notNull(_data, "Data is null");

        String result = "";

        try {

            if (_data instanceof DataFile) {
                result = storeDataFile((DataFile) _data);
            } else {
                result = storeMemoryData(_data);
            }

            return result;
        } catch (Data.DataException e) {
            throw new StorageAdapterException(e);
        } catch (IOException e) {
            throw new StorageAdapterException(e);
        }
    }

    public Data load(final String _name) throws StorageAdapterException {
        Assert.isValidString(_name, "Name is not valid");

        return new DataFile(getDataName(_name));
    }

    public void remove(final String _name) throws StorageAdapterException {
        Assert.isValidString(_name, "Name is not valid");

        File file = new File(getDataName(_name));

        if (!file.delete()) {
            this.log.error("Error deleting file [ " + _name + " ] from cache directory");
        }
    }

    public Map<String, StoredItem> getMap() {
        Map<String, StoredItem> result = new HashMap<String, StoredItem>();

        File[] files = this.rootDirectory.listFiles(DATA_FILE_FILTER);

        for (File file : files) {
            String key = file.getName();
            StoredItem item = new StoredItem(key, file.length(), file.lastModified());
            result.put(key, item);
        }

        return result;
    }

    public long getFreeSpace() {
        return this.rootDirectory.getFreeSpace();
    }

    public long getUsedSpace() {
        long result = 0;

        for (File file : this.rootDirectory.listFiles(DATA_FILE_FILTER)) {
            result += file.length();
        }

        return result;
    }

    private String getTocName() {
        return getDataName(TOC_NAME);
    }

    private String getDataName(final String _name) {
        return this.root + _name;
    }

    private void closeDecoder(final XMLDecoder _decoder) {

        if (_decoder != null) {
            _decoder.close();
        }
    }

    private void closeEncoder(final XMLEncoder _encoder) {

        if (_encoder != null) {
            _encoder.close();
        }
    }

    private String storeMemoryData(final Data _data) throws Data.DataException, IOException {
        OutputStream out = null;

        try {
            byte[] buffer = _data.getData();
            File file = File.createTempFile(DATA_FILE_PREFIX, "", this.rootDirectory);
            out = new FileOutputStream(file);
            out.write(buffer);

            return file.getName();
        } finally {
            IOTools.close(out);
        }
    }

    private String storeDataFile(final DataFile _data) throws Data.DataException, IOException {
        File file = File.createTempFile(DATA_FILE_PREFIX, "", this.rootDirectory);

        FileTools.fastCopy(_data.getFile(), file);

        return file.getName();
    }

    private static class Filter implements FileFilter {

        public boolean accept(final File _file) {
            return _file.getName().startsWith(DATA_FILE_PREFIX);
        }
    }
}
