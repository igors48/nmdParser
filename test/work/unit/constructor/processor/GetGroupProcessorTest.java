package work.unit.constructor.processor;

import constructor.objects.processor.OccurrenceSet;
import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.get_group.GetGroupProcessor;
import debug.snapshot.NameValuePair;
import debug.snapshot.ProcessorSnapshot;
import junit.framework.TestCase;
import variables.Variables;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 21.03.2009
 */
public class GetGroupProcessorTest extends TestCase {

    public GetGroupProcessorTest(String _s) {
        super(_s);
    }

    // тест - нормальная работа
    public void testNormal() {
        Variables variables = new Variables();
        variables.put("inVar", "asdopaefg");
        OccurrenceSet occurences = new OccurrenceSet();
        occurences.add(0);

        GetGroupProcessor processor = new GetGroupProcessor("inVar", "asd(.+?)efg", occurences, 1, "outVar");

        try {
            processor.process(variables);
        } catch (VariableProcessor.VariableProcessorException e) {
            fail(e.getMessage());
        }

        assertEquals("opa", variables.get("outVar"));
        assertEquals("asdopaefg", variables.get("inVar"));
    }

    // тест - паттерн не совпал
    public void testNoMatch() {
        Variables variables = new Variables();
        variables.put("inVar", "addopaefg");
        OccurrenceSet occurences = new OccurrenceSet();
        occurences.add(0);

        GetGroupProcessor processor = new GetGroupProcessor("inVar", "asd(.+?)efg", occurences, 1, "outVar");

        try {
            processor.process(variables);
        } catch (VariableProcessor.VariableProcessorException e) {
            fail(e.getMessage());
        }

        assertEquals(null, variables.get("outVar"));
        assertEquals("addopaefg", variables.get("inVar"));
    }

    // тест - дефолтные имена входа и выхода
    public void testDefaultNames() {
        Variables variables = new Variables();
        variables.put("input", "asdopaefg");
        OccurrenceSet occurences = new OccurrenceSet();
        occurences.add(0);

        GetGroupProcessor processor = new GetGroupProcessor("", "asd(.+?)efg", occurences, 1, "");

        try {
            processor.process(variables);
        } catch (VariableProcessor.VariableProcessorException e) {
            fail(e.getMessage());
        }

        assertEquals("opa", variables.get("output"));
        assertEquals("asdopaefg", variables.get("input"));
    }

    // тест списка вхождений
    public void testGroupList() {
        Variables variables = new Variables();
        variables.put("input", "asdopaefgasdppaefgasdpppefg");
        OccurrenceSet occurences = new OccurrenceSet();
        occurences.add(0);
        occurences.add(2);

        GetGroupProcessor processor = new GetGroupProcessor("", "asd(.+?)efg", occurences, 1, "");

        try {
            processor.process(variables);
        } catch (VariableProcessor.VariableProcessorException e) {
            fail(e.getMessage());
        }

        assertEquals(2, variables.getSize("output"));
        assertEquals("opa", variables.get("output", 0));
        assertEquals("ppp", variables.get("output", 1));
        assertEquals("asdopaefgasdppaefgasdpppefg", variables.get("input"));
    }

    // тест списка вхождений с указанной группой
    public void testGroupListWithDefinedGroup() {
        Variables variables = new Variables();
        variables.put("input", "asdopaefgasdppaefgasdpppefg");
        OccurrenceSet occurences = new OccurrenceSet();
        occurences.add(0);
        occurences.add(2);

        GetGroupProcessor processor = new GetGroupProcessor("", "asd(.)(.+?)efg", occurences, 2, "");

        try {
            processor.process(variables);
        } catch (VariableProcessor.VariableProcessorException e) {
            fail(e.getMessage());
        }

        assertEquals(2, variables.getSize("output"));
        assertEquals("pa", variables.get("output", 0));
        assertEquals("pp", variables.get("output", 1));
        assertEquals("asdopaefgasdppaefgasdpppefg", variables.get("input"));
    }

    // тест обработки многоэлементной переменной
    public void testManyElements() {
        Variables variables = new Variables();
        variables.put("input", 0, "asdopaefgasdppaefgasdpppefg");
        variables.put("input", 1, "asdopoefgasdppaefgasdppfefg");
        OccurrenceSet occurences = new OccurrenceSet();
        occurences.add(0);
        occurences.add(2);

        GetGroupProcessor processor = new GetGroupProcessor("", "asd(.)(.+?)efg", occurences, 2, "");

        try {
            processor.process(variables);
        } catch (VariableProcessor.VariableProcessorException e) {
            fail(e.getMessage());
        }

        assertEquals(4, variables.getSize("output"));
        assertEquals("pa", variables.get("output", 0));
        assertEquals("pp", variables.get("output", 1));
        assertEquals("po", variables.get("output", 2));
        assertEquals("pf", variables.get("output", 3));
        assertEquals("asdopaefgasdppaefgasdpppefg", variables.get("input"));
    }

    // тест снапшота с заданными вхождениями
    public void testSnapshotWithOccurrences() {
        OccurrenceSet occurrenceSet = new OccurrenceSet();
        occurrenceSet.add(0);
        occurrenceSet.add(2);
        occurrenceSet.add(789);

        GetGroupProcessor processor = new GetGroupProcessor("test", "pattern", occurrenceSet, 5, "content2");

        ProcessorSnapshot snapshot = (ProcessorSnapshot) processor.getSnapshot();

        assertEquals(GetGroupProcessor.GET_GROUP_PROCESSOR_NAME, snapshot.getName());

        List<NameValuePair> parameters = snapshot.getParameters();

        assertEquals(5, parameters.size());

        assertEquals(Variables.DEFAULT_INPUT_VARIABLE_NAME, parameters.get(0).getName());
        assertEquals("test", parameters.get(0).getValue());

        assertEquals(GetGroupProcessor.PATTERN_PARAMETER_NAME, parameters.get(1).getName());
        assertEquals("pattern", parameters.get(1).getValue());

        assertEquals(GetGroupProcessor.OCCURRENCES_PARAMETER_NAME, parameters.get(2).getName());
        assertEquals("[0, 2, 789]", parameters.get(2).getValue());

        assertEquals(GetGroupProcessor.GROUP_PARAMETER_NAME, parameters.get(3).getName());
        assertEquals("5", parameters.get(3).getValue());

        assertEquals(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, parameters.get(4).getName());
        assertEquals("content2", parameters.get(4).getValue());
    }


}
