package debug.console;

import debug.*;
import debug.snapshot.TopLevelObjectSnapshot;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import java.util.List;
import java.util.Map;

import static util.CollectionUtils.newArrayList;
import static util.CollectionUtils.newHashMap;

/**
 * Отладочная консоль с записью в файл
 *
 * @author Igor Usenko
 *         Date: 27.08.2009
 */
public class FileDebugConsole implements DebugConsole {

    private final DebugConsoleUpdater updater;
    private final SnapshotTextRendererFactory factory;
    private final Map<String, List<String>> images;

    private String current;

    private final Log log;
    private static final String EMPTY_LINE = "E:";

    public FileDebugConsole(final DebugConsoleUpdater _updater) {
        Assert.notNull(_updater, "Updater is null");
        this.updater = _updater;
        this.updater.clean();

        this.factory = new SnapshotTextRendererFactory();
        this.images = newHashMap();

        this.log = LogFactory.getLog(getClass());
    }

    public void appendSnapshot(final Snapshot _snapshot) {
        Assert.notNull(_snapshot, "Debug snapshot is null");

        try {

            if (_snapshot instanceof TopLevelObjectSnapshot) {
                processTopLevelSnapshot((TopLevelObjectSnapshot) _snapshot);
            } else {
                processCommonSnapshot(_snapshot);
            }
        } catch (DebugConsoleUpdater.DebugConsoleUpdaterException e) {
            this.log.error("Error update console", e);
        }
    }

    private void processCommonSnapshot(final Snapshot _snapshot) throws DebugConsoleUpdater.DebugConsoleUpdaterException {

        if (current == null) {
            this.log.error("Common snapshot without top level snapshot received");
        } else {
            List<String> image = getImage();

            SnapshotTextRenderer renderer = this.factory.getRender(_snapshot);
            String prefix = renderer.getPrefix();
            List<String> output = renderer.render(_snapshot);

            for (String current : output) {
                StringBuilder buffer = new StringBuilder(prefix);
                buffer.append(" ").append(current);

                image.add(buffer.toString());
            }

            image.add(EMPTY_LINE);

            this.updater.update(this.current, image);
        }
    }

    private void processTopLevelSnapshot(final TopLevelObjectSnapshot _snapshot) throws DebugConsoleUpdater.DebugConsoleUpdaterException {
        this.current = _snapshot.getName();
        processCommonSnapshot(_snapshot);
    }

    private List<String> getImage() {
        List<String> result = this.images.get(this.current);

        if (result == null) {
            result = newArrayList();
            this.images.put(this.current, result);
        }

        return result;
    }
}
