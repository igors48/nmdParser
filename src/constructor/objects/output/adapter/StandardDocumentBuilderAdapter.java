package constructor.objects.output.adapter;

import app.VersionInfo;
import app.controller.Controller;
import app.workingarea.ProcessWrapper;
import cloud.PropertiesCloud;
import constructor.objects.AdapterException;
import constructor.objects.channel.core.ChannelDataListStorage;
import constructor.objects.output.configuration.Composition;
import constructor.objects.output.configuration.DateSectionMode;
import constructor.objects.output.configuration.DocumentItemsSortMode;
import constructor.objects.output.configuration.OutputConfiguration;
import constructor.objects.output.core.AbstractDocumentBuilderAdapter;
import constructor.objects.output.core.forEach.ForEachPostProcessorUtils;
import constructor.objects.storage.Storage;
import converter.Converter;
import converter.ConverterContext;
import converter.format.fb2.Fb2Converter;
import converter.format.fb2.resource.Fb2ResourceConversionContext;
import converter.format.fb2.resource.resolver.cache.ResourceCache;
import http.HttpRequestHandler;
import flowtext.Document;
import resource.ConverterFactory;
import timeservice.TimeService;
import util.Assert;

import java.util.List;

/**
 * ����������� ������� ����������� ���������
 *
 * @author Igor Usenko
 *         Date: 11.04.2009
 */
public class StandardDocumentBuilderAdapter extends AbstractDocumentBuilderAdapter {

    private final HttpRequestHandler httpRequestHandler;
    private final ConverterFactory converterFactory;
    private final OutputConfiguration outputConfiguration;
    private final ResourceCache cache;
    private final ProcessWrapper processWrapper;
    private final String dummy;
    private final String tempDir;
    private final int maxFileNameLength;
    private final List<Storage> storages;
    private final Fb2ResourceConversionContext conversionContext;
    private final boolean fromNewToOld;


    public StandardDocumentBuilderAdapter(final PropertiesCloud _cloud,
                                          final ChannelDataListStorage _channelDataListStorage,
                                          final OutputConfiguration _outputConfiguration,
                                          final TimeService _timeService,
                                          final HttpRequestHandler _httpRequestHandler,
                                          final ConverterFactory _converterFactory,
                                          final ResourceCache _cache,
                                          final ProcessWrapper _processManager,
                                          final String _dummy,
                                          final String _tempDir,
                                          final int _maxFileNameLength,
                                          final List<Storage> _storages,
                                          final int _forcedDays,
                                          final VersionInfo _versionInfo,
                                          final Fb2ResourceConversionContext _conversionContext,
                                          final boolean _fromNewToOld,
                                          final Controller _controller) {
        super(_timeService, _cloud, _versionInfo, _controller, _forcedDays, _channelDataListStorage);

        Assert.notNull(_httpRequestHandler, "Http request handler is null");
        this.httpRequestHandler = _httpRequestHandler;

        Assert.notNull(_outputConfiguration, "Output configuration is null");
        this.outputConfiguration = _outputConfiguration;

        Assert.notNull(_converterFactory, "Converter factory is null");
        this.converterFactory = _converterFactory;

        Assert.notNull(_cache, "Resource cache is null");
        this.cache = _cache;

        Assert.notNull(_processManager, "Process manager is null");
        this.processWrapper = _processManager;

        Assert.isValidString(_dummy, "Dummy is not valid");
        this.dummy = _dummy;

        Assert.isValidString(_tempDir, "Temporary path is not valid");
        this.tempDir = _tempDir;

        Assert.greater(_maxFileNameLength, 0, "Maximum file name length <= 0");
        this.maxFileNameLength = _maxFileNameLength;

        Assert.notNull(_storages, "Storages list is null");
        this.storages = _storages;

        Assert.notNull(_conversionContext, "Conversion context is null");
        this.conversionContext = _conversionContext;

        this.fromNewToOld = _fromNewToOld;
    }

    public String getId() {
        return this.outputConfiguration.getId();
    }

    protected String getChannelId() {
        return this.outputConfiguration.getChannelId();
    }

    public Composition getComposition() throws AdapterException {
        return this.outputConfiguration.getComposition();
    }

    public String getDocumentName() throws AdapterException {
        return this.outputConfiguration.getName() == null ? "" : this.outputConfiguration.getName();
    }

    public List<String> postprocessDocument(final Document _document) throws AdapterException {
        Assert.notNull(_document, "Document is null.");

        try {
            Fb2Converter converter = new Fb2Converter(this.httpRequestHandler, this.converterFactory, this.cache, this.dummy, this.conversionContext, this.controller);

            String name = DocumentBuilderAdapterUtils.getName(_document.getHeader().getBookTitle(), this.maxFileNameLength);

            ConverterContext context = new ConverterContext(_document,
                    this.outputConfiguration.getBranch(),
                    name,
                    this.tempDir,
                    this.storages,
                    this.outputConfiguration.isLinksAsFootnotes(),
                    this.outputConfiguration.isRemoveExists(),
                    this.outputConfiguration.isResolveImageLinks(),
                    ForEachPostProcessorUtils.createForEachPostProcessor(this.processWrapper, this.outputConfiguration.getForEachPath(), this.outputConfiguration.getForEachCommand(), this.outputConfiguration.getForEachWait()),
                    this.controller);

            return converter.convert(context);
        } catch (Converter.ConverterException e) {
            throw new AdapterException(e);
        }
    }

    public boolean resolveImageLinks() {
        return this.outputConfiguration.isResolveImageLinks();
    }

    public DateSectionMode getDateSectionMode() {
        return this.outputConfiguration.getDateSectionMode();
    }

    public boolean isFromNewToOld() {
        return this.outputConfiguration.getFromNewToOld() == DocumentItemsSortMode.DEFAULT ? this.fromNewToOld : this.outputConfiguration.getFromNewToOld() == DocumentItemsSortMode.FROM_NEW_TO_OLD;
    }
}
