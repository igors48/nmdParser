package variables;

import util.Assert;

import java.util.Map;
import java.util.Set;

import static util.CollectionUtils.newHashMap;

/**
 * @author Igor Usenko
 *         Date: 01.01.2011
 */
public class Variable {

    private final Map<Integer, String> values;

    public Variable() {
        this.values = newHashMap();
    }

    public Variable(final Variable _source) {
        Assert.notNull(_source, "Source is null");
        this.values = newHashMap();

        this.values.putAll(_source.values);
    }

    public String get(final int _index) {
        Assert.greaterOrEqual(_index, 0, "Index < 0");
        return this.values.get(_index);
    }

    public int size() {
        return this.values.size();
    }

    public Set<Integer> keySet() {
        return this.values.keySet();
    }

    public void put(final int _index, final String _value) {
        Assert.greaterOrEqual(_index, 0, "Index < 0");
        Assert.notNull(_value, "Value is null");
        this.values.put(_index, _value);
    }
}
