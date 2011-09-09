package constructor.objects.storage.local.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;
import util.FileTools;
import util.PathTools;

import java.io.File;
import java.io.IOException;

/**
 * Интерфейс взаимодействия с локальной файловой системой
 *
 * @author Igor Usenko
 *         Date: 21.02.2009
 */
public class LocalHandler implements Handler {

    private final Log log;

    public LocalHandler() {
        this.log = LogFactory.getLog(getClass());
    }

    public void createDirectory(final String _directory) throws HandlerException {
        Assert.isValidString(_directory, "Directory is not valid");

        File file = new File(_directory);

        if (!file.mkdirs()) {
            throw new HandlerException("Error creating directory : " + _directory);
        }
    }

    public void deleteFile(final String _directory, final String _name)
            throws HandlerException {
        Assert.isValidString(_directory, "Directory is not valid");
        Assert.isValidString(_name, "Name is not valid");

        File file = new File(_directory + _name);

        if (!file.delete()) {
            throw new HandlerException("Error deleting file : " + _directory + _name);
        }
    }

    public void renameExistentFile(final String _directory, final String _name) throws HandlerException {
        Assert.isValidString(_directory, "Directory is not valid");
        Assert.isValidString(_name, "Name is not valid");

        File oldFile = new File(_directory, _name);
        File newFile = new File(_directory, PathTools.appendDateTimeToName(_name));

        if (!oldFile.renameTo(newFile)) {
            throw new HandlerException("Error renaming file [ " + oldFile.getName() + " ] to [ " + newFile.getName() + " ]");
        }
    }

    public boolean directoryExists(final String _directory) {
        Assert.isValidString(_directory, "Directory is not valid");

        return (new File(_directory)).exists();
    }

    public boolean fileExists(final String _directory, final String _name) {
        Assert.isValidString(_directory, "Directory is not valid");
        Assert.isValidString(_name, "Name is not valid");

        return (new File(_directory + _name)).exists();
    }

    public void storeFile(final String _directory, final String _name, final String _source)
            throws HandlerException {
        Assert.isValidString(_directory, "Directory is not valid");
        Assert.isValidString(_name, "Name is not valid");
        Assert.isValidString(_source, "Source is not valid");

        try {
            FileTools.copy(new File(_source), new File(_directory + _name));
        } catch (IOException e) {
            throw new HandlerException(e);
        }
    }

    public long freeSpace(final String _directory) {
        Assert.isValidString(_directory, "Directory is not valid");

        return (new File(_directory)).getFreeSpace();
    }

    public void clean(final String _root, final long _maxAge) {
        Assert.greater(_maxAge, 0, "Maximum age < 0.");
        Assert.isValidString(_root, "Root is not valid");
        Assert.isTrue((new File(_root)).exists(), "Root path is not exists");

        cleanDirectory(new File(_root), _maxAge, false);
    }

    private void cleanDirectory(final File _directory, final long _maxAge, boolean _removeIfEmpty) {
        File[] list = _directory.listFiles();

        for (File item : list) {

            if (item.isFile()) {
                cleanFile(item, _maxAge);
            } else {
                cleanDirectory(item, _maxAge, true);
            }
        }

        if (_removeIfEmpty) {
            list = _directory.listFiles();

            if (list.length == 0) {
                String message = _directory.delete() ?
                        "Empty directory [ " + _directory.getAbsolutePath() + " ] removed successfully." :
                        "Error removing empty directory [ " + _directory.getAbsolutePath() + " ].";
                this.log.debug(message);
            }
        }
    }

    private void cleanFile(final File _file, final long _maxAge) {

        if (_file.lastModified() < _maxAge) {
            String message = _file.delete() ?
                    "Old file [ " + _file.getAbsolutePath() + " ] removed successfully." :
                    "Error removing old file [ " + _file.getAbsolutePath() + " ].";
            this.log.debug(message);
        }
    }

}
