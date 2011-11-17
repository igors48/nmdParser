package work.unit.htmlconverter.simple;

import flowtext.FlowTextType;
import flowtext.Section;
import html.Converter;
import junit.framework.TestCase;

import java.util.List;

import static work.testutil.HtmlConverterTestUtils.*;

/**
 * @author Igor Usenko
 *         Date: 25.01.2009
 */
public class HtmlConverterSimpleFormattingTest extends TestCase {
    private static final String ABC = "abc";
    private static final String ABC_STRONG = "<b>abc</B>";
    private static final String ABC_EMPHASIS = "<I>abc</i>";

    public HtmlConverterSimpleFormattingTest(String s) {
        super(s);
    }

    public void testOneFormatStrong() throws Converter.ConverterException {
        List<Section> result = getResult(ABC_STRONG);

        assertBaseStructureValid(result);
        assertSimpleParagraphValid(result, FlowTextType.STRONG_TEXT, ABC);
    }

    public void testOneFormatEmphasis() throws Converter.ConverterException {
        List<Section> result = getResult(ABC_EMPHASIS);

        assertBaseStructureValid(result);
        assertSimpleParagraphValid(result, FlowTextType.EMPHASIS_TEXT, ABC);
    }

    public void testOneFormatSimple() throws Converter.ConverterException {
        List<Section> result = getResult(ABC);

        assertBaseStructureValid(result);
        assertSimpleParagraphValid(result, FlowTextType.SIMPLE_TEXT, ABC);
    }

    private void assertSimpleParagraphValid(List<Section> _sections, FlowTextType _type, String _text) {
        assertEquals(FlowTextType.PARAGRAPH, _sections.get(0).getContent().get(0).getType());
        assertEquals(1, getParagraph(_sections, 0).getContent().size());
        assertEquals(_type, getObject(_sections, 0, 0).getType());
        assertEquals(_text, getTextObject(_sections, 0, 0).getText().trim());
    }

    private void assertBaseStructureValid(List<Section> _result) {
        assertEquals(1, _result.size());
        assertEquals(FlowTextType.SECTION, _result.get(0).getType());
    }
}
