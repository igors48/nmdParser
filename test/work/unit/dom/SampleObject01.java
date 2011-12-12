package work.unit.dom;

import constructor.dom.Blank;
import constructor.dom.UsedObject;
import debug.Snapshot;
import util.Assert;

import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 15.03.2009
 */
public class SampleObject01 implements Blank {
    private String id;
    private String name;
    private String inner;
    private Object object;

    public void setAttributeId(final String _id) {
        Assert.isValidString(_id);
        this.id = _id;
    }

    public void setAttributeName(final String _name) {
        Assert.isValidString(_name);
        this.name = _name;
    }

    public void setAttributeInner(final String _inner) {
        Assert.isValidString(_inner);
        this.inner = _inner;
    }

    public void setAttributeObject(final Object _object) {
        Assert.notNull(_object);
        this.object = _object;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getInner() {
        return this.inner;
    }

    public Object getObject() {
        return this.object;
    }

    public void setId(String _id) {
        Assert.isValidString(_id, "Id is not valid.");
        this.id = _id;
    }

    public void appendSnapshot(final Snapshot _snapshot) {
        // empty
    }

    public List<UsedObject> getUsedObjects() {
        return newArrayList();
    }
}
