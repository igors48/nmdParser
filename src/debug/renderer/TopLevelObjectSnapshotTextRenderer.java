package debug.renderer;

import debug.Snapshot;
import debug.SnapshotTextRenderer;
import debug.snapshot.TopLevelObjectSnapshot;
import util.Assert;

import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * Рендерер снапшота объекта верхнего уровня
 *
 * @author Igor Usenko
 *         Date: 29.08.2009
 */
public class TopLevelObjectSnapshotTextRenderer implements SnapshotTextRenderer {

    private static final String PREFIX = "T:";

    public List<String> render(final Snapshot _snapshot) {
        Assert.notNull(_snapshot, "Snapshot is null");
        Assert.isTrue(_snapshot instanceof TopLevelObjectSnapshot, "This is not top level object snapshot");

        List<String> result = newArrayList();

        result.add(((TopLevelObjectSnapshot) _snapshot).getName());

        return result;
    }

    public String getPrefix() {
        return PREFIX;
    }
}
