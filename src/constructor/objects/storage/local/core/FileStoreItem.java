package constructor.objects.storage.local.core;

import util.Assert;

import java.io.File;

/**
 * Файл - единица работы для хранилища
 *
 * @author Igor Usenko
 *         Date: 21.02.2009
 */
public class FileStoreItem implements StoreItem {

    private final String branch;
    private final String fileName;
    private final boolean remove;
    private final boolean removeExists;

    public FileStoreItem(final String _branch, final String _fileName, final boolean _remove, final boolean _removeExists) {
        Assert.notNull(_branch, "Branch is null");
        //Assert.isValidString(_branch);
        Assert.isValidString(_fileName, "File name is not valid");

        this.branch = _branch;
        this.fileName = _fileName;
        this.remove = _remove;
        this.removeExists = _removeExists;
    }

    public String getBranch() {
        return this.branch;
    }

    public String getName() {
        return this.fileName;
    }

    public long getSize() {
        return (new File(this.fileName)).length();
    }

    public boolean isRemove() {
        return this.remove;
    }

    public boolean remove() {
        return (new File(this.fileName)).delete();
    }

    public boolean isRemoveExists() {
        return this.removeExists;
    }
}
