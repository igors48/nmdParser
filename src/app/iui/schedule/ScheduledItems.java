package app.iui.schedule;

import util.Assert;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Igor Usenko
 *         Date: 07.05.2011
 */
public class ScheduledItems {

    private final List<Item> items;

    public ScheduledItems(final List<Item> _items) {
        Assert.notNull(_items, "Items is null");
        this.items = _items;
    }

    public Set<String> getWorkspaces() {
        final Set<String> result = new HashSet<String>();

        for (Item item : items) {
            result.add(item.getWorkspace());
        }

        return result;
    }

    public Set<Item> getItemsFromWorkspace(final String _workspace) {
        Assert.isValidString(_workspace, "Workspace name is not valid");

        final Set<Item> result = new HashSet<Item>();

        for (Item item : items) {

            if (item.getWorkspace().equalsIgnoreCase(_workspace)) {
                result.add(item);
            }
        }

        return result;
    }

    public int size() {
        return this.items.size();
    }
}
