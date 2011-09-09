package constructor.objects.storage.local.core.mock;

import constructor.objects.storage.local.core.Handler;
import util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LocalHandlerMock implements Handler {

    private final Map<String, List<String>> root;


    public LocalHandlerMock() {
        this.root = new HashMap<String, List<String>>();
    }

    public void createDirectory(final String _directory) throws HandlerException {
        Assert.isValidString(_directory);

        this.root.put(_directory, new ArrayList<String>());
    }

    public void deleteFile(final String _directory, final String _name)
            throws HandlerException {

        List<String> item = getDirectory(_directory);

        if (fileExistsInDirectory(_directory, _name)) {
            item.remove(_name);
        } else {
            throw new HandlerException("Can`t find file " + _name + " in directory " + _directory);
        }
    }

    public void renameExistentFile(String _directory, String _name) throws HandlerException {
    }

    public boolean directoryExists(final String _directory) {
        Assert.isValidString(_directory);

        return this.root.get(_directory) != null;
    }

    public boolean fileExists(final String _directory, final String _name) {
        Assert.isValidString(_directory);
        Assert.isValidString(_name);

        return fileExistsInDirectory(_directory, _name);
    }

    public void storeFile(final String _directory, final String _name, final String _source)
            throws HandlerException {
        Assert.isValidString(_directory);
        Assert.isValidString(_name);
        Assert.isValidString(_source);

        List<String> item = getDirectory(_directory);
        item.add(_name);
    }

    public long freeSpace(final String _directory) {
        return Long.MAX_VALUE;
    }

    public void clean(final String _root, final long _maxAge) {
        // empty
    }

    public void removeDirectory(final String _directory) {
        Assert.isValidString(_directory);

        this.root.remove(_directory);
    }

    private List<String> getDirectory(String _directory) {
        List<String> result = this.root.get(_directory);

        if (result == null) {
            result = new ArrayList<String>();
        }

        return result;
    }

    private boolean fileExistsInDirectory(String _directory, String _filename) {

        List<String> directory = getDirectory(_directory);

        return directory.contains(_filename);
    }
}
