package constructor.objects.source.core.storage;

import constructor.objects.source.core.ModificationListStorage;
import dated.item.modification.stream.ModificationList;
import util.Assert;
import util.IOTools;
import util.PathTools;
import util.XmlStreamTools;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;

/**
 * Файловое хранилище списков модификаций. По файлу на каждый источник.
 *
 * @author Igor Usenko
 *         Date: 02.04.2009
 */
public class ModificationListFileStorage implements ModificationListStorage {

    private final String root;
    private static final String XML_EXTENSION = ".xml";

    public ModificationListFileStorage(final String _root) {
        Assert.isValidString(_root, "Modification list storage root name invalid.");
        this.root = PathTools.normalize(_root);
        Assert.isTrue(new File(_root).exists(), "Modification list storage root directory not exist.");
    }

    public synchronized void store(final String _sourceId, final ModificationList _list) throws ModificationListStorageException {
        Assert.isValidString(_sourceId, "Source id invalid.");
        Assert.notNull(_list, "Modification list is null.");

        //todo можно тут не херить старый файл пока не убедимся, что записался новый
        File file = getStorageFile(_sourceId);
        deleteFile(_sourceId, file);
        storeList(file, _list);
    }

    public synchronized ModificationList load(final String _sourceId) throws ModificationListStorageException {
        Assert.isValidString(_sourceId, "Source id invalid.");

        File file = getStorageFile(_sourceId);

        return loadList(file);
    }

    public synchronized void remove(final String _sourceId) throws ModificationListStorageException {
        Assert.isValidString(_sourceId, "Source id invalid.");

        File file = getStorageFile(_sourceId);

        deleteFile(_sourceId, file);
    }

    private ModificationList loadList(final File _file) throws ModificationListStorageException {
        ModificationList result = null;
        InputStream stream = null;
        XMLDecoder decoder = null;

        try {
            stream = new FileInputStream(_file);
            decoder = new XMLDecoder(stream);
            result = (ModificationList) decoder.readObject();
            decoder.close();
            stream.close();
        } catch (FileNotFoundException e) {
            result = new ModificationList();
        } catch (IOException e) {
            throw new ModificationListStorageException(e);
        } finally {
            XmlStreamTools.close(decoder);
            IOTools.close(stream);
        }

        return result;
    }

    private void storeList(final File _file, final ModificationList _list) throws ModificationListStorageException {
        OutputStream stream = null;
        XMLEncoder encoder = null;

        try {
            stream = new FileOutputStream(_file);
            encoder = new XMLEncoder(stream);
            encoder.writeObject(_list);
        } catch (FileNotFoundException e) {
            throw new ModificationListStorageException(e);
        } finally {
            XmlStreamTools.close(encoder);
            IOTools.close(stream);
        }
    }

    private void deleteFile(final String _sourceId, final File _file) throws ModificationListStorageException {

        if (_file.exists()) {

            if (!_file.delete()) {
                throw new ModificationListStorageException("Error deleting modification list storage for source [ " + _sourceId + " ].");
            }
        }
    }

    private File getStorageFile(final String _sourceId) {
        return new File(this.root + _sourceId + XML_EXTENSION);
    }
}
