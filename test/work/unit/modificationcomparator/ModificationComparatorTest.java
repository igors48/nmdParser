package work.unit.modificationcomparator;

import dated.item.modification.Modification;
import dated.item.modification.ModificationComparator;
import junit.framework.TestCase;
import timeservice.StillTimeService;

/**
 * @author Igor Usenko
 *         Date: 15.10.2009
 */
public class ModificationComparatorTest extends TestCase {

    public ModificationComparatorTest(final String _s) {
        super(_s);
    }

    // первый меньше по дате

    public void testFirstLesserByDate() {
        StillTimeService timeService = new StillTimeService();
        Modification modification01 = new Modification(timeService.getCurrentDate(), "url01");
        timeService.changeSecond(1);
        Modification modification02 = new Modification(timeService.getCurrentDate(), "url01");

        ModificationComparator comparator = new ModificationComparator();

        assertEquals(-1, comparator.compare(modification01, modification02));
    }

    // первый больше по дате

    public void testFirstGreaterByDate() {
        StillTimeService timeService = new StillTimeService();
        Modification modification01 = new Modification(timeService.getCurrentDate(), "url01");
        timeService.changeSecond(-1);
        Modification modification02 = new Modification(timeService.getCurrentDate(), "url01");

        ModificationComparator comparator = new ModificationComparator();

        assertEquals(1, comparator.compare(modification01, modification02));
    }

    // оба по дате равны, но первый больше по урлу

    public void testDateEqualsFirstGreaterByUrl() {
        StillTimeService timeService = new StillTimeService();
        Modification modification01 = new Modification(timeService.getCurrentDate(), "url01");
        Modification modification02 = new Modification(timeService.getCurrentDate(), "url00");

        ModificationComparator comparator = new ModificationComparator();

        assertEquals(1, comparator.compare(modification01, modification02));
    }

    // оба по дате равны, но первый меньше по урлу

    public void testDateEqualsFirstLesserByUrl() {
        StillTimeService timeService = new StillTimeService();
        Modification modification01 = new Modification(timeService.getCurrentDate(), "url00");
        Modification modification02 = new Modification(timeService.getCurrentDate(), "url01");

        ModificationComparator comparator = new ModificationComparator();

        assertEquals(-1, comparator.compare(modification01, modification02));
    }

    // оба абсолютно равны

    public void testEquals() {
        StillTimeService timeService = new StillTimeService();
        Modification modification01 = new Modification(timeService.getCurrentDate(), "url00");
        Modification modification02 = new Modification(timeService.getCurrentDate(), "url00");

        ModificationComparator comparator = new ModificationComparator();

        assertEquals(0, comparator.compare(modification01, modification02));
    }
}
