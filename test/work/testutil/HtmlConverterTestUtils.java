package work.testutil;

import dated.item.atdc.HtmlContent;
import flowtext.*;
import html.Converter;
import html.parser.SimpleHtmlConverter;
import html.parser.SimpleHtmlConverterContext;
import util.Assert;

import java.util.List;

import static util.CollectionUtils.newArrayList;


/**
 * @author Igor Usenko
 *         Date: 25.01.2009
 */
public final class HtmlConverterTestUtils {

    private static final String FAKE_URL = "www.fake.com";

    public static List<FlowTextType> getTypesSnapshot(Paragraph _paragraph) {
        List<FlowTextType> result = newArrayList();

        for (FlowTextObject object : _paragraph.getContent()) {
            result.add(object.getType());
        }

        return result;
    }

    public static List<Section> getResult(String _content) throws Converter.ConverterException {
        Assert.isValidString(_content);

        HtmlContent content = createContent(_content);
        Converter converter = new SimpleHtmlConverter(new SimpleHtmlConverterContext(false));

        return converter.convert(content);
    }

    public static Paragraph getParagraph(List<Section> _sections, int _index) {
        Assert.notNull(_sections);

        return (Paragraph) getSectionObject(_sections, _index);
    }

    public static FlowTextObject getSectionObject(List<Section> _sections, int _index) {
        Assert.notNull(_sections);

        return _sections.get(0).getContent().get(_index);
    }

    public static HtmlContent createContent(String _data) {
        Assert.isValidString(_data);

        return new HtmlContent(FAKE_URL, _data, FAKE_URL);
    }

    public static FlowTextObject getObject(List<Section> _sections, int _paraIndex, int _objIndex) {
        Assert.notNull(_sections);

        return getParagraph(_sections, _paraIndex).getContent().get(_objIndex);
    }

    public static FlowTextObjectText getTextObject(List<Section> _sections, int _paraIndex, int _objIndex) {
        Assert.notNull(_sections);

        return (FlowTextObjectText) getObject(_sections, _paraIndex, _objIndex);
    }

    private HtmlConverterTestUtils() {
        // empty
    }
}
