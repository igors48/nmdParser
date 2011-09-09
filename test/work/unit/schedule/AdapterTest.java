package work.unit.schedule;

import junit.framework.TestCase;
import app.iui.schedule.Item;
import app.iui.schedule.Period;
import app.iui.schedule.Schedule;
import app.iui.schedule.ScheduledItems;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Igor Usenko
 *         Date: 01.05.2011
 */
public class AdapterTest extends TestCase {
   
    public AdapterTest(final String _s) {
        super(_s);
    }

    public void testSmoke() {
        final Schedule schedule = ScheduleTestUtils.createSchedule(
                ScheduleTestUtils.createItem("w1", "f1", Period.HALF_WEEKLY, 0),
                ScheduleTestUtils.createItem("w1", "f2", Period.HALF_WEEKLY, 5)
        );

        ScheduledItems scheduled = schedule.getItemsForUpdate(Period.WEEKLY.periodInMs);

        assertEquals(2, scheduled.size());
    }

    public void testSetUpdateTimeForUpdatedItemInSchedule() {
        final Item first = ScheduleTestUtils.createItem("w1", "f1", Period.HALF_WEEKLY, 0);
        final Item second = ScheduleTestUtils.createItem("w1", "f2", Period.HALF_WEEKLY, 5);

        final Schedule schedule = ScheduleTestUtils.createSchedule(first, second);

        schedule.itemUpdated(second, 1000000);
        
        ScheduledItems scheduled = schedule.getItemsForUpdate(900000 + Period.HALF_WEEKLY.periodInMs);

        assertEquals(1, scheduled.size());

        final Item[] itemsArray = scheduled.getItemsFromWorkspace("w1").toArray(new Item[1]);

        assertEquals(first, itemsArray[0]);
    }
}
