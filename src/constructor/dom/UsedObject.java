package constructor.dom;

import util.Assert;

/**
 * Контейнер для данных об используемом объекте: тип и идентификатор
 *
 * @author Igor Usenko
 *         Date: 08.03.2010
 */
public class UsedObject {
    private final ObjectType type;
    private final String id;

    public UsedObject(final String _id, final ObjectType _type) {
        Assert.isValidString(_id, "Object id is not valid");
        this.id = _id;

        Assert.notNull(_type, "Type is null");
        this.type = _type;
    }

    public String getId() {
        return this.id;
    }

    public ObjectType getType() {
        return this.type;
    }
}
