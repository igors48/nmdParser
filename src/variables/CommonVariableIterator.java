package variables;

import util.Assert;

import java.util.Arrays;

/**
 * Итератор переменной
 *
 * @author Igor Usenko
 *         Date: 19.07.2009
 */
public class CommonVariableIterator implements VariableIterator {

    private final Variable values;
    private final Integer[] keys;

    private int index;

    public CommonVariableIterator(final Variable _variable) {
        Assert.notNull(_variable, "Variable is null.");
        this.values = _variable;

        this.keys = _variable.keySet().toArray(new Integer[_variable.size()]);
        Arrays.sort(this.keys);

        this.index = 0;
    }

    public boolean hasNext() {
        return this.index < this.keys.length;
    }

    public Object next() {
        Object result = this.values.get(this.keys[this.index]);
        ++this.index;

        return result;
    }

    public int getIndex() {
        return this.index;
    }

    public int getKey() {
        return this.keys[this.index];
    }

    public int getSize() {
        return this.keys.length;
    }

    public void remove() {
        // empty
    }
}
