package app.iui.schedule;

import util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 01.05.2011
 */
public class Schedule implements ScheduleAdapter {

    public static final long WEEK_IN_MS = 604800000L;
    public static final long HALF_WEEK_IN_MS = 604800000L / 2;
    public static final long HALF_MONTH_IN_MS = 604800000L * 2;
    public static final long MONTH_IN_MS = 604800000L * 4;

    private final List<Item> items;

    public Schedule(final List<Item> _items) {
        Assert.notNull(_items, "Items is null");
        this.items = _items;
    }

    public ScheduledItems getItemsForUpdate(final long _currentTime) {
        Assert.greaterOrEqual(_currentTime, 0, "Current time < 0");

        List<Item> result = new ArrayList<Item>();

        for (Item candidate : this.items) {

            if (candidate.needsUpdate(_currentTime)) {
                result.add(candidate);
            }
        }

        return new ScheduledItems(result);
    }

    public void itemUpdated(final Item _item, final long _time) {
        Assert.notNull(_item, "Item is null");
        Assert.greaterOrEqual(_time, 0, "Time < 0");

        this.items.remove(_item);

        _item.setLastUpdate(_time);

        this.items.add(_item);
    }
}
