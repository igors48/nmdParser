package debug.snapshot;

import debug.Snapshot;
import util.Assert;

import java.util.Map;

import static util.CollectionUtils.newHashMap;

/**
 * Отладочный снимок переменных
 *
 * @author Igor Usenko
 *         Date: 19.08.2009
 */
public class VariablesSnapshot implements Snapshot {

    private final Map<String, Map<Integer, ValueChangedPair>> items;

    public VariablesSnapshot() {
        this.items = newHashMap();
    }

    public void addElement(final String _name, final int _index, final String _value, final boolean _changed) {
        Assert.isValidString(_name, "Name is not valid");
        Assert.greaterOrEqual(_index, 0, "Index < 0");
        Assert.notNull(_value, "Value is null");

        getMap(_name).put(_index, new ValueChangedPair(_value, _changed));
    }

    public Map<String, Map<Integer, ValueChangedPair>> getItems() {
        return this.items;
    }

    private Map<Integer, ValueChangedPair> getMap(final String _name) {

        Map<Integer, ValueChangedPair> result = this.items.get(_name);

        if (result == null) {
            result = newHashMap();
            this.items.put(_name, result);
        }

        return result;
    }
}
