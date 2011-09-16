package converter.format.fb2;

import app.controller.Controller;
import app.iui.flow.custom.SingleProcessInfo;
import constructor.objects.storage.Storage;
import constructor.objects.storage.local.core.FileStoreItem;
import converter.Converter;
import converter.ConverterContext;
import converter.format.fb2.footnotes.Fb2FootNoteList;
import converter.format.fb2.objects.*;
import converter.format.fb2.resource.Fb2ResourceBundle;
import converter.format.fb2.resource.Fb2ResourceConversionContext;
import converter.format.fb2.resource.resolver.cache.ResourceCache;
import downloader.HttpRequestHandler;
import flowtext.*;
import flowtext.resource.Resource;
import flowtext.text.FootNote;
import resource.ConverterFactory;
import util.Assert;
import util.PathTools;
import util.TextTools;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

//todo �������� ���� �� ���������. �����!

/**
 * @author Igor Usenko
 *         Date: 03.09.2008
 */
public class Fb2Converter implements Converter {

    private static final String CHARSET_NAME = "UTF-8";

    private final HttpRequestHandler httpRequestHandler;
    private final ConverterFactory factory;
    private final ResourceCache cache;
    private final String dummy;
    private final Fb2ResourceConversionContext conversionContext;
    private final Controller controller;

    private ConverterContext context;

    private static final int MAX_BOOK_TITLE_LEN = 64;
    private static final String DEFAULT_EXTENSION = "fb2";

    public Fb2Converter(final HttpRequestHandler _httpRequestHandler, final ConverterFactory _factory, final ResourceCache _cache, final String _dummy, final Fb2ResourceConversionContext _conversionContext, final Controller _controller) {
        Assert.notNull(_httpRequestHandler, "Http request handler is null");
        Assert.notNull(_factory, "Converter factory is null");
        Assert.notNull(_cache, "Resource cache is null");
        Assert.isValidString(_dummy, "Dummy address is not valid");
        Assert.notNull(_conversionContext, "Conversion context is null");
        Assert.notNull(_controller, "Controller is null");

        this.httpRequestHandler = _httpRequestHandler;
        this.factory = _factory;
        this.cache = _cache;
        this.dummy = _dummy;
        this.conversionContext = _conversionContext;
        this.controller = _controller;
    }

    public List<String> convert(final ConverterContext _context) throws ConverterException {
        Assert.notNull(_context, "Converter context is null");

        try {
            List<String> result = new ArrayList<String>();

            this.context = _context;

            String tempFile = _context.getTempDirectory() + addExtension(_context.getName());

            storeDocumentImage(getDocumentImage(_context.getDocument(), _context.getTempDirectory()), tempFile);

            if (!this.controller.isCancelled()) {
                result = saveToStorages(_context, tempFile);
            }

            return result;
        } catch (Exception e) {
            throw new ConverterException(e);
        }
    }

    private List<String> saveToStorages(final ConverterContext _context, final String _tempFile) throws Storage.StorageException {
        List<String> result = new ArrayList<String>();

        // todo �������� ����������: �������� �����, � ������������� �� ���� ���� 
        for (Storage storage : _context.getStorages()) {
            result.add(storage.store(new FileStoreItem(_context.getBranch(), _tempFile, false, _context.isRemoveExists()), _context.getForEachPostProcessor()));
        }

        return result;
    }

    private String[] getDocumentImage(final Document _document, final String _tempDir) throws ConverterException {
        Fb2ResourceBundle resources = new Fb2ResourceBundle(this.httpRequestHandler, this.factory, this.cache, this.dummy, _tempDir, this.conversionContext, this.controller);
        Fb2FootNoteList notes = new Fb2FootNoteList();

        this.controller.onProgress(new SingleProcessInfo("process.convert.to.fb2"));

        Fb2Header header = parseHeader(_document.getHeader(), resources);
        Fb2Body body = parseBody(_document.getBody(), notes, resources);

        resources.resolve();

        Fb2Document document = new Fb2Document(header, body, notes, resources);

        return document.getStrings();
    }

    private void storeDocumentImage(final String[] _data, final String _fileName) throws ConverterException {
        try {
            PrintWriter writer = new PrintWriter(new File(addExtension(_fileName)), CHARSET_NAME);

            for (String current : _data) {
                writer.println(current);
            }

            writer.close();
        } catch (Exception e) {
            throw new ConverterException(e);
        }

    }

    private String addExtension(String _fileName) {
        String result = _fileName;

        if (!result.endsWith("." + DEFAULT_EXTENSION)) {
            result += "." + DEFAULT_EXTENSION;
        }

        return result;
    }

    private Fb2Header parseHeader(final Header _header, final Fb2ResourceBundle _resources) {

        String coverTag = "";

        if (!_header.getCoverUrl().isEmpty()) {
            coverTag = _resources.appendResourceItem("", _header.getCoverUrl());
        }

        return new Fb2Header(_header.getGenres(),
                _header.getAuthorFirstName(),
                _header.getAuthorLastName(),
                TextTools.intelliCut(_header.getBookTitle(), MAX_BOOK_TITLE_LEN),
                coverTag,
                _header.getLang(),
                _header.getProgramUsed(),
                _header.getDate(),
                _header.getId(),
                _header.getVersion());
    }

    private Fb2Body parseBody(final Body _body, final Fb2FootNoteList _notes, final Fb2ResourceBundle _resources) throws ConverterException {
        Fb2Body result = new Fb2Body();

        for (Section current : _body.getSections()) {
            result.insertSection(parseSection(current, _notes, _resources));
        }

        return result;
    }

    private Fb2Section parseSection(final Section _section, final Fb2FootNoteList _notes, final Fb2ResourceBundle _resources) throws ConverterException {
        try {
            Fb2Section result = new Fb2Section(_section.getId());

            if (_section.getTitle() != null) {
                result.insertTitle(parseTitle(_section.getTitle(), _notes, _resources));
            }

            for (FlowTextObject current : _section.getContent()) {

                switch (current.getType()) {
                    case SECTION: {
                        result.insertSection(parseSection((Section) current, _notes, _resources));

                        break;
                    }
                    case PARAGRAPH: {
                        result.insertParagraph(parseParagraph((Paragraph) current, _notes, _resources));

                        break;
                    }
                    case EMPTY_LINE: {
                        result.insertEmptyLine();

                        break;
                    }
                    default: {
                        throw new ConverterException("Unexpected type in section id : " + _section.getId() + " type : " + current.getType());
                    }
                }
            }
            return result;
        } catch (Exception e) {
            throw new ConverterException(e);
        }
    }

    private Fb2Paragraph parseParagraph(final Paragraph _paragraph, final Fb2FootNoteList _notes, final Fb2ResourceBundle _resources) throws ConverterException {
        Fb2Paragraph result = new Fb2Paragraph();

        for (FlowTextObject current : _paragraph.getContent()) {

            switch (current.getType()) {
                case SIMPLE_TEXT: {
                    result.insertSimpleText(((FlowTextObjectText) current).getText());

                    break;
                }
                case STRONG_TEXT: {
                    result.insertStrongText(((FlowTextObjectText) current).getText());

                    break;
                }
                case EMPHASIS_TEXT: {
                    result.insertEmphasisText(((FlowTextObjectText) current).getText());

                    break;
                }
                case CODE_TEXT: {
                    result.insertCodeText(((FlowTextObjectText) current).getText());

                    break;
                }
                case RESOURCE: {
                    String tag = _resources.appendResourceItem(((Resource) current).getBase(), ((Resource) current).getAddress());
                    result.insertResource(tag);

                    break;
                }
                case FOOT_NOTE: {

                    if (this.context.isLinksAsFootnotes()) {
                        String tag = _notes.appendFootNote(((FootNote) current).getText());
                        result.insertFootNoteLink(tag);
                    }

                    //todo �� � ����!

                    if (PathTools.imageLink(((FootNote) current).getText()) && this.context.isResolveImageLinks()) {
                        String tag = _resources.appendResourceItem(((FootNote) current).getBase(), ((FootNote) current).getText());
                        result.insertResource(tag);
                    }

                    break;
                }
                default: {
                    throw new ConverterException("Unexpected type in paragraph : " + current.getType());
                }
            }
        }

        return result;
    }

    private Fb2Title parseTitle(final Title _title, final Fb2FootNoteList _notes, final Fb2ResourceBundle _resources) throws ConverterException {
        Fb2Title result = new Fb2Title();

        for (FlowTextObject current : _title.getContent()) {

            switch (current.getType()) {
                case PARAGRAPH: {
                    result.insertParagraph(parseParagraph((Paragraph) current, _notes, _resources));

                    break;
                }
                case EMPTY_LINE: {
                    result.insertEmptyLine();

                    break;
                }
                default: {
                    throw new ConverterException("Unexpected type in title");
                }
            }
        }

        return result;
    }
}
