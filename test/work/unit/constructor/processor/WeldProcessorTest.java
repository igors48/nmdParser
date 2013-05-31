package work.unit.constructor.processor;

import constructor.objects.ConfigurationException;
import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.weld.WeldProcessor;
import constructor.objects.processor.weld.adapter.WeldProcessorAdapter;
import debug.snapshot.NameValuePair;
import debug.snapshot.ProcessorSnapshot;
import junit.framework.TestCase;
import variables.Variables;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 27.10.2009
 */
public class WeldProcessorTest extends TestCase {

    public WeldProcessorTest(final String _s) {
        super(_s);
    }

    // первоначальный тест

    public void testSmoke() throws VariableProcessor.VariableProcessorException {
        WeldProcessor processor = new WeldProcessor("", "", "");

        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 0, "a");
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 1, "b");
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 2, "c");

        processor.process(variables);

        assertEquals("abc", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
    }

    // тест с отсутствующими элементами

    public void testWithEmpties() throws VariableProcessor.VariableProcessorException {
        WeldProcessor processor = new WeldProcessor("", "", "");

        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 0, "");
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 1, "b");
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 2, "");
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 3, "c");

        processor.process(variables);

        assertEquals("bc", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
    }

    // тест с разделителем

    public void testWithDivider() throws VariableProcessor.VariableProcessorException {
        WeldProcessor processor = new WeldProcessor("", "divider", "");

        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 0, "a");
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 1, "b");
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 2, "c");

        variables.put("divider", "/");

        processor.process(variables);

        assertEquals("a/b/c", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
    }

    // тест - создание процессора с дефолтными параметрами

    public void testCreateFromDefaults() throws ConfigurationException, VariableProcessor.VariableProcessorException {
        WeldProcessor processor = (WeldProcessor) new WeldProcessorAdapter().getProcessor();

        Variables variables = new Variables();

        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 0, "a");
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 1, "b");
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 2, "c");

        processor.process(variables);

        assertEquals("abc", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
    }

    // тест снапшота

    public void testSnapshot() {
        WeldProcessor processor = new WeldProcessor("test", "divider", "content2");

        ProcessorSnapshot snapshot = (ProcessorSnapshot) processor.getSnapshot();

        assertEquals(WeldProcessor.WELD_PROCESSOR_NAME, snapshot.getName());

        List<NameValuePair> parameters = snapshot.getParameters();

        assertEquals(3, parameters.size());

        assertEquals(Variables.DEFAULT_INPUT_VARIABLE_NAME, parameters.get(0).getName());
        assertEquals("test", parameters.get(0).getValue());

        assertEquals(WeldProcessor.DIVIDER_PARAMETER_NAME, parameters.get(1).getName());
        assertEquals("divider", parameters.get(1).getValue());

        assertEquals(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, parameters.get(2).getName());
        assertEquals("content2", parameters.get(2).getValue());
    }

}
