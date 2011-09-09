package app.cli.blitz;

import app.VersionInfo;
import app.cli.blitz.request.BlitzRequest;
import app.controller.NullController;
import app.iui.flow.custom.SingleProcessInfo;
import app.workingarea.ServiceManager;
import app.workingarea.Settings;
import app.workingarea.Workspace;
import constructor.objects.AdapterException;
import constructor.objects.channel.core.stream.ChannelDataList;
import constructor.objects.output.adapter.DocumentBuilderAdapterUtils;
import constructor.objects.output.configuration.Composition;
import constructor.objects.output.configuration.DateSectionMode;
import constructor.objects.output.configuration.DocumentItemsSortMode;
import constructor.objects.output.core.DocumentBuilderAdapter;
import constructor.objects.output.core.forEach.ForEachPostProcessorUtils;
import constructor.objects.storage.Storage;
import converter.Converter;
import converter.ConverterContext;
import converter.format.fb2.Fb2Converter;
import flowtext.Document;
import timeservice.TimeService;
import util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Адаптер блиц-генератора документа
 *
 * @author Igor Usenko
 *         Date: 30.10.2009
 */
public class BlitzDocumentBuilderAdapter implements DocumentBuilderAdapter {

    private final BlitzRequest request;
    private final ChannelDataList channelDataList;
    private final ServiceManager serviceManager;
    private final Settings settings;
    private final Workspace workspace;
    private final VersionInfo versionInfo;

    private static final String BLITZ_OUTPUT_ADAPTER_ID = "blitz_output_adapter";

    public BlitzDocumentBuilderAdapter(final BlitzRequest _request,
                                       final ChannelDataList _channelDataList,
                                       final ServiceManager _serviceManager,
                                       final Settings _settings,
                                       final Workspace _workspace,
                                       final VersionInfo _versionInfo) {
        Assert.notNull(_request, "Request is null");
        this.request = _request;

        Assert.notNull(_channelDataList, "Channel data list is null");
        this.channelDataList = _channelDataList;

        Assert.notNull(_serviceManager, "Service manager is null");
        this.serviceManager = _serviceManager;

        Assert.notNull(_settings, "Settings is null");
        this.settings = _settings;

        Assert.notNull(_workspace, "Workspace is null");
        this.workspace = _workspace;

        Assert.notNull(_versionInfo, "Version information is null");
        this.versionInfo = _versionInfo;
    }

    public String getId() {
        return BLITZ_OUTPUT_ADAPTER_ID;
    }

    public ChannelDataList getChannelDatas() throws AdapterException {
        return this.channelDataList;
    }

    public TimeService getTimeService() throws AdapterException {

        try {
            return this.serviceManager.getTimeService();
        } catch (ServiceManager.ServiceManagerException e) {
            throw new AdapterException(e);
        }
    }

    public List<String> postprocessDocument(Document _document) throws AdapterException {
        Assert.notNull(_document, "Document is null.");

        try {
            Fb2Converter converter = new Fb2Converter(this.serviceManager.getDownloader(),
                    this.serviceManager.getConverterFactory(),
                    this.serviceManager.getResourceCache(),
                    this.settings.getResourceDummy(),
                    this.settings.getResourceConversionContext(),
                    new NullController());

            String name = DocumentBuilderAdapterUtils.getName(_document.getHeader().getBookTitle(), this.settings.getMaxFileNameLength());

            List<Storage> storages = new ArrayList<Storage>();
            storages.add(this.workspace.getStorage(this.request.getStorage()));

            ConverterContext context = new ConverterContext(_document,
                    this.request.getBranch(),
                    name,
                    this.settings.getTempDirectory(),
                    storages,
                    this.request.isLinksAsFootnotes(),
                    this.request.isRemoveExists(),
                    this.request.isResolveImageLinks(),
                    ForEachPostProcessorUtils.createForEachPostProcessor(this.serviceManager.getProcessWrapper(), this.request.getForEachPath(), this.request.getForEachCommand(), this.request.getForEachWait()),
                    new NullController());

            return converter.convert(context);
        } catch (Converter.ConverterException e) {
            throw new AdapterException(e);
        } catch (ServiceManager.ServiceManagerException e) {
            throw new AdapterException(e);
        } catch (Workspace.WorkspaceException e) {
            throw new AdapterException(e);
        }
    }

    public Composition getComposition() throws AdapterException {
        return this.request.getComposition();
    }

    public String getDocumentName() throws AdapterException {
        return this.request.getOutName();
    }

    public long getLatestItemTime() throws AdapterException {
        return 0;
    }

    public void setLatestItemTime(long _time) throws AdapterException {
        // empty
    }

    public boolean isForcedMode() throws AdapterException {
        return true;
    }

    public boolean resolveImageLinks() {
        return this.request.isResolveImageLinks();
    }

    public VersionInfo getVersionInfo() {
        return this.versionInfo;
    }

    public DateSectionMode getDateSectionMode() {
        return DateSectionMode.AUTO;
    }

    public boolean isFromNewToOld() {
        return this.request.isFromNewToOld() == DocumentItemsSortMode.DEFAULT ? this.settings.isFromNewToOld() : this.request.isFromNewToOld() == DocumentItemsSortMode.FROM_NEW_TO_OLD;
    }

    public void onStart() {
        // empty
    }

    public void onProgress(final SingleProcessInfo _info) {
        // empty
    }

    public void onComplete() {
        // empty
    }

    public void onFault() {
        // empty
    }

    public void onCancel() {
        // empty
    }

    public boolean isCancelled() {
        return false;
    }
}
