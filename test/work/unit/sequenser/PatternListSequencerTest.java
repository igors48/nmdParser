package work.unit.sequenser;

import junit.framework.TestCase;
import util.sequense.PatternListSequencer;
import util.sequense.SequenceGenerationParams;

import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 28.02.2010
 */
public class PatternListSequencerTest extends TestCase {

    public PatternListSequencerTest(final String _s) {
        super(_s);
    }

    // первоначальный тест

    public void testSmoke() {
        SequenceGenerationParams fixture01 = new SequenceGenerationParams("a*b", 0, 1, 1, 1, 0, "");
        SequenceGenerationParams fixture02 = new SequenceGenerationParams("c*d", 1, 2, 1, 1, 0, "");
        SequenceGenerationParams fixture03 = new SequenceGenerationParams("ef", 1, 2, 1, 1, 0, "");

        List<SequenceGenerationParams> fixture = newArrayList();

        fixture.add(fixture01);
        fixture.add(fixture02);
        fixture.add(fixture03);

        List<String> result = PatternListSequencer.getSequence(fixture);

        assertEquals(5, result.size());

        assertEquals("a0b", result.get(0));
        assertEquals("a1b", result.get(1));
        assertEquals("c1d", result.get(2));
        assertEquals("c2d", result.get(3));
        assertEquals("ef", result.get(4));
    }
}
