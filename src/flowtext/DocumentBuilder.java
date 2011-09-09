package flowtext;

import flowtext.list.FlowList;
import html.parser.SimpleHtmlConverterUtil;
import html.parser.tag.format.TextFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;
import util.PathTools;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 26.10.2008
 */
public class DocumentBuilder {
    private static final String SECTION_ID_PREFIX = "SEC";

    private FormatStack formatStack;
    private Paragraph currentParagraph;
    private Section currentSection;
    private int sectionCounter;
    private StringBuffer accumulator;

    private String storedFootNote;

    private List<FlowList> lists;

    private final String baseUrl;
    private final boolean resolveImageLinks;

    private final Log log;

    public DocumentBuilder(final DocumentBuilderContext _context) {
        Assert.notNull(_context, "Document builder context is null");

        this.baseUrl = _context.getBaseUrl();
        this.resolveImageLinks = _context.isResolveImageLinks();

        this.storedFootNote = "";

        this.lists = new ArrayList<FlowList>();

        this.log = LogFactory.getLog(getClass());
    }

    public void changeCurrentParagraph() throws Section.SectionException {

        if (!currentParagraphEmpty()) {
            insertCurrentParagraph();
            createCurrentParagraph();
        }
    }

    public boolean currentParagraphEmpty() {
        return this.currentParagraph.getContent().size() == 0;
    }

    public void insertEmptyLine() throws Section.SectionException {
        this.currentSection.insertEmptyLine();
    }

    public void insertHorizontalRule() {
        // this.currentParagraph.insertSimpleText(HORIZONTAL_RULE);
    }

    public void openList() {
        // may be insert current paragraph
        // check for inners
        this.lists.add(new FlowList());
    }

    public void closeList() throws Section.SectionException {

        //flush accu
        if (this.lists.size() > 0) {

            if (this.lists.size() == 1) {
                this.currentSection.insertList(this.lists.get(0));
                this.lists.clear();
            } else {
                FlowList list = this.lists.get(this.lists.size() - 2);
                list.appendInnerList(this.lists.get(this.lists.size() - 1));
                this.lists.remove(this.lists.get(this.lists.size() - 1));
            }
        }
    }

    public void openListItem() {

    }

    public void closeListItem() {

    }

    public void storeFootNote(final String _data) {
        Assert.isValidString(_data);

        this.storedFootNote = _data;
    }

    public void insertStoredFootNote() {

        if (this.storedFootNote != null) {

            if (!this.storedFootNote.isEmpty()) {

                if (PathTools.imageLink(this.storedFootNote) && this.resolveImageLinks) {
                    changeCurrentParagraphSilent();
                    this.currentParagraph.insertResource(this.baseUrl, this.storedFootNote);
                    changeCurrentParagraphSilent();
                } else {
                    this.currentParagraph.insertFootNote(this.storedFootNote, this.baseUrl);
                }

                this.storedFootNote = "";
            }
        }
    }

    public void pushFormat(final TextFormat _format) {
        Assert.notNull(_format);

        this.formatStack.push(_format);
    }

    public TextFormat popFormat() {
        return this.formatStack.pop();
    }

    public void insertResourceLink(final String _address) {
        Assert.isValidString(_address);

        // если сохраненный линк(при наличии) является ссылкой на графику
        // считаем, что эта ссылка "тамбнейл" -> "оригинал" и ничего не делаем
        // если сохраненный линк(при наличии) не является ссылкой на графику
        // считаем, что эта ссылка "просто картинка" и добавляем ее в документ
        if (!PathTools.imageLink(this.storedFootNote) || !this.resolveImageLinks) {
            changeCurrentParagraphSilent();
            this.currentParagraph.insertResource(this.baseUrl, _address);
            changeCurrentParagraphSilent();
        }
    }

    private void changeCurrentParagraphSilent() {

        try {
            changeCurrentParagraph();
        } catch (Section.SectionException e) {
            this.log.debug(e);
        }
    }

    public void flushAccumulatorWithoutChangeFormat() {
        String data = SimpleHtmlConverterUtil.removeUnneededSpaces(this.accumulator.toString());

        if (data.length() != 0) {
            TextFormat oldFormat = this.formatStack.pop();
            this.formatStack.push(oldFormat);
            insertTextElement(data, this.formatStack.pop());
            this.formatStack.push(oldFormat);
        }

        resetAccumulator();
    }

    public void flushAccumulator() {
        String data = SimpleHtmlConverterUtil.removeUnneededSpaces(this.accumulator.toString());

        if (data.length() != 0) {
            insertTextElement(data, this.formatStack.pop());
        }

        resetAccumulator();
    }

    public void appendToAccumulator(final String _text) {
        this.accumulator.append(_text);
    }

    public void resetAccumulator() {
        this.accumulator = new StringBuffer();
    }

    public String getAccumulator() {
        return this.accumulator.toString();
    }

    public void resetFormatStack() {
        this.formatStack = new FormatStack();
    }

    public void createCurrentParagraph() {
        this.currentParagraph = new Paragraph();
    }

    public void createCurrentSection() {
        this.currentSection = new Section(SECTION_ID_PREFIX + Long.toString(this.sectionCounter));
        ++this.sectionCounter;
    }

    public Section getCurrentSection() {
        return this.currentSection;
    }

    public void resetSectionCounter() {
        this.sectionCounter = 1;
    }

    private void insertCurrentParagraph() throws Section.SectionException {
        this.currentSection.insertParagraph(this.currentParagraph);
    }

    private void insertTextElement(final String _data, final TextFormat _format) {

        switch (_format) {
            case SIMPLE: {
                this.currentParagraph.insertSimpleText(_data);
                break;
            }
            case STRONG: {
                this.currentParagraph.insertStrongText(_data);
                break;
            }
            case EMPHASIS: {
                this.currentParagraph.insertEmphasisText(_data);
                break;
            }
        }
    }

}
