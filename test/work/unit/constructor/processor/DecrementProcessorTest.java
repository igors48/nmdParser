package work.unit.constructor.processor;

import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.decrement.DecrementProcessor;
import debug.snapshot.NameValuePair;
import debug.snapshot.ProcessorSnapshot;
import junit.framework.TestCase;
import variables.Variables;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 03.07.2009
 */
public class DecrementProcessorTest extends TestCase {

    public DecrementProcessorTest(final String _s) {
        super(_s);
    }

    // первоначальный тест

    public void testSmoke() throws VariableProcessor.VariableProcessorException {
        Variables variables = new Variables();
        variables.put("test", 0, "49");
        variables.put("test", 5, "55");
        variables.put("test", 124, "4a");

        DecrementProcessor processor = new DecrementProcessor("test", "");
        processor.process(variables);

        assertEquals("49", variables.get("test", 0));
        assertEquals("55", variables.get("test", 5));
        assertEquals("4a", variables.get("test", 124));

        assertEquals("48", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 0));
        assertEquals("54", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 5));
        assertEquals("4a", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 124));
    }

    // тест c выходной переменной

    public void testWithOutput() throws VariableProcessor.VariableProcessorException {
        Variables variables = new Variables();
        variables.put("test", 0, "49");
        variables.put("test", 5, "55");
        variables.put("test", 124, "4a");

        DecrementProcessor processor = new DecrementProcessor("test", "testout");
        processor.process(variables);

        assertEquals("49", variables.get("test", 0));
        assertEquals("55", variables.get("test", 5));
        assertEquals("4a", variables.get("test", 124));

        assertEquals("48", variables.get("testout", 0));
        assertEquals("54", variables.get("testout", 5));
        assertEquals("4a", variables.get("testout", 124));
    }

    // тест без параметров

    public void testWithoutParms() throws VariableProcessor.VariableProcessorException {
        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 0, "49");
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 5, "55");
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 124, "4a");

        DecrementProcessor processor = new DecrementProcessor("", "");
        processor.process(variables);

        assertEquals("49", variables.get(Variables.DEFAULT_INPUT_VARIABLE_NAME, 0));
        assertEquals("55", variables.get(Variables.DEFAULT_INPUT_VARIABLE_NAME, 5));
        assertEquals("4a", variables.get(Variables.DEFAULT_INPUT_VARIABLE_NAME, 124));

        assertEquals("48", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 0));
        assertEquals("54", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 5));
        assertEquals("4a", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 124));
    }

    // тест снапшота

    public void testSnapshot() {
        DecrementProcessor processor = new DecrementProcessor("test", "content2");

        ProcessorSnapshot snapshot = (ProcessorSnapshot) processor.getSnapshot();

        assertEquals(DecrementProcessor.DECREMENT_PROCESSOR_NAME, snapshot.getName());

        List<NameValuePair> parameters = snapshot.getParameters();

        assertEquals(2, parameters.size());

        assertEquals(Variables.DEFAULT_INPUT_VARIABLE_NAME, parameters.get(0).getName());
        assertEquals("test", parameters.get(0).getValue());

        assertEquals(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, parameters.get(1).getName());
        assertEquals("content2", parameters.get(1).getValue());
    }
}
