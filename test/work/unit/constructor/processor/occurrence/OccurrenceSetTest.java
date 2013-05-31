package work.unit.constructor.processor.occurrence;

import constructor.objects.processor.OccurrenceSet;
import junit.framework.TestCase;

/**
 * @author Igor Usenko
 *         Date: 22.05.2009
 */
public class OccurrenceSetTest extends TestCase {

    public OccurrenceSetTest(final String _s) {
        super(_s);
    }

    // первоначальный тест

    public void testSmoke() {
        OccurrenceSet occurrenceSet = new OccurrenceSet();

        assertTrue(occurrenceSet.acceptable(0));
    }

    // тест на неприемлемость ненулевого при пустом множестве

    public void testNonZeroSetEmpty() {
        OccurrenceSet occurrenceSet = new OccurrenceSet();

        assertFalse(occurrenceSet.acceptable(48));
    }

    // тест списка

    public void testList() {
        OccurrenceSet occurrenceSet = new OccurrenceSet();
        occurrenceSet.add(0);
        occurrenceSet.add(24);
        occurrenceSet.add(56);
        occurrenceSet.add(24);

        assertTrue(occurrenceSet.acceptable(0));
        assertTrue(occurrenceSet.acceptable(24));
        assertTrue(occurrenceSet.acceptable(56));
        assertFalse(occurrenceSet.acceptable(48));
    }

    // тест на то, что -1 перекрывает все ранее и позднее введенные значения

    public void testAllAcceptable() {
        OccurrenceSet occurrenceSet = new OccurrenceSet();
        occurrenceSet.add(0);
        occurrenceSet.add(24);
        occurrenceSet.add(-1);
        occurrenceSet.add(56);
        occurrenceSet.add(24);

        assertTrue(occurrenceSet.acceptable(0));
        assertTrue(occurrenceSet.acceptable(24));
        assertTrue(occurrenceSet.acceptable(56));
        assertTrue(occurrenceSet.acceptable(48));
        assertTrue(occurrenceSet.acceptable(100));
        assertTrue(occurrenceSet.acceptable(1024));
        assertTrue(occurrenceSet.acceptable(1056));
        assertTrue(occurrenceSet.acceptable(1048));
    }
}
