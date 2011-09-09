package work.unit.schedule;

import junit.framework.TestCase;
import app.iui.schedule.ScheduledItems;
import app.iui.schedule.Item;
import app.iui.schedule.Period;

import java.util.Set;

/**
 * @author Igor Usenko
 *         Date: 07.05.2011
 */
public class ScheduledItemsTest extends TestCase {

    public ScheduledItemsTest(final String _s) {
        super(_s);
    }

    public void testGetWorkspaceList() {
        final ScheduledItems scheduledItems = createFixture();

        Set<String> workspaces = scheduledItems.getWorkspaces();

        assertEquals(2, workspaces.size());
        assertTrue(workspaces.contains("w1"));
        assertTrue(workspaces.contains("w2"));
    }

    public void testGetItemsForWorkspace() {
        final ScheduledItems scheduledItems = createFixture();

        Set<Item> items = scheduledItems.getItemsFromWorkspace("w1");

        assertEquals(1, items.size());

        final Item[] itemsArray = items.toArray(new Item[1]);

        assertEquals("f1", itemsArray[0].getEntity());
    }

    private ScheduledItems createFixture() {
        final Item first = ScheduleTestUtils.createItem("w1", "f1", Period.HALF_WEEKLY, 0);
        final Item second = ScheduleTestUtils.createItem("w2", "f2", Period.HALF_WEEKLY, 5);

        final ScheduledItems scheduledItems = new ScheduledItems(ScheduleTestUtils.createItemList(first, second));
        return scheduledItems;
    }
}
