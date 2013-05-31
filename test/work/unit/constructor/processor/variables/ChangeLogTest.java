package work.unit.constructor.processor.variables;

import junit.framework.TestCase;
import variables.ChangeLog;

/**
 * @author Igor Usenko
 *         Date: 19.08.2009
 */
public class ChangeLogTest extends TestCase {

    public ChangeLogTest(final String _s) {
        super(_s);
    }

    // первоначальный тест

    public void testSmoke() {
        ChangeLog changeLog = new ChangeLog();

        changeLog.add("name", 5);

        assertEquals(1, changeLog.size());
        assertTrue(changeLog.isChanged("name", 5));
    }

    // тест на немодифицированную

    public void testNotModified() {
        ChangeLog changeLog = new ChangeLog();

        changeLog.add("name", 5);

        assertEquals(1, changeLog.size());
        assertFalse(changeLog.isChanged("name", 8));
    }

    // тест на отсутствие дублей

    public void testNoDuplicates() {
        ChangeLog changeLog = new ChangeLog();

        changeLog.add("name", 5);
        changeLog.add("name", 5);
        changeLog.add("name", 8);
        changeLog.add("name", 5);

        assertEquals(2, changeLog.size());
        assertTrue(changeLog.isChanged("name", 5));
        assertTrue(changeLog.isChanged("name", 8));
    }

    // тест очистки

    public void testReset() {
        ChangeLog changeLog = new ChangeLog();

        changeLog.add("name", 5);
        changeLog.add("name", 5);
        changeLog.add("name", 8);
        changeLog.add("name", 5);

        changeLog.reset();

        assertEquals(0, changeLog.size());
        assertFalse(changeLog.isChanged("name", 5));
        assertFalse(changeLog.isChanged("name", 8));
    }

}
