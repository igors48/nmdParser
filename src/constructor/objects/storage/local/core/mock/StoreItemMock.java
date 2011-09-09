package constructor.objects.storage.local.core.mock;

import constructor.objects.storage.local.core.StoreItem;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 04.03.2009
 */
public class StoreItemMock implements StoreItem {

    private final String branch;
    private final String name;
    private final boolean remove;

    private boolean removed;

    public StoreItemMock(String _branch, String _name, boolean _remove) {
        Assert.isValidString(_branch);
        Assert.isValidString(_name);

        this.branch = _branch;
        this.name = _name;
        this.remove = _remove;
    }

    public String getBranch() {
        return this.branch;
    }

    public String getName() {
        return this.name;
    }

    public long getSize() {
        return 0;
    }

    public boolean isRemove() {
        return this.remove;
    }

    public boolean remove() {
        this.removed = true;
        return true;
    }

    public boolean isRemoveExists() {
        return true;
    }

    public boolean isRemoved() {
        return this.removed;
    }
}
