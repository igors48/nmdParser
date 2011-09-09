package debug.snapshot;

import debug.Snapshot;
import util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Отладочный снимок процессора
 *
 * @author Igor Usenko
 *         Date: 14.08.2009
 */
public class ProcessorSnapshot implements Snapshot {
    private final String name;
    private final List<NameValuePair> parameters;

    private static final String NULL_VALUE_TOKEN = "null";

    public ProcessorSnapshot(final String _name) {
        Assert.isValidString(_name, "Name is not valid");
        this.name = _name;

        this.parameters = new ArrayList<NameValuePair>();
    }

    public void addParameter(final String _name, final String _value) {
        Assert.isValidString(_name, "Name is not valid");

        this.parameters.add(new NameValuePair(_name, _value == null ? NULL_VALUE_TOKEN : _value));
    }

    public String getName() {
        return this.name;
    }

    public List<NameValuePair> getParameters() {
        return this.parameters;
    }
}
