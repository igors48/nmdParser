package debug.snapshot;

import util.Assert;

/**
 * Пара "имя-значение"
 *
 * @author Igor Usenko
 *         Date: 14.08.2009
 */
public class NameValuePair {

    private final String name;
    private final String value;

    public NameValuePair(final String _name, final String _value) {
        Assert.isValidString(_name, "Name is not valid");
        this.name = _name;

        Assert.notNull(_value, "Value is null");
        this.value = _value;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }
}
