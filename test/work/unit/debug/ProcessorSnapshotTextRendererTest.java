package work.unit.debug;

import debug.SnapshotTextRenderer;
import debug.renderer.ProcessorSnapshotTextRenderer;
import debug.snapshot.ProcessorSnapshot;
import junit.framework.TestCase;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 14.08.2009
 */
public class ProcessorSnapshotTextRendererTest extends TestCase {

    public ProcessorSnapshotTextRendererTest(final String _s) {
        super(_s);
    }

    // первоначальный тест

    public void testSmoke() {
        ProcessorSnapshot snapshot = new ProcessorSnapshot("name");
        snapshot.addParameter("par01", "val01");
        snapshot.addParameter("par02", "");
        snapshot.addParameter("par03", "val03");

        SnapshotTextRenderer renderer = new ProcessorSnapshotTextRenderer();

        List<String> result = renderer.render(snapshot);

        assertEquals(1, result.size());

        assertEquals("name ( " + "par01=val01 par02= par03=val03 " + ")", result.get(0));
    }
}
