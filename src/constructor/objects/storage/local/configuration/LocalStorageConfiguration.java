package constructor.objects.storage.local.configuration;

import constructor.dom.Blank;
import constructor.dom.UsedObject;
import util.Assert;

import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * Конфигурация локального хранилища выходных документов
 *
 * @author Igor Usenko
 *         Date: 08.04.2009
 */
public class LocalStorageConfiguration implements Blank {

    private String id;
    private String root;
    private long age;

    private static final int TO_MILLIS = 24 * 60 * 60 * 1000;

    public void setId(final String _id) {
        Assert.isValidString(_id, "Local storage id is not valid.");
        this.id = _id;
    }

    public List<UsedObject> getUsedObjects() {
        return newArrayList();
    }

    public void setRoot(final String _value) {
        Assert.isValidString(_value, "Storage root is invalid.");
        this.root = _value;
    }

    public void setAge(final String _value) {
        Assert.isValidString(_value, "Days value is invalid.");

        try {
            this.age = Long.valueOf(_value) * TO_MILLIS;
        } catch (Exception e) {
            this.age = 0;
        }
    }

    public long getAge() {
        return this.age;
    }

    public String getId() {
        return this.id;
    }

    public String getRoot() {
        return this.root;
    }
}
