package http;

import util.Assert;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 16.11.2011
 */
public class StandardHttpRequestHandlerContext {

    private final int connectionTimeout;
    private final int socketTimeout;

    private final int retryCount;

    private final int bannedListTreshold;
    private final int bannedListLimit;

    private final String useragent;

    private final boolean proxyUse;
    private final String proxyHost;
    private final int proxyPort;
    private final String proxyUser;
    private final String proxyPassword;

    public StandardHttpRequestHandlerContext(final int _connectionTimeout,
                                             final int _socketTimeout,
                                             final int _retryCount,
                                             final int _bannedListTreshold,
                                             final int _bannedListLimit,
                                             final String _useragent,
                                             final boolean _proxyUse,
                                             final String _proxyHost,
                                             final int _proxyPort,
                                             final String _proxyUser,
                                             final String _proxyPassword) {
        Assert.greater(_connectionTimeout, 0, "Connection timeout < 0");
        this.connectionTimeout = _connectionTimeout;

        Assert.greater(_socketTimeout, 0, "Socket timeout < 0");
        this.socketTimeout = _socketTimeout;

        Assert.greaterOrEqual(_retryCount, 1, "Retry count < 1");
        this.retryCount = _retryCount;

        Assert.greaterOrEqual(_bannedListTreshold, 1, "Banned list treshold count < 1");
        this.bannedListTreshold = _bannedListTreshold;

        Assert.greaterOrEqual(_bannedListLimit, 1, "Banned list limit count < 1");
        this.bannedListLimit = _bannedListLimit;

        Assert.notNull(_useragent, "Useragent is null");
        this.useragent = _useragent;

        this.proxyUse = _proxyUse;

        Assert.notNull(_proxyHost, "Proxy host is null");
        this.proxyHost = _proxyHost;

        Assert.greaterOrEqual(_proxyPort, 0, "Proxy port < 0");
        this.proxyPort = _proxyPort;

        Assert.notNull(_proxyUser, "Proxy user is null");
        this.proxyUser = _proxyUser;

        Assert.notNull(_proxyPassword, "Proxy password is null");
        this.proxyPassword = _proxyPassword;
    }

    public int getConnectionTimeout() {
        return this.connectionTimeout;
    }

    public int getSocketTimeout() {
        return this.socketTimeout;
    }

    public int getRetryCount() {
        return this.retryCount;
    }

    public int getBannedListTreshold() {
        return this.bannedListTreshold;
    }

    public int getBannedListLimit() {
        return this.bannedListLimit;
    }

    public String getUseragent() {
        return this.useragent;
    }

    public boolean isProxyUse() {
        return this.proxyUse;
    }

    public String getProxyHost() {
        return this.proxyHost;
    }

    public int getProxyPort() {
        return this.proxyPort;
    }

    public String getProxyUser() {
        return this.proxyUser;
    }

    public String getProxyPassword() {
        return this.proxyPassword;
    }

}
