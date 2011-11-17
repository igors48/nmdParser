package work.unit.constructor.processor.variables;

import junit.framework.TestCase;
import variables.VariableIterator;
import variables.Variables;

/**
 * @author Igor Usenko
 *         Date: 19.07.2009
 */
public class CommonVariableIteratorTest extends TestCase {

    public CommonVariableIteratorTest(final String _s) {
        super(_s);
    }

    // первоначальный тест

    public void testSmoke() {
        Variables variables = new Variables();
        variables.put("name", "value");

        VariableIterator iterator = variables.getIterator("name");

        assertEquals(1, iterator.getSize());
        assertTrue(iterator.hasNext());
        assertEquals(0, iterator.getIndex());
        assertEquals(0, iterator.getKey());
        assertEquals("value", iterator.next());
        assertFalse(iterator.hasNext());
        assertEquals(1, iterator.getIndex());
    }

    // тест "разбросанной" переменной

    public void testScattered() {
        Variables variables = new Variables();
        variables.put("name", 0, "value0");
        variables.put("name", 56, "value56");
        variables.put("name", 1000, "value1000");

        VariableIterator iterator = variables.getIterator("name");

        assertEquals(3, iterator.getSize());

        assertTrue(iterator.hasNext());
        assertEquals(0, iterator.getIndex());
        assertEquals(0, iterator.getKey());
        assertEquals("value0", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.getIndex());
        assertEquals(56, iterator.getKey());
        assertEquals("value56", iterator.next());

        assertTrue(iterator.hasNext());
        assertEquals(2, iterator.getIndex());
        assertEquals(1000, iterator.getKey());
        assertEquals("value1000", iterator.next());

        assertFalse(iterator.hasNext());
    }
}
