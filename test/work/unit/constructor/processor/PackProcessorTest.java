package work.unit.constructor.processor;

import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.pack.PackProcessor;
import debug.snapshot.NameValuePair;
import debug.snapshot.ProcessorSnapshot;
import junit.framework.TestCase;
import variables.Variables;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 27.10.2009
 */
public class PackProcessorTest extends TestCase {

    public PackProcessorTest(final String _s) {
        super(_s);
    }

    // первоначальный тест

    public void testSmoke() throws VariableProcessor.VariableProcessorException {
        PackProcessor processor = new PackProcessor("", "");

        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 0, "zero");
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 1, "one");

        processor.process(variables);

        assertEquals("zero", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 0));
        assertEquals("one", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 1));
    }

    // тест c пустыми элементами

    public void testWithEmpties() throws VariableProcessor.VariableProcessorException {
        PackProcessor processor = new PackProcessor("", "");

        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 0, "zero");
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 1, "one");
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 2, "");
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 3, "two");

        processor.process(variables);

        assertEquals("zero", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 0));
        assertEquals("one", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 1));
        assertEquals("two", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 2));
    }

    // тест снапшота

    public void testSnapshot() {
        PackProcessor processor = new PackProcessor("test", "content2");

        ProcessorSnapshot snapshot = (ProcessorSnapshot) processor.getSnapshot();

        assertEquals(PackProcessor.PACK_PROCESSOR_NAME, snapshot.getName());

        List<NameValuePair> parameters = snapshot.getParameters();

        assertEquals(2, parameters.size());

        assertEquals(Variables.DEFAULT_INPUT_VARIABLE_NAME, parameters.get(0).getName());
        assertEquals("test", parameters.get(0).getValue());

        assertEquals(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, parameters.get(1).getName());
        assertEquals("content2", parameters.get(1).getValue());
    }
}
