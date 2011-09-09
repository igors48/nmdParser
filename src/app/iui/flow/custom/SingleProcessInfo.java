package app.iui.flow.custom;

import util.Assert;

/**
 * Полная информация о ходе выполнения команды
 *
 * @author Igor Usenko
 *         Date: 05.05.2010
 */
public class SingleProcessInfo {
    private String code;
    private final Object[] data;
    private final int current;
    private final int total;
    private final boolean indeterminate;

    public SingleProcessInfo(final String _code, final Object[] _data, final int _current, final int _total) {
        this(_code, _data, _current, _total, false);
    }

    public SingleProcessInfo(final String _code, final int _current, final int _total) {
        this(_code, new Object[]{}, _current, _total, false);
    }

    public SingleProcessInfo(String _code) {
        this(_code, new Object[]{}, 0, 0, true);
    }

    private SingleProcessInfo(final String _code, final Object[] _data, final int _current, final int _total, final boolean _indeterminate) {
        Assert.isValidString(_code, "Code is not valid");
        this.code = _code;

        Assert.notNull(_data, "Data is null");
        this.data = _data;

        Assert.greaterOrEqual(_current, 0, "Current < 0");
        this.current = _current;

        Assert.greaterOrEqual(_total, 0, "Total < 0");
        this.total = _total;

        this.indeterminate = _indeterminate;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(final String _code) {
        Assert.isValidString(_code, "Code is not valid");
        this.code = _code;
    }

    public int getCurrent() {
        return this.current;
    }

    public Object[] getData() {
        return this.data;
    }

    public int getTotal() {
        return this.total;
    }

    public boolean isIndeterminate() {
        return this.indeterminate;
    }
}
