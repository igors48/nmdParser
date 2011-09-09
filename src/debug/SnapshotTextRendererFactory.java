package debug;

import debug.renderer.EmptySnapshotTextRenderer;
import debug.renderer.ProcessorSnapshotTextRenderer;
import debug.renderer.TopLevelObjectSnapshotTextRenderer;
import debug.renderer.VariablesSnapshotTextRenderer;
import debug.snapshot.ProcessorSnapshot;
import debug.snapshot.TopLevelObjectSnapshot;
import debug.snapshot.VariablesSnapshot;
import util.Assert;

/**
 * Фабрика текстовых рендеров
 *
 * @author Igor Usenko
 *         Date: 14.08.2009
 */
public class SnapshotTextRendererFactory {

    private final SnapshotTextRenderer empty = new EmptySnapshotTextRenderer();
    private final SnapshotTextRenderer processor = new ProcessorSnapshotTextRenderer();
    private final SnapshotTextRenderer variables = new VariablesSnapshotTextRenderer();
    private final SnapshotTextRenderer topLevel = new TopLevelObjectSnapshotTextRenderer();

    public SnapshotTextRenderer getRender(final Snapshot _snapshot) {
        Assert.notNull(_snapshot, "Snapshot is null");

        SnapshotTextRenderer result = empty;

        if (_snapshot instanceof ProcessorSnapshot) {
            result = processor;
        }

        if (_snapshot instanceof VariablesSnapshot) {
            result = variables;
        }

        if (_snapshot instanceof TopLevelObjectSnapshot) {
            result = topLevel;
        }

        return result;
    }
}
