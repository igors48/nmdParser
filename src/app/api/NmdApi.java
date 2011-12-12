package app.api;

import app.VersionInfo;
import app.cli.blitz.BlitzRequestProcessor;
import app.cli.blitz.request.BlitzRequest;
import app.controller.Controller;
import app.iui.command.ExternalConverterContext;
import app.iui.flow.custom.SingleProcessInfo;
import app.metadata.ObjectMetaData;
import app.templater.*;
import app.workingarea.*;
import app.workingarea.service.StandardServiceManager;
import cloud.PropertiesCloud;
import constructor.dom.*;
import constructor.dom.locator.Mask;
import constructor.objects.channel.adapter.StandardChannelAdapter;
import constructor.objects.channel.configuration.ChannelConfiguration;
import constructor.objects.channel.core.Channel;
import constructor.objects.channel.core.ChannelAdapter;
import constructor.objects.channel.core.ChannelDataListStorage;
import constructor.objects.output.adapter.StandardDocumentBuilderAdapter;
import constructor.objects.output.configuration.OutputConfiguration;
import constructor.objects.output.core.DocumentBuilder;
import constructor.objects.output.core.DocumentBuilderAdapter;
import constructor.objects.output.core.ForEachPostProcessor;
import constructor.objects.output.core.forEach.ForEachPostProcessorUtils;
import constructor.objects.simpler.adapter.StandardSimplerAdapter;
import constructor.objects.simpler.configuration.SimplerConfiguration;
import constructor.objects.simpler.core.Simpler;
import constructor.objects.simpler.core.SimplerAdapter;
import constructor.objects.snippet.adapter.StandardSnippetProcessorAdapter;
import constructor.objects.snippet.core.SnippetProcessor;
import constructor.objects.snippet.core.SnippetProcessorAdapter;
import constructor.objects.source.adapter.StandardSourceAdapter;
import constructor.objects.source.configuration.SourceConfiguration;
import constructor.objects.source.core.ModificationListStorage;
import constructor.objects.source.core.Source;
import constructor.objects.source.core.SourceAdapter;
import constructor.objects.storage.Storage;
import greader.GoogleReaderAdapter;
import greader.profile.Profiles;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import timeservice.TimeService;
import util.Assert;
import util.FileTools;
import util.PathTools;
import util.StreamTools;

import java.io.File;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static util.CollectionUtils.newArrayList;

/**
 * NMD API
 *
 * @author Igor Usenko
 *         Date: 19.04.2009
 */
public class NmdApi implements ApiFacade {

    private final Defaults defaults;
    private final SettingsManager settingsManager;
    private final WorkspaceManager workspaceManager;
    private final VersionInfo versionInfo;

    private final TemplatesFactory templatesFactory;

    private boolean debugConsoleEnabled;
    private ServiceManager serviceManager;

    private Settings settings;
    private Workspace workspace;

    private static final long DAYS_TO_MILLIS = 24 * 60 * 60 * 1000;

    private static final String HTTP_PROXY_HOST = "http.proxyHost";
    private static final String HTTP_PROXY_PORT = "http.proxyPort";
    private static final String EXTERNAL_POSTPROCESSING = "external.postprocessing";

    private final Log log;

    public NmdApi(final Defaults _defaults, final SettingsManager _settingsManager, final WorkspaceManager _workspaceManager, final VersionInfo _versionInfo) {
        Assert.notNull(_defaults, "Defaults is null.");
        Assert.notNull(_settingsManager, "Settings manager is null.");
        Assert.notNull(_workspaceManager, "Workspace manager is null.");
        Assert.notNull(_versionInfo, "Version information is null.");

        this.defaults = _defaults;
        this.settingsManager = _settingsManager;
        this.workspaceManager = _workspaceManager;
        this.versionInfo = _versionInfo;

        this.templatesFactory = new FeedTemplatesFactory();

        this.log = LogFactory.getLog(getClass());
    }

    public final String getDefaultSettingsName() {
        return this.defaults.getDefaultSettingsName();
    }

    public final String getDefaultWorkspaceName() {
        return this.defaults.getDefaultWorkspaceName();
    }

    public List<String> getWorkspacesNames() throws FatalException {

        try {
            return this.workspaceManager.getWorkspacesIdList();
        } catch (WorkspaceManager.WorkspaceManagerException e) {
            throw new FatalException(e);
        }
    }

    public void loadSettings(final String _id) throws FatalException {
        Assert.isValidString(_id, "Settings id is not valid.");

        try {
            this.settings = this.settingsManager.getSettings(_id);
            setupProxy();
            createServiceManager();
        } catch (SettingsManager.SettingsManagerException e) {
            throw new FatalException(e);
        }
    }

    private void setupProxy() {

        if (this.settings.isProxyUsed()) {
            System.setProperty(HTTP_PROXY_HOST, this.settings.getProxyHost());
            System.setProperty(HTTP_PROXY_PORT, String.valueOf(this.settings.getProxyPort()));

            Authenticator.setDefault(
                    new Authenticator() {
                        public PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(settings.getUserName(), settings.getUserPassword().toCharArray());
                        }
                    }
            );
        }
    }

    public void loadWorkspace(final String _id) throws FatalException {
        Assert.isValidString(_id, "Workspace name is not valid.");

        try {
            this.workspace = this.workspaceManager.getWorkspace(_id, this.serviceManager);
        } catch (WorkspaceManager.WorkspaceManagerException e) {
            throw new FatalException(e);
        }
    }

    public List<ObjectMetaData> getObjectsMetaData() throws FatalException {
        checkWorkspaceAndSettings();

        ConstructorFactory factory = null;

        try {
            List<ObjectMetaData> result = newArrayList();

            Locator locator = this.workspace.getLocator();
            factory = this.workspace.getConstructorFactory();

            this.serviceManager.activateReflectionMode();

            result.addAll(getMetaDatas(locator, factory, ObjectType.STORAGE));
            result.addAll(getMetaDatas(locator, factory, ObjectType.PROCESSOR));
            result.addAll(getMetaDatas(locator, factory, ObjectType.SOURCE));
            result.addAll(getMetaDatas(locator, factory, ObjectType.CHANNEL));
            result.addAll(getMetaDatas(locator, factory, ObjectType.INTERPRETER));
            result.addAll(getMetaDatas(locator, factory, ObjectType.OUTPUT));
            result.addAll(getMetaDatas(locator, factory, ObjectType.DATEPARSER));
            result.addAll(getMetaDatas(locator, factory, ObjectType.SNIPPET));
            result.addAll(getMetaDatas(locator, factory, ObjectType.SIMPLER));

            return result;
        } catch (Workspace.WorkspaceException e) {
            throw new FatalException(e);
        } catch (Locator.LocatorException e) {
            throw new FatalException(e);
        } catch (Constructor.ConstructorException e) {
            throw new FatalException(e);
        } finally {
            this.serviceManager.deactivateReflectionMode();
        }
    }

    public void createWorkspace(final String _id) throws FatalException {
        Assert.isValidString(_id, "Workspace name is not valid.");

        try {
            this.workspaceManager.createWorkspace(_id, this.serviceManager);
        } catch (WorkspaceManager.WorkspaceManagerException e) {
            throw new FatalException(e);
        }
    }

    public void deleteWorkspace(final String _id) throws FatalException {
        Assert.isValidString(_id, "Workspace name is not valid.");

        try {
            this.workspaceManager.deleteWorkspace(_id);
        } catch (WorkspaceManager.WorkspaceManagerException e) {
            throw new FatalException(e);
        }
    }

    public void renameWorkspace(final String _oldId, final String _newId) throws FatalException {
        Assert.isValidString(_oldId, "Old workspace name is not valid.");
        Assert.isValidString(_newId, "New workspace name is not valid.");

        try {
            this.workspaceManager.renameWorkspace(_oldId, _newId);
        } catch (WorkspaceManager.WorkspaceManagerException e) {
            throw new FatalException(e);
        }
    }

    public List<String> updateFullSet(final String _sourceId, final String _channelId, final String _outputId, final int _forcedDays, final Controller _controller, final Map<String, String> _context, final ExternalConverterContext _externalConverterContext) throws FatalException, RecoverableException {
        Assert.isValidString(_sourceId, "Source id is not valid.");
        Assert.isValidString(_channelId, "Channel id is not valid.");
        Assert.isValidString(_outputId, "Output id is not valid.");
        Assert.notNull(_controller, "Controller is null");
        Assert.notNull(_context, "External context is null");
        Assert.notNull(_externalConverterContext, "External converter context is null");

        List<String> result = newArrayList();

        if (!_controller.isCancelled()) {
            updateSource(_sourceId, _controller, _context);
        }

        if (!_controller.isCancelled()) {
            updateChannel(_channelId, _forcedDays, _controller, _context);
        }

        if (!_controller.isCancelled()) {
            result = updateOutput(_outputId, _forcedDays, _controller, _context);
        }

        if (!_externalConverterContext.isEmpty()) {
            convertFilesExternal(result, _controller, _externalConverterContext);
        }

        return result;
    }

    private void convertFilesExternal(final List<String> _files, final Controller _controller, final ExternalConverterContext _externalConverterContext) {
        int index = 0;

        for (String current : _files) {
            _controller.onProgress(_files.size() == 1 ? new SingleProcessInfo(EXTERNAL_POSTPROCESSING) : new SingleProcessInfo(EXTERNAL_POSTPROCESSING, index++, _files.size()));
            convertFileExternal(current, _externalConverterContext);
        }
    }

    private void convertFileExternal(final String _file, final ExternalConverterContext _externalConverterContext) {
        ForEachPostProcessor postProcessor = ForEachPostProcessorUtils.createForEachPostProcessor(this.serviceManager.getProcessWrapper(), _externalConverterContext.getPath(), _externalConverterContext.getPattern(), _externalConverterContext.getTimeout());

        File target = new File(_file);

        String path = PathTools.normalize(target.getParent());
        String name = target.getName();

        postProcessor.process(path, name);
    }

    public List<String> getSourcesNames(final Mask _mask) throws FatalException {
        Assert.notNull(_mask, "Mask is null.");
        return locateAll(ObjectType.SOURCE, _mask);
    }

    public void updateSource(final String _id, final Controller _controller, final Map<String, String> _context) throws FatalException, RecoverableException {
        Assert.isValidString(_id, "Source id is not valid.");
        Assert.notNull(_controller, "Controller is null");
        Assert.notNull(_context, "External context is null");

        checkWorkspaceAndSettings();

        try {
            this.serviceManager.setExternalContext(_context);

            Source source = getSource(_id, _controller);
            source.process();
        } catch (Workspace.WorkspaceException e) {
            throw new FatalException(e);
        } catch (Source.SourceException e) {
            throw new FatalException(e);
        } catch (ServiceManager.ServiceManagerException e) {
            throw new FatalException(e);
        }
    }

    public List<String> getChannelsNames(final Mask _mask) throws FatalException {
        Assert.notNull(_mask, "Mask is null.");
        return locateAll(ObjectType.CHANNEL, _mask);
    }

    public void updateChannel(final String _id, final int _forcedDays, final Controller _controller, final Map<String, String> _context) throws FatalException, RecoverableException {
        Assert.isValidString(_id, "Channel id is not valid.");
        Assert.notNull(_controller, "Controller is null");
        Assert.notNull(_context, "External context is null");

        checkWorkspaceAndSettings();

        try {
            this.serviceManager.setExternalContext(_context);

            Channel channel = getChannel(_id, _forcedDays, _controller);
            channel.process();
        } catch (Workspace.WorkspaceException e) {
            throw new FatalException(e);
        } catch (Channel.ChannelException e) {
            throw new FatalException(e);
        } catch (ServiceManager.ServiceManagerException e) {
            throw new FatalException(e);
        }
    }

    public List<String> getOutputsNames(final Mask _mask) throws FatalException {
        Assert.notNull(_mask, "Mask is null.");
        return locateAll(ObjectType.OUTPUT, _mask);
    }

    public List<String> updateOutput(final String _id, final int _forcedDays, final Controller _controller, final Map<String, String> _context) throws FatalException, RecoverableException {
        Assert.isValidString(_id, "Output id is not valid.");
        Assert.notNull(_controller, "Controller is null");
        Assert.notNull(_context, "External context is null");

        checkWorkspaceAndSettings();

        try {
            this.serviceManager.setExternalContext(_context);

            DocumentBuilder documentBuilder = getOutput(_id, _forcedDays, _controller);

            return documentBuilder.process();
        } catch (Workspace.WorkspaceException e) {
            throw new FatalException(e);
        } catch (DocumentBuilder.DocumentBuilderException e) {
            throw new FatalException(e);
        } catch (ServiceManager.ServiceManagerException e) {
            throw new FatalException(e);
        }
    }

    public List<String> getSimplersNames(final Mask _mask) throws FatalException {
        Assert.notNull(_mask, "Mask is null.");
        return locateAll(ObjectType.SIMPLER, _mask);
    }

    public void storeSimplerConfiguration(final SimplerConfiguration _configuration) throws FatalException {
        Assert.notNull(_configuration, "Simpler configuration is null");

        checkWorkspaceAndSettings();

        try {
            this.workspace.getLocator().storeSimplerConfiguration(_configuration);
        } catch (Locator.LocatorException e) {
            throw new FatalException(e);
        } catch (Workspace.WorkspaceException e) {
            throw new FatalException(e);
        }
    }

    public void removeSimplerConfiguration(final SimplerConfiguration _configuration) throws FatalException {
        Assert.notNull(_configuration, "Simpler configuration is null");

        checkWorkspaceAndSettings();

        try {
            this.workspace.getLocator().removeSimplerConfiguration(_configuration);
        } catch (Locator.LocatorException e) {
            throw new FatalException(e);
        } catch (Workspace.WorkspaceException e) {
            throw new FatalException(e);
        }
    }

    public void enableDebugConsole() {
        this.debugConsoleEnabled = true;
    }

    public void createTemplates(final TemplateParameters _parameters) throws FatalException {
        Assert.notNull(_parameters, "Template parameters is null");

        try {
            checkWorkspaceAndSettings();

            List<Template> templates = this.templatesFactory.createTemplates(_parameters);
            this.workspace.getLocator().storeTemplates(_parameters.getName(), templates);
        } catch (Locator.LocatorException e) {
            throw new FatalException(e);
        } catch (Workspace.WorkspaceException e) {
            throw new FatalException(e);
        }
    }

    public List<String> processBlitzRequest(final BlitzRequest _request, final int _forcedDays) throws FatalException {
        Assert.notNull(_request, "Blitz request parameters is null");

        checkWorkspaceAndSettings();

        try {
            _request.setForced(_forcedDays == 0);

            if (_forcedDays > 0) {
                _request.setMaxAge(_forcedDays * DAYS_TO_MILLIS);
            }

            BlitzRequestProcessor processor = new BlitzRequestProcessor(_request, this.serviceManager, this.settings, this.workspace, this.versionInfo);

            return processor.process();
        } catch (BlitzRequestProcessor.BlitzRequestProcessorException e) {
            throw new FatalException(e);
        }
    }

    public List<String> processSnippet(final String _name, final int _forcedDays, final Map<String, String> _context) throws FatalException {
        Assert.isValidString(_name, "Snippet name is not valid");
        Assert.isTrue(new File(_name).exists(), "Snippet file [ " + _name + " ] does not exists");
        Assert.notNull(_context, "External context is null");

        checkWorkspaceAndSettings();

        try {
            this.serviceManager.setExternalContext(_context);

            checkWorkspaceAndSettings();
            SnippetProcessorAdapter adapter = new StandardSnippetProcessorAdapter(_name, this.workspace.getConstructorFactory(), this.serviceManager.getTimeService());
            BlitzRequest blitzRequest = SnippetProcessor.process(adapter);

            List<String> result = processBlitzRequest(blitzRequest, _forcedDays);

            long latestItemTime = blitzRequest.getLatestItemTime();

            if (latestItemTime != -1) {
                adapter.setLastUpdateTime(latestItemTime);
            }

            return result;
        } catch (Workspace.WorkspaceException e) {
            throw new FatalException(e);
        } catch (ServiceManager.ServiceManagerException e) {
            throw new FatalException(e);
        } catch (SnippetProcessor.SnippetProcessorException e) {
            throw new FatalException(e);
        } catch (SnippetProcessorAdapter.SnippetProcessorAdapterException e) {
            throw new FatalException(e);
        }
    }

    public int getUpdateAttempts() throws FatalException {
        checkWorkspaceAndSettings();

        return this.settings.getUpdateAttempts();
    }

    public long getUpdateTimeout() throws FatalException {
        checkWorkspaceAndSettings();

        return this.settings.getUpdateTimeout();
    }

    public TimeService getTimeService() throws FatalException {

        try {
            checkWorkspaceAndSettings();

            return this.serviceManager.getTimeService();
        } catch (ServiceManager.ServiceManagerException e) {
            throw new FatalException(e);
        }
    }

    public void removeServiceFiles(final Mask _mask) throws FatalException {
        Assert.notNull(_mask, "Mask is null.");

        checkWorkspaceAndSettings();

        removeSourcesServiceFiles(_mask);
        removeChannelsServiceFiles(_mask);
        removeOutputsServiceFiles(_mask);
    }

    public void createGoogleReaderProfile(final String _email, final String _password) throws FatalException {
        Assert.isValidString(_email, "EMail is not valid");
        Assert.isValidString(_password, "Password is not valid");

        try {
            this.serviceManager.getGoogleReaderAdapter().createProfile(_email, _password);
        } catch (GoogleReaderAdapter.GoogleReaderAdapterException e) {
            throw new FatalException(e);
        } catch (ServiceManager.ServiceManagerException e) {
            throw new FatalException(e);
        }
    }

    public void deleteGoogleReaderProfile(final String _email) throws FatalException {
        Assert.isValidString(_email, "EMail is not valid");

        try {
            this.serviceManager.getGoogleReaderAdapter().removeProfile(_email);
        } catch (GoogleReaderAdapter.GoogleReaderAdapterException e) {
            throw new FatalException(e);
        } catch (ServiceManager.ServiceManagerException e) {
            throw new FatalException(e);
        }
    }

    public List<String> updateGoogleReaderProfile(final String _email) throws FatalException {
        Assert.isValidString(_email, "EMail is not valid");

        try {
            return this.serviceManager.getGoogleReaderAdapter().updateProfile(_email, this);
        } catch (GoogleReaderAdapter.GoogleReaderAdapterException e) {
            throw new FatalException(e);
        } catch (ServiceManager.ServiceManagerException e) {
            throw new FatalException(e);
        }
    }

    public void changeGoogleReaderProfilePassword(String _email, String _newPassword) throws FatalException {
        Assert.isValidString(_email, "EMail is not valid");
        Assert.isValidString(_newPassword, "Password is not valid");

        try {
            this.serviceManager.getGoogleReaderAdapter().changeProfilePassword(_email, _newPassword);
        } catch (GoogleReaderAdapter.GoogleReaderAdapterException e) {
            throw new FatalException(e);
        } catch (ServiceManager.ServiceManagerException e) {
            throw new FatalException(e);
        }
    }


    public List<String> testGoogleReaderProfile(final String _email, final String _feed) throws FatalException {
        Assert.isValidString(_email, "EMail is not valid");
        Assert.isValidString(_feed, "Feeds is not valid");

        try {
            return this.serviceManager.getGoogleReaderAdapter().testProfileFeed(_email, _feed, this);
        } catch (GoogleReaderAdapter.GoogleReaderAdapterException e) {
            throw new FatalException(e);
        } catch (ServiceManager.ServiceManagerException e) {
            throw new FatalException(e);
        }
    }

    @Override
    public Profiles getRegisteredGoogleReaderProfiles() throws FatalException {

        try {
            return this.serviceManager.getGoogleReaderAdapter().getRegisteredProfiles();
        } catch (GoogleReaderAdapter.GoogleReaderAdapterException e) {
            throw new FatalException(e);
        } catch (ServiceManager.ServiceManagerException e) {
            throw new FatalException(e);
        }
    }

    public void cleanup() {

        if (this.serviceManager != null) {
            this.serviceManager.cleanup();
        }

        if (this.workspace != null) {
            this.workspace.cleanup();
        }

        if (this.settings != null) {
            cleanTempDirectory();
        }
    }

    private List<ObjectMetaData> getMetaDatas(final Locator _locator, final ConstructorFactory _factory, final ObjectType _type) throws Locator.LocatorException, Constructor.ConstructorException {
        List<ObjectMetaData> result = newArrayList();

        List<String> ids = _locator.locateAll(_type, new Mask(new ArrayList<String>(), new ArrayList<String>()));

        for (String id : ids) {
            ObjectMetaData metaData = getMetaData(id, _type, _locator, _factory);
            result.add(metaData);
        }

        return result;
    }

    private ObjectMetaData getMetaData(final String _id, final ObjectType _type, final Locator _locator, final ConstructorFactory _factory) throws Constructor.ConstructorException, Locator.LocatorException {
        Blank blank = _factory.getConstructor().create(_id, _type);

        ObjectMetaData result = new ObjectMetaData(_id, _type, blank);

        List<UsedObject> usedObjects = blank.getUsedObjects();

        for (UsedObject current : usedObjects) {
            result.addDependency(current);
        }

        String image = StreamTools.readStream(_locator.locate(_id, _type));
        List<PlaceHolderInfo> placeHolderInfos = PlaceHolderUtils.getPlaceHolderInfos(image);

        result.setPlaceholders(placeHolderInfos);

        result.setSourceFile(_locator.getSourceFile(_id, _type));

        return result;
    }

    private void cleanTempDirectory() {
        File directory = new File(this.settings.getTempDirectory());

        if (directory.exists()) {
            FileTools.clearDirectory(directory);
        }
    }

    private List<String> locateAll(final ObjectType _type, final Mask _mask) throws FatalException {
        checkWorkspaceAndSettings();

        try {
            return this.workspace.getLocator().locateAll(_type, _mask);
        } catch (Locator.LocatorException e) {
            throw new FatalException(e);
        } catch (Workspace.WorkspaceException e) {
            throw new FatalException(e);
        }
    }

    private void checkWorkspaceAndSettings() throws FatalException {

        if (this.settings == null) {
            throw new FatalException("Settings not loaded.");
        }

        if (this.workspace == null) {
            throw new FatalException("Workspace not loaded.");
        }
    }

    private void createServiceManager() {
        this.serviceManager = new StandardServiceManager(this.settings, this.debugConsoleEnabled);
    }

    private void removeOutputsServiceFiles(final Mask _mask) {

        try {
            List<String> outputs = this.workspace.getLocator().locateAll(ObjectType.OUTPUT, _mask);

            for (String output : outputs) {
                removePropertiesSilent(output, ObjectType.OUTPUT);
            }

            List<String> simplers = this.workspace.getLocator().locateAll(ObjectType.SIMPLER, _mask);

            for (String simpler : simplers) {
                removePropertiesSilent(Simpler.createId(simpler, ObjectType.OUTPUT), ObjectType.OUTPUT);
            }

        } catch (Locator.LocatorException e) {
            this.log.error("Error while remove service files", e);
        } catch (Workspace.WorkspaceException e) {
            this.log.error("Error while remove service files", e);
        }

    }

    private void removeChannelsServiceFiles(final Mask _mask) {

        try {
            List<String> channels = this.workspace.getLocator().locateAll(ObjectType.CHANNEL, _mask);

            for (String channel : channels) {
                removeChannelDataSilent(channel);
            }

            List<String> simplers = this.workspace.getLocator().locateAll(ObjectType.SIMPLER, _mask);

            for (String simpler : simplers) {
                removeChannelDataSilent(Simpler.createId(simpler, ObjectType.CHANNEL));
            }
        } catch (Locator.LocatorException e) {
            this.log.error("Error while remove service files", e);
        } catch (Workspace.WorkspaceException e) {
            this.log.error("Error while remove service files", e);
        }
    }

    private void removeSourcesServiceFiles(final Mask _mask) {

        try {
            List<String> sources = this.workspace.getLocator().locateAll(ObjectType.SOURCE, _mask);

            for (String source : sources) {
                removeSourceDataSilent(source);
                removePropertiesSilent(source, ObjectType.SOURCE);
            }

            List<String> simplers = this.workspace.getLocator().locateAll(ObjectType.SIMPLER, _mask);

            for (String simpler : simplers) {
                removeSourceDataSilent(Simpler.createId(simpler, ObjectType.SOURCE));
                removePropertiesSilent(Simpler.createId(simpler, ObjectType.SOURCE), ObjectType.SOURCE);
            }

        } catch (Locator.LocatorException e) {
            this.log.error("Error while remove service files", e);
        } catch (Workspace.WorkspaceException e) {
            this.log.error("Error while remove service files", e);
        }

    }

    private void removePropertiesSilent(final String _output, final ObjectType _type) {

        try {
            this.workspace.getCloud().removeProperties(_output, _type);
        } catch (PropertiesCloud.PropertyCloudException e) {
            this.log.error("Error while remove service files", e);
        } catch (Workspace.WorkspaceException e) {
            this.log.error("Error while remove service files", e);
        }
    }

    private void removeChannelDataSilent(final String _channel) {

        try {
            this.workspace.getChannelDataListStorage().remove(_channel);
        } catch (ChannelDataListStorage.ChannelDataListStorageException e) {
            this.log.error("Error while remove service files", e);
        } catch (Workspace.WorkspaceException e) {
            this.log.error("Error while remove service files", e);
        }
    }

    private void removeSourceDataSilent(final String _source) {

        try {
            this.workspace.getModificationListStorage().remove(_source);
        } catch (ModificationListStorage.ModificationListStorageException e) {
            this.log.error("Error while remove service files", e);
        } catch (Workspace.WorkspaceException e) {
            this.log.error("Error while remove service files", e);
        }
    }

    private Source getSource(final String _id, final Controller _controller) throws Workspace.WorkspaceException, ServiceManager.ServiceManagerException, FatalException {
        Blank configuration = getConfiguration(_id, ObjectType.SOURCE);

        SourceAdapter adapter = null;

        if (configuration instanceof SourceConfiguration) {
            adapter = new StandardSourceAdapter(this.workspace.getCloud(),
                    (SourceConfiguration) configuration,
                    this.serviceManager.getFetcherFactory(),
                    this.workspace.getModificationListStorage(),
                    this.serviceManager.getTimeService(),
                    this.settings,
                    _controller);
        }

        if (configuration instanceof SimplerConfiguration) {
            adapter = getSimpler((SimplerConfiguration) configuration, 0, _controller).getSourceAdapter();
        }

        if (adapter == null) {
            throw new FatalException(MessageFormat.format("Can not create adapter for id [ {0} ]", _id));
        }

        return new Source(adapter);
    }

    private Channel getChannel(final String _id, final int _forcedDays, final Controller _controller) throws Workspace.WorkspaceException, ServiceManager.ServiceManagerException, FatalException {
        Blank configuration = getConfiguration(_id, ObjectType.CHANNEL);

        ChannelAdapter adapter = null;

        if (configuration instanceof ChannelConfiguration) {
            adapter = new StandardChannelAdapter(this.workspace.getChannelDataListStorage(),
                    (ChannelConfiguration) configuration,
                    this.serviceManager.getBatchLoader(),
                    this.workspace.getModificationListStorage(),
                    this.workspace.getConstructorFactory(),
                    _forcedDays,
                    this.serviceManager.getTimeService(),
                    _controller,
                    this.settings.getPrecachedItemsCount());
        }

        if (configuration instanceof SimplerConfiguration) {
            adapter = getSimpler((SimplerConfiguration) configuration, _forcedDays, _controller).getChannelAdapter();
        }

        if (adapter == null) {
            throw new FatalException(MessageFormat.format("Can not create adapter for id [ {0} ]", _id));
        }

        return new Channel(adapter);
    }

    private DocumentBuilder getOutput(final String _id, final int _forcedDays, final Controller _controller) throws Workspace.WorkspaceException, ServiceManager.ServiceManagerException, FatalException {
        Blank configuration = getConfiguration(_id, ObjectType.OUTPUT);

        DocumentBuilderAdapter adapter = null;

        if (configuration instanceof OutputConfiguration) {
            List<Storage> storages = newArrayList();
            storages.add(this.workspace.getStorage(((OutputConfiguration) configuration).getStorageId()));

            adapter = new StandardDocumentBuilderAdapter(this.workspace.getCloud(),
                    this.workspace.getChannelDataListStorage(),
                    (OutputConfiguration) configuration,
                    this.serviceManager.getTimeService(),
                    this.serviceManager.getBatchLoader(),
                    this.serviceManager.getConverterFactory(),
                    this.serviceManager.getResourceCache(),
                    this.serviceManager.getProcessWrapper(),
                    this.settings.getResourceDummy(),
                    this.settings.getTempDirectory(),
                    this.settings.getMaxFileNameLength(),
                    storages,
                    _forcedDays,
                    this.versionInfo,
                    this.settings.getResourceConversionContext(),
                    this.settings.isFromNewToOld(),
                    _controller);
        }

        if (configuration instanceof SimplerConfiguration) {
            adapter = getSimpler((SimplerConfiguration) configuration, _forcedDays, _controller).getOutputAdapter();
        }

        if (adapter == null) {
            throw new FatalException(MessageFormat.format("Can not create adapter for id [ {0} ]", _id));
        }

        return new DocumentBuilder(adapter);
    }

    private Simpler getSimpler(final SimplerConfiguration _configuration, final int _forcedDays, final Controller _controller) throws ServiceManager.ServiceManagerException, Workspace.WorkspaceException {
        SimplerAdapter adapter = new StandardSimplerAdapter(_configuration,
                this.serviceManager.getTimeService(),
                this.serviceManager.getBatchLoader(),
                this.workspace.getCloud(),
                _controller,
                this.serviceManager.getConverterFactory(),
                this.serviceManager.getResourceCache(),
                this.workspace.getModificationListStorage(),
                this.workspace.getChannelDataListStorage(),
                Arrays.asList(this.workspace.getStorage(_configuration.getStorageId())),
                this.settings.isFromNewToOld(),
                this.settings.getPrecachedItemsCount(),
                _forcedDays,
                this.settings.getMaxTryCount(),
                this.settings.getErrorTimeout(),
                this.settings.getMinTimeout(),
                this.settings.getMaxFileNameLength(),
                this.settings.getTempDirectory(),
                this.settings.getResourceDummy(),
                this.settings.getResourceConversionContext(),
                this.versionInfo,
                this.serviceManager.getDebugConsole());

        return new Simpler(adapter);
    }

    private Blank getConfiguration(final String _id, final ObjectType _type) throws Workspace.WorkspaceException, FatalException {
        ConstructorFactory factory = this.workspace.getConstructorFactory();

        Blank configuration = createConfiguration(_id, _type, factory);

        if (configuration == null) {
            configuration = createConfiguration(_id, ObjectType.SIMPLER, factory);
        }

        if (configuration == null) {
            throw new FatalException(MessageFormat.format("Can not find configuration for id [ {0}]", _id));
        }

        return configuration;
    }

    private Blank createConfiguration(final String _id, final ObjectType _type, final ConstructorFactory factory) {
        Blank result = null;

        try {
            result = factory.getConstructor().create(_id, _type);
        } catch (Constructor.ConstructorException e) {
            this.log.debug(e);
        }

        return result;
    }


}
