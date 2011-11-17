package work.unit.constructor.processor;

import constructor.objects.processor.OccurrenceSet;
import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.get_group.GetGroupProcessor;
import constructor.objects.processor.remove_tag.RemoveTagProcessor;
import debug.snapshot.NameValuePair;
import debug.snapshot.ProcessorSnapshot;
import junit.framework.TestCase;
import variables.Variables;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 20.05.2009
 */
public class RemoveTagProcessorTest extends TestCase {

    public RemoveTagProcessorTest(final String _s) {
        super(_s);
    }

    // первоначальный тест

    public void testSmoke() throws VariableProcessor.VariableProcessorException {
        OccurrenceSet occurenceSet = new OccurrenceSet();
        RemoveTagProcessor processor = new RemoveTagProcessor("", "<b>", occurenceSet, "");
        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, "before<b>content</b>after");

        processor.process(variables);

        assertEquals("beforeafter", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
    }

    // тест на удаление нужного вхождения

    public void testRemoveOccurence() throws VariableProcessor.VariableProcessorException {
        OccurrenceSet occurenceSet = new OccurrenceSet();
        occurenceSet.add(1);
        RemoveTagProcessor processor = new RemoveTagProcessor("", "<b>", occurenceSet, "");
        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, "before1<b>content</b>after1before2<b>content</b>after2");

        processor.process(variables);

        assertEquals("before1<b>content</b>after1before2after2", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
    }

    // тест на отсутствие нужного вхождения

    public void testNonExistentOccurence() throws VariableProcessor.VariableProcessorException {
        OccurrenceSet occurenceSet = new OccurrenceSet();
        occurenceSet.add(48);
        RemoveTagProcessor processor = new RemoveTagProcessor("", "<b>", occurenceSet, "");
        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, "before1<b>content</b>after1before2<b>content</b>after2");

        processor.process(variables);

        assertEquals("before1<b>content</b>after1before2<b>content</b>after2", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
    }

    // тест на удаление отсутствие нужного тега

    public void testNoTagOccurence() throws VariableProcessor.VariableProcessorException {
        OccurrenceSet occurenceSet = new OccurrenceSet();
        RemoveTagProcessor processor = new RemoveTagProcessor("", "<i>", occurenceSet, "");
        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, "before1<b>content</b>after1before2<b>content</b>after2");

        processor.process(variables);

        assertEquals("before1<b>content</b>after1before2<b>content</b>after2", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
    }

    // тест на удаление списка вхождений

    public void testOccurenceList() throws VariableProcessor.VariableProcessorException {
        OccurrenceSet occurenceSet = new OccurrenceSet();
        occurenceSet.add(0);
        occurenceSet.add(2);

        RemoveTagProcessor processor = new RemoveTagProcessor("", "<b>", occurenceSet, "");

        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, "before1<b>content</b>after1before2<b>content</b>after2before3<b>content</b>after3");

        processor.process(variables);

        assertEquals("before1after1before2<b>content</b>after2before3after3", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
    }

    // тест снапшота с заданными вхождениями

    public void testSnapshotWithOccurrences() {
        OccurrenceSet occurrenceSet = new OccurrenceSet();
        occurrenceSet.add(0);
        occurrenceSet.add(2);
        occurrenceSet.add(789);

        RemoveTagProcessor processor = new RemoveTagProcessor("test", "pattern", occurrenceSet, "content2");

        ProcessorSnapshot snapshot = (ProcessorSnapshot) processor.getSnapshot();

        assertEquals(RemoveTagProcessor.REMOVE_TAG_PROCESSOR_NAME, snapshot.getName());

        List<NameValuePair> parameters = snapshot.getParameters();

        assertEquals(4, parameters.size());

        assertEquals(Variables.DEFAULT_INPUT_VARIABLE_NAME, parameters.get(0).getName());
        assertEquals("test", parameters.get(0).getValue());

        assertEquals(GetGroupProcessor.PATTERN_PARAMETER_NAME, parameters.get(1).getName());
        assertEquals("pattern", parameters.get(1).getValue());

        assertEquals(GetGroupProcessor.OCCURRENCES_PARAMETER_NAME, parameters.get(2).getName());
        assertEquals("[0, 2, 789]", parameters.get(2).getValue());

        assertEquals(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, parameters.get(3).getName());
        assertEquals("content2", parameters.get(3).getValue());
    }

}
