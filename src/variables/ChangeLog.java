package variables;

import util.Assert;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Igor Usenko
 *         Date: 19.08.2009
 */
public class ChangeLog {

    private final Set<Item> changes;

    public ChangeLog() {
        this.changes = new HashSet<Item>();
    }

    public void reset() {
        this.changes.clear();
    }

    public int size() {
        return this.changes.size();
    }

    public void add(final String _name, final int _index) {
        Assert.isValidString(_name, "Variable name is not valid");
        Assert.greaterOrEqual(_index, 0, "Variable index < 0");

        this.changes.add(new Item(_name, _index));
    }

    public boolean isChanged(final String _name, final int _index) {
        Assert.isValidString(_name, "Variable name is not valid");
        Assert.greaterOrEqual(_index, 0, "Variable index < 0");

        return this.changes.contains(new Item(_name, _index));
    }

    private class Item {
        private final String name;
        private final int index;

        private Item(final String _name, final int _index) {
            Assert.isValidString(_name, "Variable name is not valid");
            this.name = _name;

            Assert.greaterOrEqual(_index, 0, "Variable index < 0");
            this.index = _index;
        }

        public boolean equals(final Object _o) {

            if (this == _o) return true;

            if (!(_o instanceof Item)) return false;

            Item item = (Item) _o;

            return index == item.index && name.equals(item.name);
        }

        public int hashCode() {
            int result;

            result = name.hashCode();
            result = 31 * result + index;

            return result;
        }
    }
}
