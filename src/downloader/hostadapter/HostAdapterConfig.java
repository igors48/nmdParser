package downloader.hostadapter;

import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 27.09.2008
 */
public class HostAdapterConfig {
    private final int corePoolSize;
    private final int maxPoolSize;
    private final int keepAliveTime;

    public HostAdapterConfig(int _corePoolSize, int _maxPoolSize, int _keepAliveTime) {
        Assert.greater(_corePoolSize, 0, "");
        Assert.greater(_keepAliveTime, 0, "");
        Assert.greater(_maxPoolSize, 0, "");

        this.corePoolSize = _corePoolSize;
        this.keepAliveTime = _keepAliveTime;
        this.maxPoolSize = _maxPoolSize;
    }

    public int getCorePoolSize() {
        return this.corePoolSize;
    }

    public int getKeepAliveTime() {
        return this.keepAliveTime;
    }

    public int getMaxPoolSize() {
        return this.maxPoolSize;
    }
}
