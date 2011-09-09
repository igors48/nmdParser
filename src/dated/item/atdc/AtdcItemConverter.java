package dated.item.atdc;

import dated.DatedItem;
import dated.DatedItemConverter;
import dated.DatedItemConverterContext;
import flowtext.FlowTextUtil;
import flowtext.Paragraph;
import flowtext.Section;
import html.Converter;
import html.parser.SimpleHtmlConverter;
import html.parser.SimpleHtmlConverterContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;
import util.TextTools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 08.11.2008
 */
public class AtdcItemConverter implements DatedItemConverter {

    private static final String SECTION_ID_PREFIX = "ITEM";
    private static final int BRIEF_TEXT_LEN = 32;
    private static final String POST_DATE_FORMAT = "EEEE, d MMMM, yyyy, HH:mm z";

    private final Log log;

    public AtdcItemConverter() {
        this.log = LogFactory.getLog(getClass());
    }

    public Section convert(final DatedItem _item, final DatedItemConverterContext _context) {
        Assert.notNull(_item, "ATDC item is null.");
        Assert.notNull(_context, "ATDC converter context is null");

        AtdcItem item = (AtdcItem) _item;
        Section result = makeSection();

        //todo потом рассмотреть вариант injection
        Converter converter = new SimpleHtmlConverter(new SimpleHtmlConverterContext(_context.isResolveImageLinks()));

        createAuthorAvatarParagraph(item, result);
        createAuthorNickParagraph(item, result, converter);
        createAuthorInfoParagraph(item, result, converter);

        if (_context.isInsertDate()) {
            createDateParagraph(_item, result);
        }

        createContentParagraph(item, result, converter);

        return result;
    }

    private void createContentParagraph(final AtdcItem _item, final Section _result, final Converter _converter) {

        try {
            List<Section> converted = _converter.convert(_item.getContent());

            //todo конвертор возвращает список, а мы пользуем первый элемент только. ћожет список там и не нужен
            if (!converted.isEmpty()) {
                _result.copyFrom(converted.get(0));

                if (_item.getTitle().isEmpty()) {
                    _result.insertTitle(FlowTextUtil.createTitle(getBrief(_item, converted.get(0))));
                } else {
                    _result.insertTitle(FlowTextUtil.createTitle(_item.getTitle()));
                }
            } else {
                this.log.warn("Empty data received from HTML converter.");
                _result.insertTitle(FlowTextUtil.createTitle("Empty data received from HTML converter. URL : " + _item.getContent().getUrl()));
            }

            _result.removeDoubleEmptyLines();
        } catch (Exception e) {
            this.log.error("Error while converting HTML content.", e);
            _result.insertTitle(FlowTextUtil.createTitle("Error while converting HTML content. URL : " + _item.getContent().getUrl() + " " + e));
        }
    }

    private void createAuthorAvatarParagraph(final AtdcItem _item, final Section _section) {

        if (_item.getAuthorAvatar().length() != 0) {
            Paragraph authorAvatar = new Paragraph();
            authorAvatar.insertResource(_item.getContent().getUrl(), _item.getAuthorAvatar());
            insertParargraph(_section, authorAvatar);
        }
    }

    private void createAuthorNickParagraph(final AtdcItem _item, final Section _section, final Converter _converter) {

        if (_item.getAuthorNick().length() != 0) {

            try {
                List<Section> infoSections = _converter.convert(new HtmlContent(_item.getContent().getUrl(), _item.getAuthorNick(), _item.getContent().getBase()));
                _section.copyFrom(infoSections.get(0));
            } catch (Exception e) {
                this.log.error("Error processing AuthorNick in HtmlConverter " + _item.getAuthorNick());

                Paragraph authorNick = new Paragraph();
                String nick = _item.getAuthorNick();
                authorNick.insertStrongText(nick);
                insertParargraph(_section, authorNick);
            }
        }

    }

    private void createAuthorInfoParagraph(final AtdcItem _item, final Section _section, final Converter _converter) {

        if (_item.getAuthorInfo().length() != 0) {

            try {
                List<Section> infoSections = _converter.convert(new HtmlContent(_item.getContent().getUrl(), _item.getAuthorInfo(), _item.getContent().getBase()));
                _section.copyFrom(infoSections.get(0));
            } catch (Exception e) {
                this.log.error("Error processing AuthorInfo in HtmlConverter " + _item.getAuthorInfo());

                Paragraph authorInfo = new Paragraph();
                authorInfo.insertEmphasisText(_item.getAuthorInfo());
                insertParargraph(_section, authorInfo);
            }

        }
    }

    private void createDateParagraph(final DatedItem _item, final Section _section) {
        Paragraph postDate = new Paragraph();
        DateFormat formatter = new SimpleDateFormat(POST_DATE_FORMAT);
        String dateString = formatter.format(_item.getDate());
        postDate.insertSimpleText(dateString);
        insertParargraph(_section, postDate);
        insertEmptyLine(_section);
    }

    private void insertEmptyLine(final Section _section) {
        try {
            _section.insertEmptyLine();
        } catch (Exception e) {
            this.log.error("Error while inserting empty line.", e);
        }
    }

    private void insertParargraph(final Section _section, final Paragraph _paragraph) {
        try {
            _section.insertParagraph(_paragraph);
        } catch (Exception e) {
            this.log.error("Error while inserting paragraph.", e);
        }
    }

    private Section makeSection() {
        return new Section(SECTION_ID_PREFIX + Long.toString(System.nanoTime()));
    }

    private String getBrief(final AtdcItem _item, final Section _converted) {
        StringBuffer result = new StringBuffer();

        result.append(_item.getAuthorNick());
        result.append(" : ");
        result.append(_converted.getBrief(BRIEF_TEXT_LEN));

        return TextTools.removeHtmlTags(result.toString());
    }


}
