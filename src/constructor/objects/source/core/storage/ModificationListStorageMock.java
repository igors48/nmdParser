package constructor.objects.source.core.storage;

import constructor.objects.source.core.ModificationListStorage;
import dated.item.modification.stream.ModificationList;
import util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Igor Usenko
 *         Date: 05.03.2009
 */
public class ModificationListStorageMock implements ModificationListStorage {

    private final Map<String, ModificationList> map;

    public ModificationListStorageMock() {
        this.map = new HashMap<String, ModificationList>();
    }

    public synchronized void store(String _sourceId, ModificationList _list) throws ModificationListStorageException {
        Assert.isValidString(_sourceId);
        Assert.notNull(_list);

        this.map.put(_sourceId, _list);
    }

    public synchronized ModificationList load(String _sourceId) throws ModificationListStorageException {
        Assert.isValidString(_sourceId);

        ModificationList result = this.map.get(_sourceId);

        if (result == null) {
            result = new ModificationList();
        }

        return result;
    }

    public synchronized void remove(String _sourceId) throws ModificationListStorageException {
        Assert.isValidString(_sourceId);

        this.map.remove(_sourceId);
    }
}
