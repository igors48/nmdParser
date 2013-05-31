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

    // ������ ������ �� ����

    public void testFirstLesserByDate() {
        StillTimeService timeService = new StillTimeService();
        Modification modification01 = new Modification(timeService.getCurrentDate(), "url01");
        timeService.changeSecond(1);
        Modification modification02 = new Modification(timeService.getCurrentDate(), "url01");

        ModificationComparator comparator = new ModificationComparator();

        assertEquals(-1, comparator.compare(modification01, modification02));
    }

    // ������ ������ �� ����

    public void testFirstGreaterByDate() {
        StillTimeService timeService = new StillTimeService();
        Modification modification01 = new Modification(timeService.getCurrentDate(), "url01");
        timeService.changeSecond(-1);
        Modification modification02 = new Modification(timeService.getCurrentDate(), "url01");

        ModificationComparator comparator = new ModificationComparator();

        assertEquals(1, comparator.compare(modification01, modification02));
    }

    // ��� �� ���� �����, �� ������ ������ �� ����

    public void testDateEqualsFirstGreaterByUrl() {
        StillTimeService timeService = new StillTimeService();
        Modification modification01 = new Modification(timeService.getCurrentDate(), "url01");
        Modification modification02 = new Modification(timeService.getCurrentDate(), "url00");

        ModificationComparator comparator = new ModificationComparator();

        assertEquals(1, comparator.compare(modification01, modification02));
    }

    // ��� �� ���� �����, �� ������ ������ �� ����

    public void testDateEqualsFirstLesserByUrl() {
        StillTimeService timeService = new StillTimeService();
        Modification modification01 = new Modification(timeService.getCurrentDate(), "url00");
        Modification modification02 = new Modification(timeService.getCurrentDate(), "url01");

        ModificationComparator comparator = new ModificationComparator();

        assertEquals(-1, comparator.compare(modification01, modification02));
    }

    // ��� ��������� �����

    public void testEquals() {
        StillTimeService timeService = new StillTimeService();
        Modification modification01 = new Modification(timeService.getCurrentDate(), "url00");
        Modification modification02 = new Modification(timeService.getCurrentDate(), "url00");

        ModificationComparator comparator = new ModificationComparator();

        assertEquals(0, comparator.compare(modification01, modification02));
    }
}
