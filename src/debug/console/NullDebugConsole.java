package debug.console;

import debug.DebugConsole;
import debug.Snapshot;
import util.Assert;

/**
 * Заглушка для отладочной консоли
 *
 * @author Igor Usenko
 *         Date: 27.08.2009
 */
public class NullDebugConsole implements DebugConsole {

    public void appendSnapshot(final Snapshot _snapshot) {
        Assert.notNull(_snapshot, "Debug snapshot is null");
    }
}
