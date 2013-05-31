package work.unit.constructor.processor;

import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.concat.ConcatProcessor;
import debug.snapshot.NameValuePair;
import debug.snapshot.ProcessorSnapshot;
import junit.framework.TestCase;
import variables.Variables;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 06.06.2009
 */
public class ConcatProcessorTest extends TestCase {

    public ConcatProcessorTest(final String _s) {
        super(_s);
    }

    // первоначальный тест

    public void testSmoke() throws VariableProcessor.VariableProcessorException {
        ConcatProcessor processor = new ConcatProcessor("", "", "", "");

        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, "first");
        variables.put(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, "second");

        processor.process(variables);

        assertEquals("firstsecond", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
    }

    // тест конкатенации указанных переменных

    public void testVariables() throws VariableProcessor.VariableProcessorException {
        ConcatProcessor processor = new ConcatProcessor("", "first", "second", "");

        Variables variables = new Variables();
        variables.put("first", "firstVal");
        variables.put("second", "secondVal");

        processor.process(variables);

        assertEquals("firstValsecondVal", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
    }

    // тест сцепления элементов

    public void testConcatElements() throws VariableProcessor.VariableProcessorException {
        ConcatProcessor processor = new ConcatProcessor("", "", "", "");

        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 0, "first0");
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 56, "first56");
        variables.put(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 56, "second56");
        variables.put(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 245, "second245");

        processor.process(variables);

        assertEquals("first0", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 0));
        assertEquals("first56second56", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 56));
        assertEquals("second245", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 245));
    }

    // тест сцепления элементов c указанием переменных

    public void testConcatElementsToVariable() throws VariableProcessor.VariableProcessorException {
        ConcatProcessor processor = new ConcatProcessor("", "first", "second", "test");

        Variables variables = new Variables();
        variables.put("first", 0, "first0");
        variables.put("first", 56, "first56");
        variables.put("second", 56, "second56");
        variables.put("second", 245, "second245");

        processor.process(variables);

        assertEquals("first0", variables.get("test", 0));
        assertEquals("first56second56", variables.get("test", 56));
        assertEquals("second245", variables.get("test", 245));
    }

    // тест на случайно пойманный глюк #1

    public void testBugNo1() throws VariableProcessor.VariableProcessorException {
        ConcatProcessor processor = new ConcatProcessor("", "", "", "test");

        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 0, "first0");
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 56, "first56");
        variables.put(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 56, "second56");
        variables.put(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 245, "second245");

        processor.process(variables);

        assertEquals("first0", variables.get("test", 0));
        assertEquals("first56second56", variables.get("test", 56));
        assertEquals("second245", variables.get("test", 245));
    }

    // тест на случайно пойманный глюк #2

    public void testBugNo2() throws VariableProcessor.VariableProcessorException {
        ConcatProcessor processor = new ConcatProcessor("", "content", "text", "content");

        Variables variables = new Variables();
        variables.put("content", 0, "first0");
        variables.put("content", 56, "first56");
        variables.put("text", 56, "second56");
        variables.put("text", 245, "second245");

        processor.process(variables);

        assertEquals("first0", variables.get("content", 0));
        assertEquals("first56second56", variables.get("content", 56));
        assertEquals("second245", variables.get("content", 245));
    }

    // тест конкатенации к выходной переменной

    public void testConcatToOutput() throws VariableProcessor.VariableProcessorException {
        ConcatProcessor processor = new ConcatProcessor("", Variables.DEFAULT_OUTPUT_VARIABLE_NAME, "text", "");

        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, "first0");
        variables.put("text", "first1");

        processor.process(variables);

        assertEquals("first0first1", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
    }

    // тест снапшота

    public void testSnapshot() {
        ConcatProcessor processor = new ConcatProcessor("test", "content", "text", "content2");

        ProcessorSnapshot snapshot = (ProcessorSnapshot) processor.getSnapshot();

        assertEquals(ConcatProcessor.CONCAT_PROCESSOR_NAME, snapshot.getName());

        List<NameValuePair> parameters = snapshot.getParameters();

        assertEquals(4, parameters.size());

        assertEquals(Variables.DEFAULT_INPUT_VARIABLE_NAME, parameters.get(0).getName());
        assertEquals("test", parameters.get(0).getValue());

        assertEquals(ConcatProcessor.FIRST_PARAMETER_NAME, parameters.get(1).getName());
        assertEquals("content", parameters.get(1).getValue());

        assertEquals(ConcatProcessor.SECOND_PARAMETER_NAME, parameters.get(2).getName());
        assertEquals("text", parameters.get(2).getValue());

        assertEquals(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, parameters.get(3).getName());
        assertEquals("content2", parameters.get(3).getValue());
    }
}
