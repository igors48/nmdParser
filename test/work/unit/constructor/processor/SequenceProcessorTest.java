package work.unit.constructor.processor;

import junit.framework.TestCase;
import variables.Variables;
import constructor.objects.processor.sequencer.SequenceProcessor;
import constructor.objects.processor.VariableProcessor;

import java.util.List;

import debug.snapshot.ProcessorSnapshot;
import debug.snapshot.NameValuePair;

/**
 * @author Igor Usenko
 *         Date: 04.06.2009
 */
public class SequenceProcessorTest extends TestCase {

    public SequenceProcessorTest(final String _s) {
        super(_s);
    }

    // первоначальный тест
    public void testSmoke() throws VariableProcessor.VariableProcessorException {
        Variables variables = new Variables();
        variables.put("start", "50");
        variables.put("stop", "51");
        variables.put("step", "1");
        variables.put("mult", "1");
        variables.put("pattern", "www.domain.com/page=*&index");

        SequenceProcessor processor = new SequenceProcessor("input", "pattern", "start", "stop", "step", "mult", "output", "", "");

        processor.process(variables);

        assertEquals(2, variables.getSize("output"));
        assertEquals("www.domain.com/page=50&index", variables.get("output", 0));
        assertEquals("www.domain.com/page=51&index", variables.get("output", 1));
    }

    // тест с заданием длины и паддинга
    public void testLengthAndPadding() throws VariableProcessor.VariableProcessorException {
        Variables variables = new Variables();
        variables.put("start", "50");
        variables.put("stop", "51");
        variables.put("step", "1");
        variables.put("mult", "1");
        variables.put("len", "3");
        variables.put("padd", "x");
        variables.put("pattern", "www.domain.com/page=*&index");

        SequenceProcessor processor = new SequenceProcessor("input", "pattern", "start", "stop", "step", "mult", "output", "len", "padd");

        processor.process(variables);

        assertEquals(2, variables.getSize("output"));
        assertEquals("www.domain.com/page=x50&index", variables.get("output", 0));
        assertEquals("www.domain.com/page=x51&index", variables.get("output", 1));
    }

    // тест снапшота
    public void testSnapshot() {
        SequenceProcessor processor = new SequenceProcessor("test", "pattern", "start", "stop", "step", "mult", "out", "len", "padd");

        ProcessorSnapshot snapshot = (ProcessorSnapshot) processor.getSnapshot();

        assertEquals(SequenceProcessor.SEQUENCE_PROCESSOR_NAME, snapshot.getName());

        List<NameValuePair> parameters = snapshot.getParameters();

        assertEquals(9, parameters.size());

        assertEquals(Variables.DEFAULT_INPUT_VARIABLE_NAME, parameters.get(0).getName());
        assertEquals("test", parameters.get(0).getValue());

        assertEquals(SequenceProcessor.PATTERN_PARAMETER_NAME, parameters.get(1).getName());
        assertEquals("pattern", parameters.get(1).getValue());

        assertEquals(SequenceProcessor.START_PARAMETER_NAME, parameters.get(2).getName());
        assertEquals("start", parameters.get(2).getValue());

        assertEquals(SequenceProcessor.STOP_PARAMETER_NAME, parameters.get(3).getName());
        assertEquals("stop", parameters.get(3).getValue());

        assertEquals(SequenceProcessor.STEP_PARAMETER_NAME, parameters.get(4).getName());
        assertEquals("step", parameters.get(4).getValue());

        assertEquals(SequenceProcessor.MULT_PARAMETER_NAME, parameters.get(5).getName());
        assertEquals("mult", parameters.get(5).getValue());

        assertEquals(SequenceProcessor.LEN_PARAMETER_NAME, parameters.get(6).getName());
        assertEquals("len", parameters.get(6).getValue());

        assertEquals(SequenceProcessor.PADD_PARAMETER_NAME, parameters.get(7).getName());
        assertEquals("padd", parameters.get(7).getValue());

        assertEquals(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, parameters.get(8).getName());
        assertEquals("out", parameters.get(8).getValue());
    }

}
