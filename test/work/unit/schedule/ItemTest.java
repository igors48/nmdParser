package work.unit.schedule;

import app.iui.schedule.Item;
import app.iui.schedule.Period;
import junit.framework.TestCase;

/**
 * @author Igor Usenko
 *         Date: 07.05.2011
 */
public class ItemTest extends TestCase {

    private static final long START_TIME = 0;
    private static final long BIG_TIME = 100500;
    private static final long MIDDLE_TIME = 10050;

    public ItemTest(final String _s) {
        super(_s);
    }

    public void testNeverUpdatedItemWithPeriodAlwaysMustBeUpdated() {
        final Item item = ScheduleTestUtils.createItem("w", "f", Period.ALWAYS, Item.NEVER_UPDATED_BEFORE);

        assertTrue(item.needsUpdate(START_TIME));
        assertTrue(item.needsUpdate(BIG_TIME));
    }

    public void testNeverUpdatedItemWithPeriodHalfWeeklyMustBeUpdated() {
        final Item item = ScheduleTestUtils.createItem("w", "f", Period.HALF_WEEKLY, Item.NEVER_UPDATED_BEFORE);

        assertTrue(item.needsUpdate(START_TIME));
        assertTrue(item.needsUpdate(BIG_TIME));
    }

    public void testNeverUpdatedItemWithPeriodWeeklyMustBeUpdated() {
        final Item item = ScheduleTestUtils.createItem("w", "f", Period.WEEKLY, Item.NEVER_UPDATED_BEFORE);

        assertTrue(item.needsUpdate(START_TIME));
        assertTrue(item.needsUpdate(BIG_TIME));
    }

    public void testNeverUpdatedItemWithPeriodHalfMonthlyMustBeUpdated() {
        final Item item = ScheduleTestUtils.createItem("w", "f", Period.HALF_MONTHLY, Item.NEVER_UPDATED_BEFORE);

        assertTrue(item.needsUpdate(START_TIME));
        assertTrue(item.needsUpdate(BIG_TIME));
    }

    public void testNeverUpdatedItemWithPeriodMonthlyMustBeUpdated() {
        final Item item = ScheduleTestUtils.createItem("w", "f", Period.MONTHLY, Item.NEVER_UPDATED_BEFORE);

        assertTrue(item.needsUpdate(START_TIME));
        assertTrue(item.needsUpdate(BIG_TIME));
    }

    public void testItemWithPeriodAlwaysMustBeAlwaysUpdated() {
        final Item item = ScheduleTestUtils.createItem("w", "f", Period.ALWAYS, MIDDLE_TIME);

        assertTrue(item.needsUpdate(START_TIME));
        assertTrue(item.needsUpdate(BIG_TIME));
    }

    public void testItemWithPeriodHalfWeekly() {
        final Item item = ScheduleTestUtils.createItem("w", "f", Period.HALF_WEEKLY, MIDDLE_TIME);

        assertFalse(item.needsUpdate(START_TIME));
        assertTrue(item.needsUpdate(MIDDLE_TIME + Period.HALF_WEEKLY.periodInMs));
    }

    public void testItemWithPeriodWeekly() {
        final Item item = ScheduleTestUtils.createItem("w", "f", Period.WEEKLY, MIDDLE_TIME);

        assertFalse(item.needsUpdate(START_TIME));
        assertTrue(item.needsUpdate(MIDDLE_TIME + Period.WEEKLY.periodInMs));
    }

    public void testItemWithPeriodHalfMonthly() {
        final Item item = ScheduleTestUtils.createItem("w", "f", Period.HALF_MONTHLY, MIDDLE_TIME);

        assertFalse(item.needsUpdate(START_TIME));
        assertTrue(item.needsUpdate(MIDDLE_TIME + Period.HALF_MONTHLY.periodInMs));
    }

    public void testItemWithPeriodMonthly() {
        final Item item = ScheduleTestUtils.createItem("w", "f", Period.MONTHLY, MIDDLE_TIME);

        assertFalse(item.needsUpdate(START_TIME));
        assertTrue(item.needsUpdate(MIDDLE_TIME + Period.MONTHLY.periodInMs));
    }
}
