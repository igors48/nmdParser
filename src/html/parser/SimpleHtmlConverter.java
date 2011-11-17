package html.parser;

import dated.item.atdc.HtmlContent;
import http.Data;
import flowtext.DocumentBuilder;
import flowtext.DocumentBuilderContext;
import flowtext.Section;
import html.Converter;
import html.parser.tag.HtmlTag;
import html.parser.tag.format.CancelTextFormatHandler;
import html.parser.tag.format.EmphasisTextFormatHandler;
import html.parser.tag.format.StrongTextFormatHandler;
import html.parser.tag.section.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import java.util.*;

/**
 * @author Igor Usenko
 *         Date: 19.09.2008
 */
public class SimpleHtmlConverter implements Converter {

    private static final String OPEN_BRACE = "<";
    private static final String CLOSE_BRACE = ">";

    private final SimpleHtmlConverterContext context;

    private Map<String, TagHandler> tagHandlers;
    private String[] tagList;

    private int openBraceCounter;

    private DocumentBuilder builder;

    private final Log log;

    public SimpleHtmlConverter(final SimpleHtmlConverterContext _context) {
        Assert.notNull(_context, "Simple HTML convertor context is null");
        this.context = _context;

        this.log = LogFactory.getLog(getClass());

        createTagHandlersMap();
        createTagList();
    }

    public List<Section> convert(final HtmlContent _content) throws ConverterException {
        Assert.notNull(_content, "Content is null");

        this.builder = new DocumentBuilder(new DocumentBuilderContext(_content.getBase(), this.context.isResolveImageLinks()));

        reset();

        builder.createCurrentParagraph();
        builder.createCurrentSection();

        List<Section> result = new ArrayList<Section>();

        try {

            String data = prepare(_content.getContent());

            ChunkSource source = new ChunkSource(data, new String[]{OPEN_BRACE, CLOSE_BRACE});
            String buffer = source.get();

            while (buffer != null) {

                if (buffer.equals(OPEN_BRACE)) {
                    increaseOpenBraceCounter();

                    // ���� ���������� ������ � ���� � ��������� �����
                    // ����� ��� ����, ����� ��������� ��������,
                    // ����� � ���� ���� ���������� ������� < >
                    if (this.openBraceCounter == 1) {
                        openBraceReceived();
                    }

                } else if (buffer.equals(CLOSE_BRACE)) {
                    decreaseOpenBraceCounter();

                    if (this.openBraceCounter == 1) {
                        closeBraceReceived();
                    }

                } else {
                    textReceived(buffer);
                }

                buffer = source.get();
            }

            this.builder.flushAccumulator();
            this.builder.changeCurrentParagraph();

            result.add(this.builder.getCurrentSection());
        } catch (Exception e) {
            this.log.error("Error while converting HTML content from URL: " + _content.getUrl(), e);
        }

        return result;
    }

    private String prepare(final String _data) throws Data.DataException {
        return SimpleHtmlConverterUtil.clearEmptyLines(
                SimpleHtmlConverterUtil.processIgnoredTags(
                        /*SimpleHtmlConverterUtil.cleanupHtml*/(_data))); //cleanup �������� � ���� �������������� �����, � ���� �������       
    }

    private void textReceived(final String _text) {
        this.builder.appendToAccumulator(_text);
    }

    private void openBraceReceived() {
        this.builder.flushAccumulatorWithoutChangeFormat();
        increaseOpenBraceCounter();
    }

    private void increaseOpenBraceCounter() {
        ++this.openBraceCounter;
    }

    private void decreaseOpenBraceCounter() {
        --this.openBraceCounter;

        // ������, ����� ������� ��������� ���� ����
        // ��� ������� � ����� ���� ���������
        // ��������� ���� ��� ������� �������� ��� �� 8 ��� 2009
        if (this.openBraceCounter < 0) {
            this.openBraceCounter = 0;
        }
    }

    private void closeBraceReceived() {

        if (openBracesOccured()) {

            try {
                handleTag();
            } catch (TagHandler.TagHandlerException e) {
                this.builder.flushAccumulatorWithoutChangeFormat();
                this.log.debug("Error handling tag ", e);
            }
            decreaseOpenBraceCounter();
        }
    }

    private boolean openBracesOccured() {
        return this.openBraceCounter != 0;
    }

    private void handleTag() throws TagHandler.TagHandlerException {
        HtmlTag htmlTag = new HtmlTag(this.builder.getAccumulator(), this.tagList);

        String tag = htmlTag.getId();

        TagHandler handler = getHandler(tag);

        if (handler == null) {
            this.log.debug("Unknown tag " + tag + ". Full form [" + this.builder.getAccumulator() + "]");
            this.builder.flushAccumulator();
        } else {
            executeHandler(htmlTag, handler);
            this.builder.resetAccumulator();
        }
    }

    private void executeHandler(final HtmlTag _tag, final TagHandler _handler) throws TagHandler.TagHandlerException {
        _handler.handle(_tag, this.builder);
    }

    private TagHandler getHandler(final String _tag) {
        return this.tagHandlers.get(_tag);
    }

    private void reset() {
        resetOpenBraceCounter();
        this.builder.resetSectionCounter();
        this.builder.resetFormatStack();
        this.builder.resetAccumulator();
    }

    private void resetOpenBraceCounter() {
        this.openBraceCounter = 0;
    }

    private void createTagHandlersMap() {
        this.tagHandlers = new HashMap<String, TagHandler>();

        this.tagHandlers.put("B", new StrongTextFormatHandler());
        this.tagHandlers.put("/B", new CancelTextFormatHandler());
        this.tagHandlers.put("I", new EmphasisTextFormatHandler());
        this.tagHandlers.put("/I", new CancelTextFormatHandler());
        this.tagHandlers.put("CODE", new EmphasisTextFormatHandler());
        this.tagHandlers.put("/CODE", new CancelTextFormatHandler());
        this.tagHandlers.put("EM", new EmphasisTextFormatHandler());
        this.tagHandlers.put("/EM", new CancelTextFormatHandler());
        this.tagHandlers.put("BLOCKQUOTE", new EmphasisTextFormatHandler());
        this.tagHandlers.put("/BLOCKQUOTE", new CancelTextFormatHandler());
        this.tagHandlers.put("Q", new EmphasisTextFormatHandler());
        this.tagHandlers.put("/Q", new CancelTextFormatHandler());
        this.tagHandlers.put("STRONG", new StrongTextFormatHandler());
        this.tagHandlers.put("/STRONG", new CancelTextFormatHandler());
        this.tagHandlers.put("H1", new hOpenTagHandler());
        this.tagHandlers.put("/H1", new hCloseTagHandler());
        this.tagHandlers.put("H2", new hOpenTagHandler());
        this.tagHandlers.put("/H2", new hCloseTagHandler());
        this.tagHandlers.put("H3", new hOpenTagHandler());
        this.tagHandlers.put("/H3", new hCloseTagHandler());
        this.tagHandlers.put("H4", new hOpenTagHandler());
        this.tagHandlers.put("/H4", new hCloseTagHandler());
        this.tagHandlers.put("H5", new hOpenTagHandler());
        this.tagHandlers.put("/H5", new hCloseTagHandler());
        this.tagHandlers.put("H6", new hOpenTagHandler());
        this.tagHandlers.put("/H6", new hCloseTagHandler());

        this.tagHandlers.put("P", new pOpenTagHandler());
        this.tagHandlers.put("/P", new pOpenTagHandler());
        this.tagHandlers.put("LI", new pOpenTagHandler());
        this.tagHandlers.put("/LI", new pOpenTagHandler());
        this.tagHandlers.put("BR", new brOpenTagHandler());
        this.tagHandlers.put("HR", new hrOpenTagHandler());
        this.tagHandlers.put("DIV", new pOpenTagHandler());
        this.tagHandlers.put("/DIV", new pOpenTagHandler());

        this.tagHandlers.put("DL", new pOpenTagHandler());
        this.tagHandlers.put("/DL", new pOpenTagHandler());
        this.tagHandlers.put("DT", new pOpenTagHandler());
        this.tagHandlers.put("/DT", new pOpenTagHandler());
        this.tagHandlers.put("DD", new pOpenTagHandler());
        this.tagHandlers.put("/DD", new pOpenTagHandler());

        this.tagHandlers.put("IMG", new imgTagHandler());

        this.tagHandlers.put("A", new aOpenTagHandler());
        this.tagHandlers.put("/A", new aCloseTagHandler());
    }

    private void createTagList() {
        this.tagList = this.tagHandlers.keySet().toArray(new String[this.tagHandlers.size()]);
        Arrays.sort(this.tagList);
    }

}
