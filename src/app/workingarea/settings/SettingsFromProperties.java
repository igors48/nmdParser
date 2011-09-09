package app.workingarea.settings;

import app.workingarea.Settings;
import app.workingarea.defaults.FileDefaults;
import converter.format.fb2.resource.Fb2ResourceConversionContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;
import util.IOTools;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Установки хранящиеся в пропертях
 *
 * @author Igor Usenko
 *         Date: 16.04.2009
 */
public class SettingsFromProperties implements Settings {

    private static final String TRUE_LITERAL = "true";
    private static final String FALSE_LITERAL = "false";
    private static final String EQUALS_LITERAL = " = ";
    private static final long DAY_TO_MILLIS = 24 * 60 * 60 * 1000;

    private static final String PRECACHED_ITEMS_COUNT_KEY = "precached.items.count";
    private static final String PRECACHED_ITEMS_COUNT_DEFAULT = "4";

    private static final String PROCESS_MANAGER_MAX_FAIL_COUNT_KEY = "process.manager.max.fail.count";
    private static final String PROCESS_MANAGER_MAX_FAIL_COUNT_DEFAULT = "3";

    private static final String RESOURCE_CACHE_ENABLED_KEY = "resource.cache.enabled";
    private static final String RESOURCE_CACHE_ENABLED_DEFAULT = TRUE_LITERAL;
    private static final String RESOURCE_CACHE_ROOT_KEY = "resource.cache.root";
    private static final String RESOURCE_CACHE_ROOT_DEFAULT = "./work/resources/";
    private static final String RESOURCE_CACHE_MAX_ITEM_AGE_KEY = "resource.cache.max.item.age.day";
    private static final String RESOURCE_CACHE_MAX_ITEM_AGE_DEFAULT = "7";
    private static final String RESOURCE_CACHE_MAX_ITEM_SIZE_KEY = "resource.cache.max.item.size";
    private static final String RESOURCE_CACHE_MAX_ITEM_SIZE_DEFAULT = "5000000";
    private static final String RESOURCE_CACHE_MAX_SIZE_KEY = "resource.cache.max.size";
    private static final String RESOURCE_CACHE_MAX_SIZE_DEFAULT = "500000000";

    private static final String API_TEMP_DIRECTORY_KEY = "api.temp.directory";
    private static final String API_TEMP_DIRECTORY_DEFAULT = "./work/cache/";
    private static final String API_RESOURCE_DUMMY_KEY = "api.resource.dummy";
    private static final String API_RESOURCE_DUMMY_DEFAULT = "./etc/notSupFmt.jpg";
    private static final String API_DEBUG_DIRECTORY_KEY = "api.debug.directory";
    private static final String API_DEBUG_DIRECTORY_DEFAULT = "./work/debug/";
    private static final String API_MAX_FILE_NAME_LENGTH_KEY = "api.max.file.name.length";
    private static final String API_MAX_FILE_NAME_LENGTH_DEFAULT = "20";

    private static final String PROXY_USE_KEY = "proxy.use";
    private static final String PROXY_USE_DEFAULT = FALSE_LITERAL;
    private static final String PROXY_HOST_KEY = "proxy.host";
    private static final String PROXY_HOST_DEFAULT = "";
    private static final String PROXY_PORT_KEY = "proxy.port";
    private static final String PROXY_PORT_DEFAULT = "0";
    private static final String PROXY_USER_KEY = "proxy.user";
    private static final String PROXY_USER_DEFAULT = "";
    private static final String PROXY_PASSWORD_KEY = "proxy.password";
    private static final String PROXY_PASSWORD_DEFAULT = "";

    private static final String DOWNLOADER_BANNED_LIST_TRESHOLD_KEY = "downloader.banned.list.treshold";
    private static final String DOWNLOADER_BANNED_LIST_TRESHOLD_DEFAULT = "5";
    private static final String DOWNLOADER_BANNED_LIST_LIMIT_KEY = "downloader.banned.list.limit";
    private static final String DOWNLOADER_BANNED_LIST_LIMIT_DEFAULT = "500";
    private static final String DOWNLOADER_MAX_CONNECTION_PER_HOST_KEY = "downloader.max.connections.per.host";
    private static final String DOWNLOADER_MAX_CONNECTION_PER_HOST_DEFAULT = "6";
    private static final String DOWNLOADER_MAX_TOTAL_CONNECTIONS_KEY = "downloader.max.total.connections";
    private static final String DOWNLOADER_MAX_TOTAL_CONNECTIONS_DEFAULT = "64";
    private static final String DOWNLOADER_USER_AGENT_KEY = "downloader.user.agent";
    private static final String DOWNLOADER_USER_AGENT_DEFAULT = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)";
    private static final String DOWNLOADER_CORE_POOL_SIZE_KEY = "downloader.core.pool.size";
    private static final String DOWNLOADER_CORE_POOL_SIZE_DEFAULT = "10";
    private static final String DOWNLOADER_MAX_POOL_SIZE_KEY = "downloader.max.pool.size";
    private static final String DOWNLOADER_MAX_POOL_SIZE_DEFAULT = "10";
    private static final String DOWNLOADER_KEEP_ALIVE_TIME_KEY = "downloader.keep.alive.time";
    private static final String DOWNLOADER_KEEP_ALIVE_TIME_DEFAULT = "10";
    private static final String DOWNLOADER_CACHE_STORAGE_TIME_KEY = "downloader.cache.storage.time";
    private static final String DOWNLOADER_CACHE_STORAGE_TIME_DEFAULT = "1800000";
    private static final String DOWNLOADER_MAX_TRY_COUNT_KEY = "downloader.max.try.count";
    private static final String DOWNLOADER_MAX_TRY_COUNT_DEFAULT = "5";
    private static final String DOWNLOADER_SOCKET_TIMEOUT_KEY = "downloader.socket.timeout";
    private static final String DOWNLOADER_SOCKET_TIMEOUT_DEFAULT = "30000";
    private static final String DOWNLOADER_ERROR_TIMEOUT_KEY = "downloader.error.timeout";
    private static final String DOWNLOADER_ERROR_TIMEOUT_DEFAULT = "60000";
    private static final String DOWNLOADER_MIN_TIMEOUT_KEY = "downloader.min.timeout";
    private static final String DOWNLOADER_MIN_TIMEOUT_DEFAULT = "15000";

    private static final String IMAGE_MAXIMUM_WIDTH_KEY = "image.maximum.width";
    private static final String IMAGE_MAXIMUM_WIDTH_DEFAULT = "670";
    private static final String IMAGE_MAXIMUM_HEIGHT_KEY = "image.maximum.height";
    private static final String IMAGE_MAXIMUM_HEIGHT_DEFAULT = "470";
    private static final String IMAGE_GRAYSCALE_KEY = "image.grayscale";
    private static final String IMAGE_GRAYSCALE_DEFAULT = "true";

    private static final String DOCUMENT_SORT_FROM_NEW_TO_OLD_KEY = "document.sort.from.new.to.old";
    private static final String DOCUMENT_SORT_FROM_NEW_TO_OLD_DEFAULT = "false";

    private final Properties properties;

    private final String defaultStorageRoot;
    private final long defaultStoragePeriod;
    private final String googleReaderRoot; 

    private String tempDirectory;
    private String resourceDummy;
    private String debugDirectory;
    private int maxFileNameLength;
    private boolean useProxy;
    private String proxyHost;
    private int proxyPort;
    private String userName;
    private String userPassword;
    private int bannedListTreshold;
    private int bannedListLimit;
    private int maxConnectionsPerHost;
    private int maxTotalConnections;
    private String userAgent;
    private int corePoolSize;
    private int maxPoolSize;
    private int keepAliveTime;
    private long cacheStorageTime;
    private int maxTryCount;
    private int socketTimeOut;
    private int errorTimeOut;
    private int minTimeOut;
    private boolean resourceCacheEnabled;
    private String resourceCacheRoot;
    private long resourceCacheItemMaxAge;
    private long resourceCacheItemMaxSize;
    private long resourceCacheMaxSize;
    private int processManagerMaxFailCount;
    private int imageMaximumWidth;
    private int imageMaximumHeight;
    private boolean imageGrayscale;
    private boolean fromNewToOld;
    private int precachedItemsCount;

    private byte[] dummy;

    private final Log log;

    public SettingsFromProperties(final Properties _properties, final String _defaultStorageRoot, final long _defaultStoragePeriod, final String _googleReaderRoot) throws SettingsException {
        Assert.notNull(_properties, "Properties is null.");
        this.properties = _properties;

        Assert.isValidString(_defaultStorageRoot, "Default storage root is not valid");
        this.defaultStorageRoot = _defaultStorageRoot;

        Assert.greaterOrEqual(_defaultStoragePeriod, 0, "Default storage period < 0");
        this.defaultStoragePeriod = _defaultStoragePeriod;

        Assert.isValidString(_googleReaderRoot, "Google Reader root is not valid");
        this.googleReaderRoot = _googleReaderRoot;

        this.log = LogFactory.getLog(getClass());

        mapProperties();
        logProperties();

        this.dummy = null;
    }

    public String getTempDirectory() {
        return this.tempDirectory;
    }

    public String getResourceDummy() {
        return this.resourceDummy;
    }

    public byte[] getResourceDummyAsBytes() {

        if (this.dummy == null) {
            this.dummy = readDummy();
        }

        return this.dummy;
    }

    private byte[] readDummy() {
        byte[] result = new byte[0];

        try {
            result = IOTools.readFile(new File(this.resourceDummy));
        } catch (IOException e) {
            this.log.error("Error reading resource dummy data", e);
        }

        return result;
    }

    public String getDebugDirectory() {
        return this.debugDirectory;
    }

    public int getUpdateAttempts() {
        return 3;
    }

    public long getUpdateTimeout() {
        return 3000;
    }

    public boolean isProxyUsed() {
        return this.useProxy;
    }

    public String getProxyHost() {
        return this.proxyHost;
    }

    public int getProxyPort() {
        return this.proxyPort;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getUserPassword() {
        return this.userPassword;
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

    public int getCorePoolSize() {
        return this.corePoolSize;
    }

    public int getMaxPoolSize() {
        return this.maxPoolSize;
    }

    public int getKeepAliveTime() {
        return this.keepAliveTime;
    }

    public long getCacheStorageTime() {
        return this.cacheStorageTime;
    }

    public int getMaxTryCount() {
        return this.maxTryCount;
    }

    public int getSocketTimeout() {
        return this.socketTimeOut;
    }

    public int getErrorTimeout() {
        return this.errorTimeOut;
    }

    public int getMinTimeout() {
        return this.minTimeOut;
    }

    public int getMaxFileNameLength() {
        return this.maxFileNameLength;
    }

    public boolean isResourceCacheEnabled() {
        return this.resourceCacheEnabled;
    }

    public String getResourceCacheRoot() {
        return this.resourceCacheRoot;
    }

    public long getResourceItemMaxAge() {
        return this.resourceCacheItemMaxAge;
    }

    public long getResourceItemMaxSize() {
        return this.resourceCacheItemMaxSize;
    }

    public long getResourceCacheMaxSize() {
        return this.resourceCacheMaxSize;
    }

    public int getExternalProcessMaxFailCount() {
        return this.processManagerMaxFailCount;
    }

    public String getDefaultStorageRoot() {
        return this.defaultStorageRoot;
    }

    public long getDefaultStoragePeriod() {
        return this.defaultStoragePeriod;
    }

    public int getResourceImageMaximumWidth() {
        return this.imageMaximumWidth;
    }

    public int getResourceImageMaximumHeight() {
        return this.imageMaximumHeight;
    }

    public boolean isResourceImageGrayscale() {
        return this.imageGrayscale;
    }

    public boolean isFromNewToOld() {
        return this.fromNewToOld;
    }

    public int getPrecachedItemsCount() {
        return this.precachedItemsCount;
    }

    public Fb2ResourceConversionContext getResourceConversionContext() {

        return new Fb2ResourceConversionContext(
                this.imageMaximumWidth,
                this.imageMaximumHeight,
                this.imageGrayscale,
                this.getResourceDummyAsBytes(),
                0.7,
                true,
                true,
                5);
    }

    public String getGoogleReaderRoot() {
        return this.googleReaderRoot;
    }

    public void valid() throws SettingsException {
        // not implemented yet
    }

    private void mapProperties() throws SettingsException {

        try {
            this.tempDirectory = this.properties.getProperty(API_TEMP_DIRECTORY_KEY, API_TEMP_DIRECTORY_DEFAULT);
            this.resourceDummy = this.properties.getProperty(API_RESOURCE_DUMMY_KEY, API_RESOURCE_DUMMY_DEFAULT);
            this.debugDirectory = this.properties.getProperty(API_DEBUG_DIRECTORY_KEY, API_DEBUG_DIRECTORY_DEFAULT);
            this.maxFileNameLength = Integer.valueOf(this.properties.getProperty(API_MAX_FILE_NAME_LENGTH_KEY, API_MAX_FILE_NAME_LENGTH_DEFAULT));

            this.useProxy = TRUE_LITERAL.equalsIgnoreCase(this.properties.getProperty(PROXY_USE_KEY, PROXY_USE_DEFAULT));
            this.proxyHost = this.properties.getProperty(PROXY_HOST_KEY, PROXY_HOST_DEFAULT);
            this.proxyPort = Integer.valueOf(this.properties.getProperty(PROXY_PORT_KEY, PROXY_PORT_DEFAULT));
            this.userName = this.properties.getProperty(PROXY_USER_KEY, PROXY_USER_DEFAULT);
            this.userPassword = this.properties.getProperty(PROXY_PASSWORD_KEY, PROXY_PASSWORD_DEFAULT);

            this.bannedListTreshold = Integer.valueOf(this.properties.getProperty(DOWNLOADER_BANNED_LIST_TRESHOLD_KEY, DOWNLOADER_BANNED_LIST_TRESHOLD_DEFAULT));
            this.bannedListLimit = Integer.valueOf(this.properties.getProperty(DOWNLOADER_BANNED_LIST_LIMIT_KEY, DOWNLOADER_BANNED_LIST_LIMIT_DEFAULT));
            this.maxConnectionsPerHost = Integer.valueOf(this.properties.getProperty(DOWNLOADER_MAX_CONNECTION_PER_HOST_KEY, DOWNLOADER_MAX_CONNECTION_PER_HOST_DEFAULT));
            this.maxTotalConnections = Integer.valueOf(this.properties.getProperty(DOWNLOADER_MAX_TOTAL_CONNECTIONS_KEY, DOWNLOADER_MAX_TOTAL_CONNECTIONS_DEFAULT));
            this.userAgent = this.properties.getProperty(DOWNLOADER_USER_AGENT_KEY, DOWNLOADER_USER_AGENT_DEFAULT);
            this.corePoolSize = Integer.valueOf(this.properties.getProperty(DOWNLOADER_CORE_POOL_SIZE_KEY, DOWNLOADER_CORE_POOL_SIZE_DEFAULT));
            this.maxPoolSize = Integer.valueOf(this.properties.getProperty(DOWNLOADER_MAX_POOL_SIZE_KEY, DOWNLOADER_MAX_POOL_SIZE_DEFAULT));
            this.keepAliveTime = Integer.valueOf(this.properties.getProperty(DOWNLOADER_KEEP_ALIVE_TIME_KEY, DOWNLOADER_KEEP_ALIVE_TIME_DEFAULT));
            this.cacheStorageTime = Long.valueOf(this.properties.getProperty(DOWNLOADER_CACHE_STORAGE_TIME_KEY, DOWNLOADER_CACHE_STORAGE_TIME_DEFAULT));
            this.maxTryCount = Integer.valueOf(this.properties.getProperty(DOWNLOADER_MAX_TRY_COUNT_KEY, DOWNLOADER_MAX_TRY_COUNT_DEFAULT));
            this.socketTimeOut = Integer.valueOf(this.properties.getProperty(DOWNLOADER_SOCKET_TIMEOUT_KEY, DOWNLOADER_SOCKET_TIMEOUT_DEFAULT));
            this.errorTimeOut = Integer.valueOf(this.properties.getProperty(DOWNLOADER_ERROR_TIMEOUT_KEY, DOWNLOADER_ERROR_TIMEOUT_DEFAULT));
            this.minTimeOut = Integer.valueOf(this.properties.getProperty(DOWNLOADER_MIN_TIMEOUT_KEY, DOWNLOADER_MIN_TIMEOUT_DEFAULT));

            this.resourceCacheEnabled = TRUE_LITERAL.equalsIgnoreCase(this.properties.getProperty(RESOURCE_CACHE_ENABLED_KEY, RESOURCE_CACHE_ENABLED_DEFAULT));
            this.resourceCacheRoot = this.properties.getProperty(RESOURCE_CACHE_ROOT_KEY, RESOURCE_CACHE_ROOT_DEFAULT);
            this.resourceCacheItemMaxAge = Long.valueOf(this.properties.getProperty(RESOURCE_CACHE_MAX_ITEM_AGE_KEY, RESOURCE_CACHE_MAX_ITEM_AGE_DEFAULT)) * DAY_TO_MILLIS;
            this.resourceCacheItemMaxSize = Long.valueOf(this.properties.getProperty(RESOURCE_CACHE_MAX_ITEM_SIZE_KEY, RESOURCE_CACHE_MAX_ITEM_SIZE_DEFAULT));
            this.resourceCacheMaxSize = Long.valueOf(this.properties.getProperty(RESOURCE_CACHE_MAX_SIZE_KEY, RESOURCE_CACHE_MAX_SIZE_DEFAULT));

            this.processManagerMaxFailCount = Integer.valueOf(this.properties.getProperty(PROCESS_MANAGER_MAX_FAIL_COUNT_KEY, PROCESS_MANAGER_MAX_FAIL_COUNT_DEFAULT));

            this.imageMaximumWidth = Integer.valueOf(this.properties.getProperty(IMAGE_MAXIMUM_WIDTH_KEY, IMAGE_MAXIMUM_WIDTH_DEFAULT));
            this.imageMaximumHeight = Integer.valueOf(this.properties.getProperty(IMAGE_MAXIMUM_HEIGHT_KEY, IMAGE_MAXIMUM_HEIGHT_DEFAULT));
            this.imageGrayscale = TRUE_LITERAL.equalsIgnoreCase(this.properties.getProperty(IMAGE_GRAYSCALE_KEY, IMAGE_GRAYSCALE_DEFAULT));

            this.fromNewToOld = TRUE_LITERAL.equalsIgnoreCase(this.properties.getProperty(DOCUMENT_SORT_FROM_NEW_TO_OLD_KEY, DOCUMENT_SORT_FROM_NEW_TO_OLD_DEFAULT));

            this.precachedItemsCount = Integer.valueOf(this.properties.getProperty(PRECACHED_ITEMS_COUNT_KEY, PRECACHED_ITEMS_COUNT_DEFAULT));
        } catch (Exception e) {
            throw new SettingsException(e);
        }
    }

    private void logProperties() {

        this.log.debug(API_TEMP_DIRECTORY_KEY + EQUALS_LITERAL + this.tempDirectory);
        this.log.debug(API_RESOURCE_DUMMY_KEY + EQUALS_LITERAL + this.resourceDummy);
        this.log.debug(API_DEBUG_DIRECTORY_KEY + EQUALS_LITERAL + this.debugDirectory);
        this.log.debug(API_MAX_FILE_NAME_LENGTH_KEY + EQUALS_LITERAL + this.maxFileNameLength);

        this.log.debug(PROXY_USE_KEY + EQUALS_LITERAL + this.useProxy);
        this.log.debug(PROXY_HOST_KEY + EQUALS_LITERAL + this.proxyHost);
        this.log.debug(PROXY_PORT_KEY + EQUALS_LITERAL + this.proxyPort);
        this.log.debug(PROXY_USER_KEY + EQUALS_LITERAL + this.userName);
        this.log.debug(PROXY_PASSWORD_KEY + EQUALS_LITERAL + this.userPassword);

        this.log.debug(DOWNLOADER_BANNED_LIST_TRESHOLD_KEY + EQUALS_LITERAL + this.bannedListTreshold);
        this.log.debug(DOWNLOADER_BANNED_LIST_LIMIT_KEY + EQUALS_LITERAL + this.bannedListLimit);
        this.log.debug(DOWNLOADER_MAX_CONNECTION_PER_HOST_KEY + EQUALS_LITERAL + this.maxConnectionsPerHost);
        this.log.debug(DOWNLOADER_MAX_TOTAL_CONNECTIONS_KEY + EQUALS_LITERAL + this.maxTotalConnections);
        this.log.debug(DOWNLOADER_USER_AGENT_KEY + EQUALS_LITERAL + this.userAgent);
        this.log.debug(DOWNLOADER_CORE_POOL_SIZE_KEY + EQUALS_LITERAL + this.corePoolSize);
        this.log.debug(DOWNLOADER_MAX_POOL_SIZE_KEY + EQUALS_LITERAL + this.maxPoolSize);
        this.log.debug(DOWNLOADER_KEEP_ALIVE_TIME_KEY + EQUALS_LITERAL + this.keepAliveTime);
        this.log.debug(DOWNLOADER_CACHE_STORAGE_TIME_KEY + EQUALS_LITERAL + this.cacheStorageTime);
        this.log.debug(DOWNLOADER_MAX_TRY_COUNT_KEY + EQUALS_LITERAL + this.maxTryCount);
        this.log.debug(DOWNLOADER_SOCKET_TIMEOUT_KEY + EQUALS_LITERAL + this.socketTimeOut);
        this.log.debug(DOWNLOADER_ERROR_TIMEOUT_KEY + EQUALS_LITERAL + this.errorTimeOut);
        this.log.debug(DOWNLOADER_MIN_TIMEOUT_KEY + EQUALS_LITERAL + this.minTimeOut);

        this.log.debug(RESOURCE_CACHE_ENABLED_KEY + EQUALS_LITERAL + this.resourceCacheEnabled);
        this.log.debug(RESOURCE_CACHE_ROOT_KEY + EQUALS_LITERAL + this.resourceCacheRoot);
        this.log.debug(RESOURCE_CACHE_MAX_ITEM_AGE_KEY + EQUALS_LITERAL + this.resourceCacheItemMaxAge);
        this.log.debug(RESOURCE_CACHE_MAX_ITEM_SIZE_KEY + EQUALS_LITERAL + this.resourceCacheItemMaxSize);
        this.log.debug(RESOURCE_CACHE_MAX_SIZE_KEY + EQUALS_LITERAL + this.resourceCacheMaxSize);

        this.log.debug(PROCESS_MANAGER_MAX_FAIL_COUNT_KEY + EQUALS_LITERAL + this.processManagerMaxFailCount);

        this.log.debug(FileDefaults.DEFAULT_STORAGE_ROOT_KEY + EQUALS_LITERAL + this.defaultStorageRoot);
        this.log.debug(FileDefaults.DEFAULT_STORAGE_PERIOD_KEY + EQUALS_LITERAL + this.defaultStoragePeriod);

        this.log.debug(IMAGE_MAXIMUM_WIDTH_KEY + EQUALS_LITERAL + this.imageMaximumWidth);
        this.log.debug(IMAGE_MAXIMUM_HEIGHT_KEY + EQUALS_LITERAL + this.imageMaximumHeight);
        this.log.debug(IMAGE_GRAYSCALE_KEY + EQUALS_LITERAL + this.imageGrayscale);

        this.log.debug(DOCUMENT_SORT_FROM_NEW_TO_OLD_KEY + EQUALS_LITERAL + this.fromNewToOld);

        this.log.debug(PRECACHED_ITEMS_COUNT_KEY + EQUALS_LITERAL + this.precachedItemsCount);
    }
}
