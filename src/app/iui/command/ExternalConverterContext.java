package app.iui.command;

import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 24.03.2011
 */
public class ExternalConverterContext {

    private final String path;
    private final String pattern;
    private final long timeout;

    public ExternalConverterContext() {
        this("", "", -1);
    }

    public ExternalConverterContext(final String _path, final String _pattern, final long _timeout) {
        Assert.notNull(_path, "Path is null");
        this.path = _path;

        Assert.notNull(_pattern, "Pattern is null");
        this.pattern = _pattern;

        Assert.greaterOrEqual(_timeout, -1, "Timeout < -1");
        this.timeout = _timeout;
    }

    public String getPath() {
        return this.path;
    }

    public String getPattern() {
        return this.pattern;
    }

    public long getTimeout() {
        return this.timeout;
    }

    public boolean isEmpty() {
        return this.path.isEmpty();
    }
}
