package debug.snapshot;

import util.Assert;

/**
 * Комплект "значение - флаг модификации"
 *
 * @author Igor Usenko
 *         Date: 19.08.2009
 */
public class ValueChangedPair {

    private final String value;
    private final boolean changed;

    public ValueChangedPair(final String _value, final boolean _changed) {
        Assert.notNull(_value, "Value is null");
        this.value = _value;

        this.changed = _changed;
    }

    public boolean isChanged() {
        return this.changed;
    }

    public String getValue() {
        return this.value;
    }
}
