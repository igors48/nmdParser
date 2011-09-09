package debug.snapshot;

import debug.Snapshot;
import util.Assert;

/**
 * Отладочный снапшот объекта верхнего уровня
 *
 * @author Igor Usenko
 *         Date: 29.08.2009
 */
public class TopLevelObjectSnapshot implements Snapshot {
    private final String name;

    public TopLevelObjectSnapshot(final String _name) {
        Assert.isValidString(_name, "Top level object name is invalid");
        this.name = _name;
    }

    public String getName() {
        return this.name;
    }
}
