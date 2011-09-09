package constructor.objects.channel.core.storage;

import constructor.objects.channel.core.ChannelDataListStorage;
import constructor.objects.channel.core.stream.ChannelDataList;
import util.Assert;
import util.IOTools;
import util.PathTools;
import util.XmlStreamTools;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

/**
 * Хранилище для данных канала, которое для реализации своих
 * целей использует файлы в указанном каталоге.
 *
 * @author Igor Usenko
 *         Date: 04.04.2009
 */
public class ChannelDataListFileStorage implements ChannelDataListStorage {

    private final String root;
    private static final String XML_EXTENSION = ".xml";

    public ChannelDataListFileStorage(final String _root) {
        Assert.isValidString(_root, "Channel data list storage root name invalid.");
        this.root = PathTools.normalize(_root);
        Assert.isTrue(new File(_root).exists(), "Channel data list storage root directory not exist.");
    }

    public synchronized void store(final String _channelId, final ChannelDataList _list) throws ChannelDataListStorageException {
        Assert.isValidString(_channelId, "Channel id invalid.");
        Assert.notNull(_list, "Channel data list is null.");

        //todo можно тут не херить старый файл пока не убедимся, что записался новый
        File file = getStorageFile(_channelId);
        deleteFile(_channelId, file);
        storeList(file, _list);
    }

    public synchronized ChannelDataList load(final String _channelId) throws ChannelDataListStorageException {
        Assert.isValidString(_channelId, "Channel id invalid.");

        File file = getStorageFile(_channelId);

        return loadList(file);
    }

    public synchronized void remove(final String _channelId) throws ChannelDataListStorageException {
        Assert.isValidString(_channelId, "Channel id invalid.");

        File file = getStorageFile(_channelId);

        deleteFile(_channelId, file);
    }

    private ChannelDataList loadList(final File _file) throws ChannelDataListStorageException {
        ChannelDataList result = null;
        InputStream stream = null;
        XMLDecoder decoder = null;

        try {
            stream = new FileInputStream(_file);
            decoder = new XMLDecoder(stream);
            result = (ChannelDataList) decoder.readObject();
            decoder.close();
            stream.close();
        } catch (FileNotFoundException e) {
            result = new ChannelDataList();
        } catch (IOException e) {
            throw new ChannelDataListStorageException(e);
        } finally {
            XmlStreamTools.close(decoder);
            IOTools.close(stream);
        }

        return result;
    }

    private void storeList(final File _file, final ChannelDataList _list) throws ChannelDataListStorageException {
        OutputStream stream = null;
        XMLEncoder encoder = null;

        try {
            stream = new FileOutputStream(_file);
            encoder = new XMLEncoder(stream);
            encoder.writeObject(_list);
        } catch (FileNotFoundException e) {
            throw new ChannelDataListStorageException(e);
        } finally {
            XmlStreamTools.close(encoder);
            IOTools.close(stream);
        }
    }

    private void deleteFile(final String _sourceId, final File _file) throws ChannelDataListStorageException {

        if (_file.exists()) {

            if (!_file.delete()) {
                throw new ChannelDataListStorageException("Error deleting modification list storage for source [ " + _sourceId + " ].");
            }
        }
    }

    private File getStorageFile(final String _sourceId) {
        return new File(this.root + _sourceId + XML_EXTENSION);
    }
}
