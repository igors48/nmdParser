package constructor.objects.simpler.core;

import app.VersionInfo;
import app.controller.Controller;
import cloud.PropertiesCloud;
import constructor.objects.AdapterException;
import constructor.objects.channel.core.ChannelDataListStorage;
import constructor.objects.output.adapter.DocumentBuilderAdapterUtils;
import constructor.objects.output.core.AbstractDocumentBuilderAdapter;
import constructor.objects.output.core.forEach.NullForEachPostProcessor;
import constructor.objects.storage.Storage;
import converter.Converter;
import converter.ConverterContext;
import converter.format.fb2.Fb2Converter;
import converter.format.fb2.resource.Fb2ResourceConversionContext;
import converter.format.fb2.resource.resolver.cache.ResourceCache;
import downloader.HttpRequestHandler;
import flowtext.Document;
import resource.ConverterFactory;
import timeservice.TimeService;
import util.Assert;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 07.07.2010
 */
public class SimplerDocumentBuilderAdapter extends AbstractDocumentBuilderAdapter {
    private final String channelId;
    private final String id;
    private final String documentName;
    private final boolean fromNewToOld;
    private final int maxFileNameLength;
    private final String branch;
    private final String tempDir;
    private final boolean linksAsFootnotes;
    private final boolean removeExists;
    private final boolean resolveImageLinks;
    private final HttpRequestHandler httpRequestHandler;
    private final ConverterFactory converterFactory;
    private final ResourceCache cache;
    private final String dummy;
    private final Fb2ResourceConversionContext conversionContext;
    private final List<Storage> storages;

    public SimplerDocumentBuilderAdapter(
            final String _id,
            final String _channelId,
            final String _documentName,
            final boolean _fromNewToOld,
            final int _maxFileNameLength,
            final String _branch,
            final String _tempDir,
            final boolean _linksAsFootnotes,
            final boolean _removeExists,
            final boolean _resolveImageLinks,
            final HttpRequestHandler _httpRequestHandler,
            final ConverterFactory _converterFactory,
            final ResourceCache _cache,
            final String _dummy,
            final Fb2ResourceConversionContext _conversionContext,
            final List<Storage> _storages,
            final TimeService _timeService,
            final PropertiesCloud _cloud,
            final VersionInfo _versionInfo,
            final Controller _controller,
            final int _forcedDays,
            final ChannelDataListStorage _channelDataListStorage) {
        super(_timeService, _cloud, _versionInfo, _controller, _forcedDays, _channelDataListStorage);

        Assert.isValidString(_id, "Id is invalid");
        this.id = _id;

        Assert.isValidString(_channelId, "Channel id is invalid");
        this.channelId = _channelId;

        Assert.isValidString(_documentName, "Document name is invalid");
        this.documentName = _documentName;

        this.fromNewToOld = _fromNewToOld;

        Assert.greater(_maxFileNameLength, 0, "Max file name <= 0");
        this.maxFileNameLength = _maxFileNameLength;

        Assert.notNull(_branch, "Branch is null");
        this.branch = _branch;

        Assert.isValidString(_tempDir, "Temporary path is not valid");
        this.tempDir = _tempDir;

        this.linksAsFootnotes = _linksAsFootnotes;
        this.removeExists = _removeExists;
        this.resolveImageLinks = _resolveImageLinks;

        Assert.notNull(_httpRequestHandler, "Downloader is null");
        this.httpRequestHandler = _httpRequestHandler;

        Assert.notNull(_converterFactory, "Converter factory is null");
        this.converterFactory = _converterFactory;

        Assert.notNull(_cache, "Resource cache is null");
        this.cache = _cache;

        Assert.isValidString(_dummy, "Dummy is not valid");
        this.dummy = _dummy;

        Assert.notNull(_conversionContext, "Conversion context is null");
        this.conversionContext = _conversionContext;

        Assert.notNull(_storages, "Storages list is null");
        this.storages = _storages;
    }

    protected String getChannelId() {
        return this.channelId;
    }

    public String getId() {
        return this.id;
    }

    public List<String> postprocessDocument(final Document _document) throws AdapterException {
        Assert.notNull(_document, "Document is null.");

        try {
            Fb2Converter converter = new Fb2Converter(this.httpRequestHandler, this.converterFactory, this.cache, this.dummy, this.conversionContext, this.controller);

            String name = DocumentBuilderAdapterUtils.getName(_document.getHeader().getBookTitle(), this.maxFileNameLength);

            ConverterContext context = new ConverterContext(_document,
                    this.branch,
                    name,
                    this.tempDir,
                    this.storages,
                    this.linksAsFootnotes,
                    this.removeExists,
                    this.resolveImageLinks,
                    new NullForEachPostProcessor(),
                    this.controller);

            return converter.convert(context);
        } catch (Converter.ConverterException e) {
            throw new AdapterException(e);
        }
    }

    public String getDocumentName() throws AdapterException {
        return this.documentName;
    }

    public boolean isFromNewToOld() {
        return this.fromNewToOld;
    }
}
