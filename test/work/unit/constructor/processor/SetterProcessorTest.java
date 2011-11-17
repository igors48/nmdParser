package work.unit.constructor.processor;

import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.setter.SetterProcessor;
import debug.snapshot.NameValuePair;
import debug.snapshot.ProcessorSnapshot;
import junit.framework.TestCase;
import variables.Variables;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 05.06.2009
 */
public class SetterProcessorTest extends TestCase {

    public SetterProcessorTest(final String _s) {
        super(_s);
    }

    // первоначальный тест

    public void testSmoke() throws VariableProcessor.VariableProcessorException {
        SetterProcessor processor = new SetterProcessor("", "48", "", "test");

        Variables variables = new Variables();

        processor.process(variables);

        assertEquals("48", variables.get("test"));
    }

    // тест на замену существующего значения

    public void testReplace() throws VariableProcessor.VariableProcessorException {
        SetterProcessor processor = new SetterProcessor("", "48", "", "test");

        Variables variables = new Variables();
        variables.put("test", "45");

        processor.process(variables);

        assertEquals("48", variables.get("test"));
    }

    // тест на копирование значения

    public void testCopy() throws VariableProcessor.VariableProcessorException {
        SetterProcessor processor = new SetterProcessor("test", "", "", "test2");
        Variables variables = new Variables();
        variables.put("test", "45");

        processor.process(variables);

        assertEquals("45", variables.get("test"));
        assertEquals("45", variables.get("test2"));
    }

    // тест копирования без параметров

    public void testCopyNoParms() throws VariableProcessor.VariableProcessorException {
        SetterProcessor processor = new SetterProcessor("", "", "", "");
        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 0, "45");
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 1000, "45000");
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 56, "4556");

        processor.process(variables);

        assertEquals("45", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 0));
        assertEquals("45000", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 1000));
        assertEquals("4556", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 56));

        assertEquals("45", variables.get(Variables.DEFAULT_INPUT_VARIABLE_NAME, 0));
        assertEquals("45000", variables.get(Variables.DEFAULT_INPUT_VARIABLE_NAME, 1000));
        assertEquals("4556", variables.get(Variables.DEFAULT_INPUT_VARIABLE_NAME, 56));
    }

    // тест индексного копирования

    public void testIndexedCopy() throws VariableProcessor.VariableProcessorException {
        SetterProcessor processor = new SetterProcessor("", "", "index", "");

        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 0, "0");
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 98, "98");
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, 879, "879");

        variables.put("index", 0, "0");
        variables.put("index", 1, "104");
        variables.put("index", 58, "98");

        variables.put(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 0, "48");
        variables.put(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 8791, "8791");

        processor.process(variables);

        assertEquals("0", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 0));
        assertEquals("98", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 98));
        assertEquals("8791", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 8791));
        assertNull(variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 104));
    }

    // тест установки значения первого элемента

    public void testSetFirst() throws VariableProcessor.VariableProcessorException {
        SetterProcessor processor = new SetterProcessor("", "value", "", "");

        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, "0");

        variables.put(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 8791, "8791");

        processor.process(variables);

        assertEquals("value", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 0));
        assertEquals("8791", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 8791));
    }

    // тест установки значения в элементы указанные в индексе

    public void testSetIndexed() throws VariableProcessor.VariableProcessorException {
        SetterProcessor processor = new SetterProcessor("", "value", "index", "");

        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, "0");

        variables.put(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 8791, "8791");

        variables.put("index", 0, "0");
        variables.put("index", 1, "104");
        variables.put("index", 58, "98");

        processor.process(variables);

        assertEquals("value", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 0));
        assertEquals("value", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 104));
        assertEquals("value", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 98));
        assertEquals("8791", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 8791));
    }

    // тест снапшота

    public void testSnapshot() {
        SetterProcessor processor = new SetterProcessor("test", "content2", "index", "out");

        ProcessorSnapshot snapshot = (ProcessorSnapshot) processor.getSnapshot();

        assertEquals(SetterProcessor.SETTER_PROCESSOR_NAME, snapshot.getName());

        List<NameValuePair> parameters = snapshot.getParameters();

        assertEquals(4, parameters.size());

        assertEquals(Variables.DEFAULT_INPUT_VARIABLE_NAME, parameters.get(0).getName());
        assertEquals("test", parameters.get(0).getValue());

        assertEquals(SetterProcessor.VALUE_PARAMETER_NAME, parameters.get(1).getName());
        assertEquals("content2", parameters.get(1).getValue());

        assertEquals(SetterProcessor.INDEX_PARAMETER_NAME, parameters.get(2).getName());
        assertEquals("index", parameters.get(2).getValue());

        assertEquals(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, parameters.get(3).getName());
        assertEquals("out", parameters.get(3).getValue());
    }

}
