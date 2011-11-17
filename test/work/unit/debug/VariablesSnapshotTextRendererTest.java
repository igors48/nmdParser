package work.unit.debug;

import debug.renderer.VariablesSnapshotTextRenderer;
import debug.snapshot.VariablesSnapshot;
import junit.framework.TestCase;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 19.08.2009
 */
public class VariablesSnapshotTextRendererTest extends TestCase {

    public VariablesSnapshotTextRendererTest(final String _s) {
        super(_s);
    }

    // первоначальный тест

    public void testSmoke() {
        VariablesSnapshot snapshot = new VariablesSnapshot();
        snapshot.addElement("name", 1, "value01", false);
        snapshot.addElement("name", 2, "value02", true);

        VariablesSnapshotTextRenderer renderer = new VariablesSnapshotTextRenderer();
        List<String> image = renderer.render(snapshot);

        assertNotNull(image);
        assertEquals(3, image.size());
        assertEquals("name", image.get(0));
        assertEquals(VariablesSnapshotTextRenderer.NO_CHANGED_PREFIX + "1" + VariablesSnapshotTextRenderer.INDEX_VALUE_DIVIDER + "value01", image.get(1));
        assertEquals(VariablesSnapshotTextRenderer.CHANGED_PREFIX + "2" + VariablesSnapshotTextRenderer.INDEX_VALUE_DIVIDER + "value02", image.get(2));
    }

    // тест двух переменных

    public void testTwoVariables() {
        VariablesSnapshot snapshot = new VariablesSnapshot();
        snapshot.addElement("name1", 1, "value01", false);
        snapshot.addElement("name1", 2, "value02", true);
        snapshot.addElement("name2", 1, "value201", false);
        snapshot.addElement("name2", 201, "value202", true);

        VariablesSnapshotTextRenderer renderer = new VariablesSnapshotTextRenderer();
        List<String> image = renderer.render(snapshot);

        assertNotNull(image);
        assertEquals(6, image.size());
        assertEquals("name1", image.get(0));
        assertEquals(VariablesSnapshotTextRenderer.NO_CHANGED_PREFIX + "1" + VariablesSnapshotTextRenderer.INDEX_VALUE_DIVIDER + "value01", image.get(1));
        assertEquals(VariablesSnapshotTextRenderer.CHANGED_PREFIX + "2" + VariablesSnapshotTextRenderer.INDEX_VALUE_DIVIDER + "value02", image.get(2));

        assertEquals("name2", image.get(3));
        assertEquals(VariablesSnapshotTextRenderer.NO_CHANGED_PREFIX + "1" + VariablesSnapshotTextRenderer.INDEX_VALUE_DIVIDER + "value201", image.get(4));
        assertEquals(VariablesSnapshotTextRenderer.CHANGED_PREFIX + "201" + VariablesSnapshotTextRenderer.INDEX_VALUE_DIVIDER + "value202", image.get(5));
    }
}
