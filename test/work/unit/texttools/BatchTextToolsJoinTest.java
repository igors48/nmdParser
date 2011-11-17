package work.unit.texttools;

import junit.framework.TestCase;
import util.batchtext.BatchTextTools;
import util.batchtext.Boundary;

/**
 * @author Igor Usenko
 *         Date: 23.05.2009
 */
public class BatchTextToolsJoinTest extends TestCase {

    public BatchTextToolsJoinTest(final String _s) {
        super(_s);
    }

    // первоначальный тест

    public void testSmoke() {
        Boundary first = new Boundary(2, 4);
        Boundary second = new Boundary(3, 6);

        Boundary result = BatchTextTools.join(first, second);

        assertEquals(2, result.getStart());
        assertEquals(6, result.getEnd());
    }

    // тест не пересекающихся

    public void testNonIntercepted() {
        Boundary first = new Boundary(2, 4);
        Boundary second = new Boundary(8, 10);

        Boundary result = BatchTextTools.join(first, second);

        assertNull(result);
    }

    // тест на поглощение

    public void testConsumed() {
        Boundary first = new Boundary(4, 6);
        Boundary second = new Boundary(2, 10);

        Boundary result = BatchTextTools.join(first, second);

        assertEquals(2, result.getStart());
        assertEquals(10, result.getEnd());
    }
}
