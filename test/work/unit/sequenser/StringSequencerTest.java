package work.unit.sequenser;

import junit.framework.TestCase;
import util.sequense.StringSequencer;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 04.06.2009
 */
public class StringSequencerTest extends TestCase {

    public StringSequencerTest(final String _s) {
        super(_s);
    }

    // первоначальный тест

    public void testSmoke() {
        List<String> result = StringSequencer.getSequence("a*b", 0, 1, 1, 1, 0, "");

        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertEquals("a0b", result.get(0));
        assertEquals("a1b", result.get(1));
    }

    // тест - место вставки номеров отсутствует

    public void testNoWildcard() {
        List<String> result = StringSequencer.getSequence("ab", 0, 1, 1, 1, 0, "");

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("ab", result.get(0));
    }

    // тест - двухсимвольный паддинг с символом по умолчанию

    public void testTwoSymbolWildcard() {
        List<String> result = StringSequencer.getSequence("a*b", 0, 1, 1, 1, 2, "");

        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertEquals("a00b", result.get(0));
        assertEquals("a01b", result.get(1));
    }

    // тест - трехсимвольный паддинг с пробелом

    public void testThreeSpaceWildcard() {
        List<String> result = StringSequencer.getSequence("a*b", 0, 1, 1, 1, 3, " ");

        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertEquals("a  0b", result.get(0));
        assertEquals("a  1b", result.get(1));
    }

    // тест - отсутствия обрезки если длина превышает заданную

    public void testNoCrop() {
        List<String> result = StringSequencer.getSequence("a*b", 21, 22, 1, 1, 1, " ");

        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertEquals("a21b", result.get(0));
        assertEquals("a22b", result.get(1));
    }
}
