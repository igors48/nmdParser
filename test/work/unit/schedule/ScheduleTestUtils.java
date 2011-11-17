package work.unit.schedule;

import app.iui.schedule.Item;
import app.iui.schedule.Period;
import app.iui.schedule.Schedule;
import util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 07.05.2011
 */
public final class ScheduleTestUtils {

    public static Schedule createSchedule(final Item _first, final Item _second) {
        Assert.notNull(_first, "First item is null");
        Assert.notNull(_second, "Secondt item is null");

        return new Schedule(createItemList(_first, _second));
    }

    public static List<Item> createItemList(Item _first, Item _second) {
        Assert.notNull(_first, "First item is null");
        Assert.notNull(_second, "Secondt item is null");

        final List<Item> items = new ArrayList<Item>();

        items.add(_first);
        items.add(_second);

        return items;
    }

    public static Item createItem(final String _workspace, final String _feeder, final Period _period, final long _lastUpdate) {
        Assert.isValidString(_workspace, "Workspace name is not valid");
        Assert.isValidString(_feeder, "Feeder name is not valid");
        Assert.notNull(_period, "Period is null");
        Assert.greaterOrEqual(_lastUpdate, Item.NEVER_UPDATED_BEFORE, "Last update < Item.NEVER_UPDATED_BEFORE");

        final Item result = new Item(_workspace, _feeder, _period);

        result.setLastUpdate(_lastUpdate);

        return result;
    }

    private ScheduleTestUtils() {
        // empty
    }
}
