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
public class HtmlConverterComplexManyParagraphFormattingTest extends TestCase {

    private static final String TEST_PATTERN_01 = "<blockquote>цитата:<br><b>EvgenyS</b>:</blockquote>Неправда";

    /**
     * Посвящается неправильной отработке форматирования тегов h3.../h3 (болд перелез на прарграф)
     * http://www.ixbt.com/mobile/southwing-sh505.shtml
     */
    private static final String TEST_PATTERN_02 = "<h2>Дизайн</h2><p>Внешность </p>";

    public HtmlConverterComplexManyParagraphFormattingTest(String s) {
        super(s);
    }

    public void testPattern01() throws Converter.ConverterException {
        List<Section> result = getResult(TEST_PATTERN_01);

        assertBaseStructureValid(result);

        List<FlowTextType> snapshot01 = getTypesSnapshot(getParagraph(result, 0));
        assertSnapshotEquals(new FlowTextType[]{
                FlowTextType.EMPHASIS_TEXT},
                snapshot01);

        List<FlowTextType> snapshot03 = getTypesSnapshot(getParagraph(result, 1));
        assertSnapshotEquals(new FlowTextType[]{
                FlowTextType.STRONG_TEXT,
                FlowTextType.EMPHASIS_TEXT,
                FlowTextType.SIMPLE_TEXT},
                snapshot03);

    }

    public void testPattern02() throws Converter.ConverterException {
        List<Section> result = getResult(TEST_PATTERN_02);

        assertEquals(1, result.size());
        assertEquals(FlowTextType.SECTION, result.get(0).getType());
        assertEquals(3, result.get(0).getContent().size());

        List<FlowTextType> snapshot01 = getTypesSnapshot(getParagraph(result, 1));
        assertSnapshotEquals(new FlowTextType[]{
                FlowTextType.STRONG_TEXT},
                snapshot01);

        List<FlowTextType> snapshot02 = getTypesSnapshot(getParagraph(result, 2));
        assertSnapshotEquals(new FlowTextType[]{
                FlowTextType.SIMPLE_TEXT},
                snapshot02);

    }

    private void assertBaseStructureValid(List<Section> _sections) {
        assertEquals(1, _sections.size());
        assertEquals(FlowTextType.SECTION, _sections.get(0).getType());
        assertEquals(2, _sections.get(0).getContent().size());
    }

    private void assertSnapshotEquals(FlowTextType[] _expected, List<FlowTextType> _snapshot) {

        if (_expected.length != _snapshot.size()) {
            fail("Length mismatch expected : " + _expected.length + " actual : " + _snapshot.size());
        }

        for (int index = 0; index < _expected.length; ++index) {

            if (_expected[index] != _snapshot.get(index)) {
                fail("Formatting mismatch at index : " + index + " expected : " + _expected[index] + " actual : " + _snapshot.get(index));
            }
        }
    }

}
