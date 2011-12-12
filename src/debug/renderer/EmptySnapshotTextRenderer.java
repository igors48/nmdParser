package debug.renderer;

import debug.Snapshot;
import debug.SnapshotTextRenderer;

import java.util.ArrayList;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * Рендерер для пустого снапшота
 *
 * @author Igor Usenko
 *         Date: 14.08.2009
 */
public class EmptySnapshotTextRenderer implements SnapshotTextRenderer {

    private static final String PREFIX = "E:";

    public List<String> render(final Snapshot _snapshot) {
        List<String> result = newArrayList();

        result.add("Empty snapshot");

        return result;
    }

    public String getPrefix() {
        return PREFIX;
    }
}
