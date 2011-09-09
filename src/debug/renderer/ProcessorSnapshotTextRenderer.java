package debug.renderer;

import debug.Snapshot;
import debug.SnapshotTextRenderer;
import debug.snapshot.NameValuePair;
import debug.snapshot.ProcessorSnapshot;
import util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Рендерер процессорного снапшота
 *
 * @author Igor Usenko
 *         Date: 14.08.2009
 */
public class ProcessorSnapshotTextRenderer implements SnapshotTextRenderer {

    public static final String INDENT_STRING = "    ";

    private static final String PREFIX = "O:";

    public List<String> render(final Snapshot _snapshot) {
        Assert.notNull(_snapshot, "Snapshot is null");
        Assert.isTrue(_snapshot instanceof ProcessorSnapshot, "This is not processor snapshot");

        List<String> result = new ArrayList<String>();

        StringBuilder buffer = new StringBuilder();
        buffer.append(((ProcessorSnapshot) _snapshot).getName());
        buffer.append(" ( ");
        buffer.append(renderParameters((ProcessorSnapshot) _snapshot));
        buffer.append(")");

        result.add(buffer.toString());

        return result;
    }

    public String getPrefix() {
        return PREFIX;
    }

    private String renderParameters(final ProcessorSnapshot _snapshot) {
        StringBuilder result = new StringBuilder();

        for (NameValuePair pair : _snapshot.getParameters()) {
            result.append(renderPair(pair)).append(" ");
        }

        return result.toString();
    }

    private String renderPair(final NameValuePair _pair) {
        return new StringBuilder(_pair.getName()).append("=").append(_pair.getValue()).toString();
    }
}
