package work.unit.app.timetable;

import app.cli.timetable.TimeTable;
import junit.framework.TestCase;
import timeservice.StillTimeService;

/**
 * @author Igor Usenko
 *         Date: 30.04.2009
 */
public class TimeTableTest extends TestCase {

    public TimeTableTest(String _s) {
        super(_s);
    }

    // �������������� ����

    public void testSmoke() {
        StillTimeService timeService = new StillTimeService();
        TimeTable timeTable = new TimeTable(timeService);

        timeTable.add("1", 1, 1000);
        String current = timeTable.getCurrentId();

        assertEquals(1, timeTable.size());
        assertEquals("1", current);
    }

    // ���� �� ��������

    public void testRemove() {
        StillTimeService timeService = new StillTimeService();
        TimeTable timeTable = new TimeTable(timeService);

        timeTable.add("1", 1, 1000);
        timeTable.remove("1");

        assertEquals(0, timeTable.size());
    }

    // ���� �������� �������� ���� �� ���� ������� �� �����������

    public void testNoExecTimeout() {
        StillTimeService timeService = new StillTimeService();
        TimeTable timeTable = new TimeTable(timeService);

        timeTable.add("1", 1, 1000);

        assertEquals(0, timeTable.getTimeout());
    }

    // ���� �������� = -1 ���� ������� �����

    public void testEmptyQueueTimeout() {
        StillTimeService timeService = new StillTimeService();
        TimeTable timeTable = new TimeTable(timeService);

        assertEquals(-1, timeTable.getTimeout());
    }

    // ���� �������� ����� �������� �������

    public void testTouchTimeoutOneItem() {
        StillTimeService timeService = new StillTimeService();
        TimeTable timeTable = new TimeTable(timeService);

        timeTable.add("1", 2, 1000);
        timeTable.touch("1");

        assertEquals(1000, timeTable.getTimeout());
    }

    // ���� �������� �� ��������� �� ���� �������� ������

    public void testTouchTimeoutTwoItem() {
        StillTimeService timeService = new StillTimeService();
        TimeTable timeTable = new TimeTable(timeService);

        timeTable.add("1", 2, 2000);
        timeTable.add("2", 2, 3000);
        timeTable.touch("1");
        timeTable.touch("2");

        assertEquals(2000, timeTable.getTimeout());
    }

    // ���� �� �������������� �������

    public void testExpired() {
        StillTimeService timeService = new StillTimeService();
        TimeTable timeTable = new TimeTable(timeService);

        timeTable.add("1", 1, 2000);
        int result = timeTable.touch("1");

        assertEquals(0, timeTable.size());
        assertEquals(0, result);
    }
}
