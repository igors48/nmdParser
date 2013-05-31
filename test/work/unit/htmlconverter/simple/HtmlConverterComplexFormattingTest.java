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
public class HtmlConverterComplexFormattingTest extends TestCase {

    private static final String TEST_PATTERN_01 = "abc<b>def</b>ghi";
    private static final String TEST_PATTERN_02 = "abc<i>def</i>ghi";
    private static final String TEST_PATTERN_03 = "<b>abc</b><i>def</i>ghi";
    private static final String TEST_PATTERN_04 = "<b>abc<i>def</i>ghi</b>";
    private static final String TEST_PATTERN_05 = "<b>abc<i>def</i>ghi";
    private static final String TEST_PATTERN_06 = "<b>abc<i>defghi";
    private static final String TEST_PATTERN_07 = "qwe<b>abc<i>def</i>ghi</b>rty";
    private static final String TEST_PATTERN_08 = "qwe<b>abc<i>def</i>ghi</b>rty<i>iop</i>";

    public HtmlConverterComplexFormattingTest(String s) {
        super(s);
    }

    public void testPattern01() throws Converter.ConverterException {
        List<Section> result = getResult(TEST_PATTERN_01);

        assertBaseStructureValid(result);

        List<FlowTextType> snapshot = getTypesSnapshot(getParagraph(result, 0));

        assertSnapshotEquals(new FlowTextType[]{
                FlowTextType.SIMPLE_TEXT,
                FlowTextType.STRONG_TEXT,
                FlowTextType.SIMPLE_TEXT},
                snapshot);
    }

    public void testPattern02() throws Converter.ConverterException {
        List<Section> result = getResult(TEST_PATTERN_02);

        assertBaseStructureValid(result);

        List<FlowTextType> snapshot = getTypesSnapshot(getParagraph(result, 0));

        assertSnapshotEquals(new FlowTextType[]{
                FlowTextType.SIMPLE_TEXT,
                FlowTextType.EMPHASIS_TEXT,
                FlowTextType.SIMPLE_TEXT},
                snapshot);
    }

    public void testPattern03() throws Converter.ConverterException {
        List<Section> result = getResult(TEST_PATTERN_03);

        assertBaseStructureValid(result);

        List<FlowTextType> snapshot = getTypesSnapshot(getParagraph(result, 0));

        assertSnapshotEquals(new FlowTextType[]{
                FlowTextType.STRONG_TEXT,
                FlowTextType.EMPHASIS_TEXT,
                FlowTextType.SIMPLE_TEXT},
                snapshot);
    }

    public void testPattern04() throws Converter.ConverterException {
        List<Section> result = getResult(TEST_PATTERN_04);

        assertBaseStructureValid(result);

        List<FlowTextType> snapshot = getTypesSnapshot(getParagraph(result, 0));

        assertSnapshotEquals(new FlowTextType[]{
                FlowTextType.STRONG_TEXT,
                FlowTextType.EMPHASIS_TEXT,
                FlowTextType.STRONG_TEXT},
                snapshot);
    }

    public void testPattern05() throws Converter.ConverterException {
        List<Section> result = getResult(TEST_PATTERN_05);

        assertBaseStructureValid(result);

        List<FlowTextType> snapshot = getTypesSnapshot(getParagraph(result, 0));

        assertSnapshotEquals(new FlowTextType[]{
                FlowTextType.STRONG_TEXT,
                FlowTextType.EMPHASIS_TEXT,
                FlowTextType.STRONG_TEXT},
                snapshot);
    }

    public void testPattern06() throws Converter.ConverterException {
        List<Section> result = getResult(TEST_PATTERN_06);

        assertBaseStructureValid(result);

        List<FlowTextType> snapshot = getTypesSnapshot(getParagraph(result, 0));

        assertSnapshotEquals(new FlowTextType[]{
                FlowTextType.STRONG_TEXT,
                FlowTextType.EMPHASIS_TEXT},
                snapshot);
    }

    public void testPattern07() throws Converter.ConverterException {
        List<Section> result = getResult(TEST_PATTERN_07);

        assertBaseStructureValid(result);

        List<FlowTextType> snapshot = getTypesSnapshot(getParagraph(result, 0));

        assertSnapshotEquals(new FlowTextType[]{
                FlowTextType.SIMPLE_TEXT,
                FlowTextType.STRONG_TEXT,
                FlowTextType.EMPHASIS_TEXT,
                FlowTextType.STRONG_TEXT,
                FlowTextType.SIMPLE_TEXT},
                snapshot);
    }

    public void testPattern08() throws Converter.ConverterException {
        List<Section> result = getResult(TEST_PATTERN_08);

        assertBaseStructureValid(result);

        List<FlowTextType> snapshot = getTypesSnapshot(getParagraph(result, 0));

        assertSnapshotEquals(new FlowTextType[]{
                FlowTextType.SIMPLE_TEXT,
                FlowTextType.STRONG_TEXT,
                FlowTextType.EMPHASIS_TEXT,
                FlowTextType.STRONG_TEXT,
                FlowTextType.SIMPLE_TEXT,
                FlowTextType.EMPHASIS_TEXT},
                snapshot);
    }

    private void assertBaseStructureValid(List<Section> _sections) {
        assertEquals(1, _sections.size());
        assertEquals(FlowTextType.SECTION, _sections.get(0).getType());
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
