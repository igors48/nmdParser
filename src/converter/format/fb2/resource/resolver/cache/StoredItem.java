package converter.format.fb2.resource.resolver.cache;

import util.Assert;

/**
 * Данные о сохраненном в хранилище элементе кэша
 *
 * @author Igor Usenko
 *         Date: 03.11.2009
 */
public class StoredItem {

    private final String name;
    private final long size;
    private final long created;

    public StoredItem(final String _name, final long _size, final long _created) {
        Assert.isValidString(_name, "Name is not valid");
        this.name = _name;

        Assert.greaterOrEqual(_size, 0, "Size < 0");
        this.size = _size;

        Assert.greater(_created, 0, "Creation time <= 0");
        this.created = _created;
    }

    public long getCreated() {
        return this.created;
    }

    public String getName() {
        return this.name;
    }

    public long getSize() {
        return this.size;
    }
}
