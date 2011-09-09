package work.unit.constructor.processor;

import constructor.objects.ConfigurationException;
import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.append.AppendProcessor;
import constructor.objects.processor.append.adapter.AppendProcessorAdapter;
import debug.snapshot.NameValuePair;
import debug.snapshot.ProcessorSnapshot;
import junit.framework.TestCase;
import variables.Variables;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 21.09.2009
 */
public class AppendProcessorTest extends TestCase {

    public AppendProcessorTest(final String _s) {
        super(_s);
    }

    // первоначальный тест
    public void testSmoke() throws VariableProcessor.VariableProcessorException {
        AppendProcessor processor = new AppendProcessor("", "", "", "");

        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, "first");
        variables.put(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, "second");

        processor.process(variables);

        assertEquals(2, variables.getSize(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));

        assertEquals("second", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 0));
        assertEquals("first", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 1));
    }

    // тест указанных переменных
    public void testVariables() throws VariableProcessor.VariableProcessorException {
        AppendProcessor processor = new AppendProcessor("", "first", "second", "");

        Variables variables = new Variables();
        variables.put("first", 50, "first");
        variables.put("second", 60, "second");

        processor.process(variables);

        assertEquals(0, variables.getSize(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
        assertEquals(2, variables.getSize("second"));

        assertEquals("second", variables.get("second", 60));
        assertEquals("first", variables.get("second", 61));
    }

    // тест создание с дефолтными значениями
    public void testCreateWithDefaults() throws ConfigurationException {
        AppendProcessor processor = (AppendProcessor) new AppendProcessorAdapter().getProcessor();
    }

    // тест снапшота
    public void testSnapshot() {
        AppendProcessor processor = new AppendProcessor("test", "content", "text", "content2");

        ProcessorSnapshot snapshot = (ProcessorSnapshot) processor.getSnapshot();

        assertEquals(AppendProcessor.APPEND_PROCESSOR_NAME, snapshot.getName());

        List<NameValuePair> parameters = snapshot.getParameters();

        assertEquals(4, parameters.size());

        assertEquals(Variables.DEFAULT_INPUT_VARIABLE_NAME, parameters.get(0).getName());
        assertEquals("test", parameters.get(0).getValue());

        assertEquals(AppendProcessor.FIRST_PARAMETER_NAME, parameters.get(1).getName());
        assertEquals("content", parameters.get(1).getValue());

        assertEquals(AppendProcessor.SECOND_PARAMETER_NAME, parameters.get(2).getName());
        assertEquals("text", parameters.get(2).getValue());

        assertEquals(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, parameters.get(3).getName());
        assertEquals("content2", parameters.get(3).getValue());
    }

}
