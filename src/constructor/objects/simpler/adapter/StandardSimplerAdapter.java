package constructor.objects.simpler.adapter;

import app.VersionInfo;
import app.controller.Controller;
import cloud.PropertiesCloud;
import constructor.objects.channel.core.ChannelDataListStorage;
import constructor.objects.output.configuration.DocumentItemsSortMode;
import constructor.objects.simpler.configuration.SimplerConfiguration;
import constructor.objects.simpler.core.SimplerAdapter;
import constructor.objects.source.core.ModificationListStorage;
import constructor.objects.storage.Storage;
import converter.format.fb2.resource.Fb2ResourceConversionContext;
import converter.format.fb2.resource.resolver.cache.ResourceCache;
import debug.DebugConsole;
import http.BatchLoader;
import resource.ConverterFactory;
import timeservice.TimeService;
import util.Assert;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 06.07.2010
 */
public class StandardSimplerAdapter implements SimplerAdapter {

    private final SimplerConfiguration configuration;
    private final TimeService timeService;
    private final BatchLoader batchLoader;
    private final PropertiesCloud propertiesCloud;
    private final Controller controller;
    private final ConverterFactory converterFactory;
    private final ResourceCache resourceCache;
    private final ModificationListStorage modificationListStorage;
    private final ChannelDataListStorage channelDataListStorage;
    private final List<Storage> storages;
    private final boolean fromNewToOld;
    private final int precachedItemsCount;
    private final int forcedDays;
    private final int tryCount;
    private final int timeout;
    private final int minTimeout;
    private final int maxFileNameLength;
    private final String tempDir;
    private final String resourceDummy;
    private final Fb2ResourceConversionContext resourceConversionContext;
    private final VersionInfo versionInfo;
    private final DebugConsole debugConsole;

    public StandardSimplerAdapter(final SimplerConfiguration _configuration,
                                  final TimeService _timeService,
                                  final BatchLoader _batchLoader,
                                  final PropertiesCloud _propertiesCloud,
                                  final Controller _controller,
                                  final ConverterFactory _converterFactory,
                                  final ResourceCache _resourceCache,
                                  final ModificationListStorage _modificationListStorage,
                                  final ChannelDataListStorage _channelDataListStorage,
                                  final List<Storage> _storages,
                                  final boolean _fromNewToOld,
                                  final int _precachedItemsCount,
                                  final int _forcedDays,
                                  final int _tryCount,
                                  final int _timeout,
                                  final int _minTimeout,
                                  final int _maxFileNameLength,
                                  final String _tempDir,
                                  final String _resourceDummy,
                                  final Fb2ResourceConversionContext _resourceConversionContext,
                                  final VersionInfo _versionInfo,
                                  final DebugConsole _debugConsole) {
        Assert.notNull(_configuration, "Simpler configuration is null");
        this.configuration = _configuration;

        Assert.notNull(_timeService, "Time service is null");
        this.timeService = _timeService;

        Assert.notNull(_batchLoader, "Batch loader is null");
        this.batchLoader = _batchLoader;

        Assert.notNull(_propertiesCloud, "Properties cloud is null");
        this.propertiesCloud = _propertiesCloud;

        Assert.notNull(_controller, "Controller is null");
        this.controller = _controller;

        Assert.notNull(_converterFactory, "Converter factory is null");
        this.converterFactory = _converterFactory;

        Assert.notNull(_resourceCache, "Resource cache is null");
        this.resourceCache = _resourceCache;

        Assert.notNull(_modificationListStorage, "Modification list storage is null");
        this.modificationListStorage = _modificationListStorage;

        Assert.notNull(_channelDataListStorage, "Channel data list storage is null");
        this.channelDataListStorage = _channelDataListStorage;

        Assert.notNull(_storages, "Storages is null");
        this.storages = _storages;

        this.fromNewToOld = _fromNewToOld;

        Assert.greaterOrEqual(_precachedItemsCount, 0, "Precached items count < 0");
        this.precachedItemsCount = _precachedItemsCount;

        this.forcedDays = _forcedDays;

        Assert.greater(_tryCount, 0, "Try count <= 0");
        this.tryCount = _tryCount;

        Assert.greater(_timeout, 0, "Timeout <= 0");
        this.timeout = _timeout;

        Assert.greater(_minTimeout, 0, "Minimum timeout <= 0");
        this.minTimeout = _minTimeout;

        Assert.greater(_maxFileNameLength, 0, "Max filename length <= 0");
        this.maxFileNameLength = _maxFileNameLength;

        Assert.isValidString(_tempDir, "Temp directory path is not valid");
        this.tempDir = _tempDir;

        Assert.isValidString(_resourceDummy, "Resource dummy path is not valid");
        this.resourceDummy = _resourceDummy;

        Assert.notNull(_resourceConversionContext, "Resource conversion context is null");
        this.resourceConversionContext = _resourceConversionContext;

        Assert.notNull(_versionInfo, "Version info is null");
        this.versionInfo = _versionInfo;

        Assert.notNull(_debugConsole, "Debug console is null");
        this.debugConsole = _debugConsole;
    }

    public String getId() {
        return this.configuration.getId();
    }

    public String getFeedUrl() {
        return this.configuration.getFeedUrl();
    }

    public int getStoreDays() {
        return parse(this.configuration.getStoreDays());
    }

    public TimeService getTimeService() {
        return this.timeService;
    }

    public PropertiesCloud getPropertiesCloud() {
        return this.propertiesCloud;
    }

    public Controller getController() {
        return this.controller;
    }

    public ModificationListStorage getModificationListStorage() {
        return this.modificationListStorage;
    }

    public int getTryCount() {
        return this.tryCount;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public int getMinTimeout() {
        return this.minTimeout;
    }

    public String getCoverUrl() {
        return this.configuration.getCoverUrl();
    }

    public ChannelDataListStorage getChannelDataListStorage() {
        return this.channelDataListStorage;
    }

    public BatchLoader getBatchLoader() {
        return this.batchLoader;
    }

    public int getForcedDays() {
        return this.forcedDays;
    }

    public int getPrecachedItemsCount() {
        return this.precachedItemsCount;
    }

    public long getPauseBetweenRequests() {
        return this.configuration.getPauseBetweenRequests();
    }

    public String getDocumentName() {
        return this.configuration.getOutName();
    }

    public boolean isFromNewToOld() {
        return this.configuration.getFromNewToOld() == DocumentItemsSortMode.DEFAULT ? this.fromNewToOld : this.configuration.getFromNewToOld() == DocumentItemsSortMode.FROM_NEW_TO_OLD;
    }

    public int getMaxFileNameLength() {
        return this.maxFileNameLength;
    }

    public String getBranch() {
        return this.configuration.getBranch();
    }

    public String getTempDir() {
        return this.tempDir;
    }

    public boolean isLinksAsFootnotes() {
        return false;
    }

    public boolean removeExists() {
        return true;
    }

    public boolean isResolveImageLinks() {
        return true;
    }

    public ConverterFactory getConverterFactory() {
        return this.converterFactory;
    }

    public ResourceCache getResourceCache() {
        return this.resourceCache;
    }

    public String getDummy() {
        return this.resourceDummy;
    }

    public Fb2ResourceConversionContext getConversionContext() {
        return this.resourceConversionContext;
    }

    public List<Storage> getStorages() {
        return this.storages;
    }

    public VersionInfo getVersionInfo() {
        return this.versionInfo;
    }

    public String getCriterions() {
        return this.configuration.getCriterions();
    }

    public boolean isAutoContentFiltering() {
        return this.configuration.isAutoContentFiltering();
    }

    public DebugConsole getDebugConsole() {
        return this.debugConsole;
    }

    private int parse(final String _value) {
        int result = 0;

        try {
            result = Integer.valueOf(_value);
        } catch (Throwable t) {
            // ignore
        }

        return result;
    }
}
