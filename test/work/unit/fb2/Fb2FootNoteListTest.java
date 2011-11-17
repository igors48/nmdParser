package work.unit.fb2;

import converter.format.fb2.footnotes.Fb2FootNoteList;
import junit.framework.TestCase;

/**
 * @author Igor Usenko
 *         Date: 15.09.2009
 */
public class Fb2FootNoteListTest extends TestCase {

    public Fb2FootNoteListTest(final String _s) {
        super(_s);
    }

    // первоначальный тест

    public void testSmoke() {
        Fb2FootNoteList list = new Fb2FootNoteList();

        String tag01 = list.appendFootNote("first");
        String tag02 = list.appendFootNote("second");

        assertNotNull(tag01);
        assertNotNull(tag02);

        assertFalse(tag01.equals(tag02));
    }

    // тест одинакового тега на одинаковые футноты

    public void testSame() {
        Fb2FootNoteList list = new Fb2FootNoteList();

        String tag01 = list.appendFootNote("same");
        String tag02 = list.appendFootNote("sAme");

        assertTrue(tag01.equals(tag02));
    }
}
