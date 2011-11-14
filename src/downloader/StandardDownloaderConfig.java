package downloader;

import timeservice.TimeService;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 28.09.2008
 */
public class StandardDownloaderConfig {
    private final int corePoolSize;
    private final int maxPoolSize;
    private final int keepAliveTime;

    private final String workDirectory;
    private final long cacheStorageTime;

    private final TimeService timeService;

    private final int bannedListTreshold;
    private final int bannedListLimit;

    private final int maxConnectionsPerHost;
    private final int maxTotalConnections;

    private final String userAgent;

    private final boolean useProxy;
    private final String proxyHost;
    private final int proxyPort;
    private final String proxyUserName;
    private final String proxyUserPassword;

    private final int tryCount;
    private final int socketTimeout;
    private final int errorTimeout;
    private final int minTimeout;

    public StandardDownloaderConfig(final int _corePoolSize,
                                    final int _maxPoolSize,
                                    final int _keepAliveTime,
                                    final String _workDirectory,
                                    final TimeService _timeService,
                                    final long _cacheStorageTime,
                                    final int _bannedListTreshold,
                                    final int _bannedListLimit,
                                    final int _maxConnectionsPerHost,
                                    final int _maxTotalConnections,
                                    final String _userAgent,
                                    final boolean _useProxy,
                                    final String _proxyHost,
                                    final int _proxyPort,
                                    final String _proxyUserName,
                                    final String _proxyUserPassword,
                                    final int _tryCount,
                                    final int _socketTimeout,
                                    final int _errorTimeout,
                                    final int _minTimeout) {
        Assert.isValidString(_workDirectory, "Simple downloader config : work directory is not valid.");
        Assert.greater(_cacheStorageTime, 0, "Simple downloader config : cache storage time <= 0.");

        Assert.greater(_corePoolSize, 0, "Simple downloader config : pool size <= 0.");
        Assert.greater(_keepAliveTime, 0, "Simple downloader config : keep alive time <= 0.");
        Assert.greater(_maxPoolSize, 0, "Simple downloader config : max pool size <=0.");

        Assert.notNull(_timeService, "Simple downloader config : time service is null.");

        Assert.greater(_bannedListTreshold, 0, "Simple downloader config : bannedListTreshold <= 0.");
        Assert.greater(_bannedListLimit, 0, "Simple downloader configr : bannedListLimit <= 0.");
        Assert.greater(_maxConnectionsPerHost, 0, "Simple downloader config : maxConnectionsPerHost <= 0.");
        Assert.greater(_maxTotalConnections, 0, "Simple downloader config : maxTotalConnections <= 0.");

        Assert.isValidString(_userAgent, "Simple downloader config : user agent is not valid.");

        Assert.greaterOrEqual(_tryCount, 1, "Simple downloader config : Try count < 1");
        Assert.greater(_socketTimeout, 0, "Simple downloader config : Socket timeout <= 0");
        Assert.greater(_errorTimeout, 0, "Simple downloader config : Error timeout <= 0");
        Assert.greater(_minTimeout, 0, "Http adapter : Minimum timeout <= 0");

        if (_useProxy) {
            Assert.isValidString(_proxyHost, "Simple downloader config : proxyHost is not valid.");
            Assert.isValidString(_proxyUserName, "Simple downloader config : proxyUserName is not valid.");
            Assert.isValidString(_proxyUserPassword, "Simple downloader config : proxyUserPassword is not valid.");
            Assert.greater(_proxyPort, 0, "Simple downloader config : proxyPort <=0.");
        }

        this.corePoolSize = _corePoolSize;
        this.keepAliveTime = _keepAliveTime;
        this.maxPoolSize = _maxPoolSize;
        this.workDirectory = _workDirectory;
        this.timeService = _timeService;
        this.cacheStorageTime = _cacheStorageTime;

        this.bannedListTreshold = _bannedListTreshold;
        this.bannedListLimit = _bannedListLimit;

        this.maxConnectionsPerHost = _maxConnectionsPerHost;
        this.maxTotalConnections = _maxTotalConnections;

        this.userAgent = _userAgent;

        this.useProxy = _useProxy;
        this.proxyHost = _proxyHost;
        this.proxyPort = _proxyPort;
        this.proxyUserName = _proxyUserName;
        this.proxyUserPassword = _proxyUserPassword;

        this.tryCount = _tryCount;
        this.socketTimeout = _socketTimeout;
        this.errorTimeout = _errorTimeout;
        this.minTimeout = _minTimeout;
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

    public String getWorkDirectory() {
        return this.workDirectory;
    }

    public long getCacheStorageTime() {
        return this.cacheStorageTime;
    }

    public TimeService getTimeService() {
        return this.timeService;
    }

    public boolean isProxyUsing() {
        return this.useProxy;
    }

    public int getBannedListTreshold() {
        return this.bannedListTreshold;
    }

    public int getBannedListLimit() {
        return this.bannedListLimit;
    }

    public int getMaxConnectionsPerHost() {
        return this.maxConnectionsPerHost;
    }

    public int getMaxTotalConnections() {
        return this.maxTotalConnections;
    }

    public String getUserAgent() {
        return this.userAgent;
    }

    public String getProxyHost() {
        return this.proxyHost;
    }

    public int getProxyPort() {
        return this.proxyPort;
    }

    public String getUserName() {
        return this.proxyUserName;
    }

    public String getUserPassword() {
        return this.proxyUserPassword;
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

    public int getMinTimeout() {
        return this.minTimeout;
    }
}