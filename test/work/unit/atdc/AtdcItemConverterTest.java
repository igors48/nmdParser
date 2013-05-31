package work.unit.atdc;

import dated.DatedItemConverter;
import dated.DatedItemConverterContext;
import dated.item.atdc.AtdcItem;
import flowtext.*;
import flowtext.resource.Resource;
import http.Data;
import junit.framework.TestCase;
import timeservice.StandardTimeService;
import timeservice.TimeService;
import work.testutil.AtdcTestUtils;
import work.testutil.FlowTextDocumentTestUtils;

/**
 * @author Igor Usenko
 *         Date: 27.12.2008
 */
public class AtdcItemConverterTest extends TestCase {

    private static final int ITEM_NUMBER = 48;

    public AtdcItemConverterTest(String s) {
        super(s);
    }

    public void testAtdcFullItem() throws Data.DataException {
        TimeService timeService = new StandardTimeService();
        AtdcItem atdcItem = AtdcTestUtils.getAtdcFullItem(timeService, ITEM_NUMBER);
        DatedItemConverter converter = atdcItem.getSectionConverter();

        Section section = converter.convert(atdcItem, new DatedItemConverterContext(false, true));

        assertTrue(isValidSectionStructure(section));
        assertTrue(isValidOriginalTitle(section.getTitle(), atdcItem));
        assertTrue(isValidAuthorAvatar(section, atdcItem));
        assertTrue(isValidAuthorNick(section, atdcItem));
        assertTrue(isValidAuthorInfo(section, atdcItem));
        assertTrue(isEmptyLineExists(section));
        assertTrue(isValidContent(section, atdcItem));
    }

    public void testAtdcItemNoTitle() throws Data.DataException {
        TimeService timeService = new StandardTimeService();
        AtdcItem atdcItem = AtdcTestUtils.getAtdcNoTitleItem(timeService, ITEM_NUMBER);
        DatedItemConverter converter = atdcItem.getSectionConverter();

        Section section = converter.convert(atdcItem, new DatedItemConverterContext(false, true));

        assertTrue(isValidSectionStructure(section));
        assertTrue(isValidConstructedTitle(section.getTitle(), atdcItem));
        assertTrue(isValidAuthorAvatar(section, atdcItem));
        assertTrue(isValidAuthorNick(section, atdcItem));
        assertTrue(isValidAuthorInfo(section, atdcItem));
        assertTrue(isEmptyLineExists(section));
        assertTrue(isValidContent(section, atdcItem));
    }

    private boolean isValidAuthorAvatar(Section _section, AtdcItem _item) {
        Resource resource = (Resource) FlowTextDocumentTestUtils.getObject(_section, 0, 0);
        String avatar = resource.getAddress();

        return ((_item.getAuthorAvatar()).equalsIgnoreCase(avatar));
    }

    private boolean isValidAuthorNick(Section _section, AtdcItem _item) {
        return (_item.getAuthorNick()).equalsIgnoreCase(FlowTextDocumentTestUtils.getString(_section, 1, 0).trim());
    }

    private boolean isValidAuthorInfo(Section _section, AtdcItem _item) {
        return (_item.getAuthorInfo()).equalsIgnoreCase(FlowTextDocumentTestUtils.getString(_section, 2, 0).trim());
    }

    private boolean isValidContent(Section _section, AtdcItem _item) throws Data.DataException {
        String content = AtdcTestUtils.getContentString(_item);
        String text = FlowTextDocumentTestUtils.getString(_section, 5, 0).trim();

        return content.equalsIgnoreCase(text);
    }

    private boolean isEmptyLineExists(Section _section) {
        FlowTextObject object = FlowTextDocumentTestUtils.getObject(_section, 4);

        return FlowTextType.EMPTY_LINE == object.getType();
    }

    private boolean isValidOriginalTitle(Title _title, AtdcItem _item) {
        boolean result = true;

        if (!isValidTitleStructure(_title)) {
            result = false;
        } else if (!isValidOriginalTitleContent(_title, _item)) {
            result = false;
        }

        return result;
    }

    private boolean isValidConstructedTitle(Title _title, AtdcItem _item) throws Data.DataException {
        boolean result = true;

        if (!isValidTitleStructure(_title)) {
            result = false;
        } else if (!isValidConstructedTitleContent(_title, _item)) {
            result = false;
        }

        return result;
    }

    private boolean isValidOriginalTitleContent(Title _title, AtdcItem _item) {
        return (_item.getTitle()).equalsIgnoreCase(((FlowTextObjectText) ((Paragraph) _title.getContent().get(0)).getContent().get(0)).getText());
    }

    private boolean isValidConstructedTitleContent(Title _title, AtdcItem _item) throws Data.DataException {
        String title = ((FlowTextObjectText) ((Paragraph) _title.getContent().get(0)).getContent().get(0)).getText();
        String content = AtdcTestUtils.getContentString(_item);

        return (title.contains(_item.getAuthorNick())) && (title.contains(content));
    }

    private boolean isValidTitleStructure(Title _title) {
        boolean result = true;

        if (1 != _title.getContent().size()) {
            result = false;
        } else if (FlowTextType.PARAGRAPH != _title.getContent().get(0).getType()) {
            result = false;
        } else if (1 != ((Paragraph) _title.getContent().get(0)).getContent().size()) {
            result = false;
        } else if (FlowTextType.STRONG_TEXT != ((Paragraph) _title.getContent().get(0)).getContent().get(0).getType()) {
            result = false;
        }

        return result;
    }

    private boolean isValidSectionStructure(Section _section) {
        boolean result = true;

        if (_section.getTitle() == null) {
            result = false;
        } else if (6 != _section.getContent().size()) {
            result = false;
        } else if (FlowTextType.PARAGRAPH != FlowTextDocumentTestUtils.getObject(_section, 0).getType()) {
            result = false;
        } else if (FlowTextType.PARAGRAPH != FlowTextDocumentTestUtils.getObject(_section, 1).getType()) {
            result = false;
        } else if (FlowTextType.PARAGRAPH != FlowTextDocumentTestUtils.getObject(_section, 2).getType()) {
            result = false;
        } else if (FlowTextType.PARAGRAPH != FlowTextDocumentTestUtils.getObject(_section, 3).getType()) {
            result = false;
        } else if (FlowTextType.EMPTY_LINE != FlowTextDocumentTestUtils.getObject(_section, 4).getType()) {
            result = false;
        } else if (FlowTextType.PARAGRAPH != FlowTextDocumentTestUtils.getObject(_section, 5).getType()) {
            result = false;
        }

        return result;
    }
}
