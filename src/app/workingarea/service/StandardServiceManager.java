package app.workingarea.service;

import app.workingarea.ProcessWrapper;
import app.workingarea.ServiceManager;
import app.workingarea.Settings;
import app.workingarea.process.ExternalProcessExecutor;
import app.workingarea.process.ExternalProcessManager;
import constructor.dom.Preprocessor;
import constructor.dom.preprocessor.NullPreprocessor;
import constructor.dom.preprocessor.PlaceholderPreprocessor;
import constructor.objects.source.core.FetcherFactory;
import constructor.objects.source.core.fetcher.StandardFetcherFactory;
import constructor.objects.storage.Storage;
import constructor.objects.storage.local.core.LocalHandler;
import constructor.objects.storage.local.core.LocalStorage;
import converter.format.fb2.resource.resolver.cache.*;
import debug.DebugConsole;
import debug.console.FileDebugConsole;
import debug.console.FileDebugConsoleUpdater;
import debug.console.NullDebugConsole;
import downloader.Downloader;
import downloader.StandardDownloader;
import downloader.StandardDownloaderConfig;
import greader.GoogleReaderAdapter;
import greader.GoogleReaderProvider;
import greader.StandardGoogleReaderAdapter;
import greader.profile.ProfilesStorage;
import resource.ConverterFactory;
import resource.ResourceConverterFactory;
import timeservice.StandardTimeService;
import timeservice.TimeService;
import util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Стандартный менеджер системных сервисов
 *
 * @author Igor Usenko
 *         Date: 20.04.2009
 */
public class StandardServiceManager implements ServiceManager {

    private final Settings settings;
    private final boolean debugConsoleEnabled;

    private Map<String, String> context;

    private TimeService timeService;
    private FetcherFactory fetcherFactory;
    private Downloader downloader;
    private DebugConsole debugConsole;
    private ResourceCache resourceCache;
    private ProcessWrapper processWrapper;
    private LocalStorage defaultStorage;

    private ProfilesStorage profilesStorage;
    private GoogleReaderProvider googleReaderProvider;
    private GoogleReaderAdapter googleReaderAdapter;

    private boolean reflectionMode;

    public StandardServiceManager(final Settings _settings, final boolean _debugConsoleEnabled) {
        Assert.notNull(_settings, "Settings is null.");
        this.settings = _settings;

        this.debugConsoleEnabled = _debugConsoleEnabled;

        this.context = new HashMap<String, String>();

        deactivateReflectionMode();
    }

    public void setExternalContext(final Map<String, String> _context) {
        Assert.notNull(_context, "External context is null");
        this.context = _context;
    }

    public TimeService getTimeService() throws ServiceManagerException {

        if (this.timeService == null) {
            this.timeService = new StandardTimeService();
        }

        return this.timeService;
    }

    public FetcherFactory getFetcherFactory() throws ServiceManagerException {

        if (this.fetcherFactory == null) {
            this.fetcherFactory = new StandardFetcherFactory();
        }

        return this.fetcherFactory;
    }

    public Downloader getDownloader() throws ServiceManagerException {

        try {

            if (this.downloader == null) {

                StandardDownloaderConfig config = new StandardDownloaderConfig(this.settings.getCorePoolSize(),
                        this.settings.getMaxPoolSize(),
                        this.settings.getKeepAliveTime(),
                        this.settings.getTempDirectory(),
                        getTimeService(),
                        this.settings.getCacheStorageTime(),
                        this.settings.getBannedListTreshold(),
                        this.settings.getBannedListLimit(),
                        this.settings.getMaxConnectionsPerHost(),
                        this.settings.getMaxTotalConnections(),
                        this.settings.getUserAgent(),
                        this.settings.isProxyUsed(),
                        this.settings.getProxyHost(),
                        this.settings.getProxyPort(),
                        this.settings.getUserName(),
                        this.settings.getUserPassword(),
                        this.settings.getMaxTryCount(),
                        this.settings.getSocketTimeout(),
                        this.settings.getErrorTimeout(),
                        this.settings.getMinTimeout());

                this.downloader = new StandardDownloader(config);
                this.downloader.start();
            }

            return this.downloader;
        } catch (Downloader.DownloaderException e) {
            throw new ServiceManagerException(e);
        }
    }

    public ConverterFactory getConverterFactory() throws ServiceManagerException {
        return new ResourceConverterFactory(this.settings.getTempDirectory());
    }

    public DebugConsole getDebugConsole() {

        if (this.debugConsole == null) {
            this.debugConsole = this.debugConsoleEnabled ? new FileDebugConsole(new FileDebugConsoleUpdater(this.settings.getDebugDirectory())) : new NullDebugConsole();
        }

        return this.debugConsole;
    }

    public Map<String, String> getExternalContext() {
        return this.context;
    }

    public ResourceCache getResourceCache() {

        if (this.resourceCache == null) {
            this.resourceCache = new NullResourceCache();

            if (this.settings.isResourceCacheEnabled()) {
                StorageAdapter adapter = new InDirectoryStorageAdapter(this.settings.getResourceCacheRoot());
                this.resourceCache = new StandardResourceCache(adapter,
                        this.timeService,
                        this.settings.getResourceItemMaxAge(),
                        this.settings.getResourceCacheMaxSize(),
                        this.settings.getResourceItemMaxSize());
            }
        }

        return this.resourceCache;
    }

    public ProcessWrapper getProcessWrapper() {

        if (this.processWrapper == null) {
            this.processWrapper = new ExternalProcessManager(this.settings.getExternalProcessMaxFailCount(), new ExternalProcessExecutor());
        }

        return this.processWrapper;
    }

    public Storage getDefaultStorage() throws ServiceManagerException {

        try {

            if (this.defaultStorage == null) {
                this.defaultStorage = new LocalStorage();

                this.defaultStorage.setId(LocalStorage.DEFAULT_STORAGE_ID);
                this.defaultStorage.setHandler(new LocalHandler());
                this.defaultStorage.setTimeService(getTimeService());
                this.defaultStorage.configureRoot(this.settings.getDefaultStorageRoot());
                this.defaultStorage.setFileAge(this.settings.getDefaultStoragePeriod());

                this.defaultStorage.open();
            }

            return this.defaultStorage;
        } catch (Storage.StorageException e) {
            throw new ServiceManagerException(e);
        }
    }

    public void activateReflectionMode() {
        this.reflectionMode = true;
    }

    public void deactivateReflectionMode() {
        this.reflectionMode = false;
    }

    public Preprocessor getPreprocessor() {
        return getContextPreprocessor();
    }

    public GoogleReaderAdapter getGoogleReaderAdapter() throws ServiceManagerException {

        try {
            if (this.profilesStorage == null) {
                this.profilesStorage = new ProfilesStorage(this.settings.getGoogleReaderRoot());
            }

            if (this.googleReaderProvider == null) {
                this.googleReaderProvider = new GoogleReaderProvider();
            }

            if (this.googleReaderAdapter == null) {
                this.googleReaderAdapter = new StandardGoogleReaderAdapter(this.googleReaderProvider, this.profilesStorage);
            }

            return this.googleReaderAdapter;
        } catch (ProfilesStorage.ProfilesStorageException e) {
            throw new ServiceManagerException(e);
        }
    }

    public void cleanup() {

        if (this.resourceCache != null) {
            this.resourceCache.stop();
        }

        if (this.downloader != null) {
            this.downloader.cancel();

            waitSomeTime();

            this.downloader.stop();
        }

        if (this.defaultStorage != null) {
            this.defaultStorage.close();
        }

    }

    private void waitSomeTime() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // empty
        }
    }

    private Preprocessor getContextPreprocessor() {
        return this.reflectionMode ? new NullPreprocessor() : new PlaceholderPreprocessor(this.context);
    }
}
