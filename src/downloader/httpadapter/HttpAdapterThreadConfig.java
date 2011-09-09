package downloader.httpadapter;

import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 28.09.2008
 */
public class HttpAdapterThreadConfig {
    private final String workDirectory;
    private final int tryCount;
    private final int socketTimeout;
    private final int errorTimeout;
    private final HttpAdapterThreadOwner owner;
    private final int minTimeout;

    public HttpAdapterThreadConfig(final String _workDirectory,
                                   final int _tryCount,
                                   final int _socketTimeout,
                                   final int _errorTimeout,
                                   final int _minTimeout,
                                   final HttpAdapterThreadOwner _owner) {
        Assert.isValidString(_workDirectory, "Work directory name is not valid.");
        Assert.greaterOrEqual(_tryCount, 1, "Try count < 1");
        Assert.greater(_socketTimeout, 0, "Socket timeout <= 0");
        Assert.greater(_errorTimeout, 0, "Error timeout <= 0");
        Assert.greater(_minTimeout, 0, "Minimum timeout <= 0");
        Assert.notNull(_owner, "Thread owner is null");

        this.workDirectory = _workDirectory;
        this.tryCount = _tryCount;
        this.socketTimeout = _socketTimeout;
        this.errorTimeout = _errorTimeout;
        this.minTimeout = _minTimeout;
        this.owner = _owner;
    }

    public String getWorkDirectory() {
        return this.workDirectory;
    }

    public int getMaxTryCount() {
        return this.tryCount;
    }

    public int getSocketTimeout() {
        return this.socketTimeout;
    }

    public int getErrorTimeout() {
        return this.errorTimeout;
    }

    public HttpAdapterThreadOwner getOwner() {
        return this.owner;
    }

    public int getMinTimeout() {
        return this.minTimeout;
    }
}
