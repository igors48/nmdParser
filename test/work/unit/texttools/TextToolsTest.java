package work.unit.texttools;

import junit.framework.TestCase;
import util.TextTools;

import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 04.11.2008
 */
public class TextToolsTest extends TestCase {

    public TextToolsTest(String s) {
        super(s);
    }

    public void testIntelliCut01() {
        String result = TextTools.intelliCut("12345", 5);

        assertEquals("12345", result);
    }

    public void testIntelliCut02() {
        String result = TextTools.intelliCut("12345 06", 10);

        assertEquals("12345 06", result);
    }

    public void testIntelliCut03() {
        String result = TextTools.intelliCut("12345 067890123456", 10);

        assertEquals("12345", result);
    }

    public void testIntelliCut04() {
        String result = TextTools.intelliCut("123456789012345 123456", 5);

        assertEquals("12345", result);
    }

    public void testIntelliCut05() {
        String result = TextTools.intelliCut("  123456789012345 123456  ", 5);

        assertEquals("12345", result);
    }

    // �������� � ������� �������

    public void testCapFirstNormal() {
        assertEquals("Qwerty", TextTools.capsFirst("qwerty"));

    }

    // �������� �� ������� ��� ������ � ��� �������

    public void testCapFirstCaps() {
        assertEquals("Qwerty", TextTools.capsFirst("Qwerty"));

    }

    // �������� �� ������� �� ������ �������

    public void testCapOneChar() {
        assertEquals("Q", TextTools.capsFirst("q"));

    }

    // ���� ������ � ������������

    public void testWeldWithDivider() {
        List<String> fixture = newArrayList();
        fixture.add("a");
        fixture.add("b");
        fixture.add("c");

        String result = TextTools.weld(fixture, "/");

        assertEquals("a/b/c", result);
    }

    // ���� ������ ��� �����������

    public void testWeldWithoutDivider() {
        List<String> fixture = newArrayList();
        fixture.add("a");
        fixture.add("b");
        fixture.add("c");

        String result = TextTools.weld(fixture, "");

        assertEquals("abc", result);
    }

    // smoke ���� ������������� ����������

    public void testSmokeEscapeAmpersand() {
        String result = TextTools.escapeAmpersands("asd&asd");

        assertEquals("asd&amp;asd", result);
    }

    // ���� - �������������� ��� ���������

    public void testEscapedAmpersandDoesntChanged() {
        String result = TextTools.escapeAmpersands("asd&amp;asd");

        assertEquals("asd&amp;asd", result);
    }

    // ���� - ������������� ���� ������

    public void testTwoAmpersands() {
        String result = TextTools.escapeAmpersands("asd&&asd");

        assertEquals("asd&amp;&amp;asd", result);
    }

    // ���� - ��������� � ������ ������

    public void testStartsWithAmpersand() {
        String result = TextTools.escapeAmpersands("&asd");

        assertEquals("&amp;asd", result);
    }

    // ���� - ��������� � ����� ������

    public void testEndsWithAmpersand() {
        String result = TextTools.escapeAmpersands("asd&");

        assertEquals("asd&amp;", result);
    }

    //���� �������� �������������� �����������

    public void testRemoveEscapedAmps() {
        assertEquals("a&b", TextTools.removeEscapedAmpersands("a&amp;b"));
        assertEquals("a&b", TextTools.removeEscapedAmpersands("a&AMP;b"));
    }
}
