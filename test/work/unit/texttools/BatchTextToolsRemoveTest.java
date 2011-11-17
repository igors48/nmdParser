package work.unit.texttools;

import junit.framework.TestCase;
import util.batchtext.BatchTextTools;
import util.batchtext.Boundary;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 24.05.2009
 */
public class BatchTextToolsRemoveTest extends TestCase {

    public BatchTextToolsRemoveTest(final String _s) {
        super(_s);
    }

    // первоначальный тест

    public void testSmoke() {
        List<Boundary> boundaries = new ArrayList<Boundary>();
        boundaries.add(new Boundary(5, 6));

        String result = BatchTextTools.remove("0123456789", boundaries);

        assertEquals("01234789", result);
    }

    // тест пустого списка диапазонов

    public void testEmpty() {
        List<Boundary> boundaries = new ArrayList<Boundary>();

        String result = BatchTextTools.remove("0123456789", boundaries);

        assertEquals("0123456789", result);
    }

    // тест с начала

    public void testAtStart() {
        List<Boundary> boundaries = new ArrayList<Boundary>();
        boundaries.add(new Boundary(0, 1));

        String result = BatchTextTools.remove("0123456789", boundaries);

        assertEquals("23456789", result);
    }

    // тест с конца

    public void testAtEnd() {
        List<Boundary> boundaries = new ArrayList<Boundary>();
        boundaries.add(new Boundary(9, 9));

        String result = BatchTextTools.remove("0123456789", boundaries);

        assertEquals("012345678", result);
    }

    // тест на один символ

    public void testSingle() {
        List<Boundary> boundaries = new ArrayList<Boundary>();
        boundaries.add(new Boundary(8, 8));

        String result = BatchTextTools.remove("0123456789", boundaries);

        assertEquals("012345679", result);
    }

    // тест на один символ в начале

    public void testSingleAtStart() {
        List<Boundary> boundaries = new ArrayList<Boundary>();
        boundaries.add(new Boundary(0, 0));

        String result = BatchTextTools.remove("0123456789", boundaries);

        assertEquals("123456789", result);
    }

    // тест на один символ в начале

    public void testSingleAtEnd() {
        List<Boundary> boundaries = new ArrayList<Boundary>();
        boundaries.add(new Boundary(9, 9));

        String result = BatchTextTools.remove("0123456789", boundaries);

        assertEquals("012345678", result);
    }

    // тест на составной диапазон

    public void testComposite() {
        List<Boundary> boundaries = new ArrayList<Boundary>();
        boundaries.add(new Boundary(1, 3));
        boundaries.add(new Boundary(2, 4));
        boundaries.add(new Boundary(6, 9));
        boundaries.add(new Boundary(1, 8));

        String result = BatchTextTools.remove("0123456789", boundaries);

        assertEquals("0", result);
    }

    // тест на два отдельных диапазона

    public void testSeparate() {
        List<Boundary> boundaries = new ArrayList<Boundary>();
        boundaries.add(new Boundary(1, 3));
        boundaries.add(new Boundary(6, 8));

        String result = BatchTextTools.remove("0123456789", boundaries);

        assertEquals("0459", result);
    }

}
