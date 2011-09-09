package work.unit.constructor.processor;

import constructor.objects.processor.OccurrenceSet;
import constructor.objects.processor.VariableProcessor;
import constructor.objects.processor.extract_tag.ExtractTagProcessor;
import debug.snapshot.NameValuePair;
import debug.snapshot.ProcessorSnapshot;
import junit.framework.TestCase;
import variables.Variables;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 29.04.2009
 */
public class ExtractTagProcessorTest extends TestCase {

    public static String FIXTURE = "trashtrashtrash<td colspan=\"2\"><span class=\"postbody\">asdfrg</span>aaas</td>opaopaopa<td colspan=\"2\"><span class=\"postbody\">asdfrg2</span>aaQQQas</td>";
    public static String LIST_FIXTURE = "trashtrashtrash<td colspan=\"2\">asdfrg1</td>opaopaopa<td colspan=\"2\">asdfrg2</td><td colspan=\"2\">asdfrg3</td>a";

    public static String REAL_FIXTURE_001 = "pageNav={0=  <table class=\"tborder\" cellpadding=\"3\" cellspacing=\"1\" border=\"0\">  <tr>   <td class=\"vbmenu_control\" style=\"font-weight:normal\">Страница 1 из 5</td>          <td class=\"alt2\"><span class=\"smallfont\" title=\"Показано с 1 по 20 из 94.\"><strong>1</strong></span></td>   <td class=\"alt1\"><a class=\"smallfont\" href=\"showthread.php?s=7c38fbb194f1cc59462b195b9acdfb04&amp;t=216999&amp;page=2\" title=\"с 21 по 40 из 94\">2</a></td><td class=\"alt1\"><a class=\"smallfont\" href=\"showthread.php?s=7c38fbb194f1cc59462b195b9acdfb04&amp;t=216999&amp;page=3\" title=\"с 41 по 60 из 94\">3</a></td><td class=\"alt1\"><a class=\"smallfont\" href=\"showthread.php?s=7c38fbb194f1cc59462b195b9acdfb04&amp;t=216999&amp;page=4\" title=\"с 61 по 80 из 94\">4</a></td><td class=\"alt1\"><a class=\"smallfont\" href=\"showthread.php?s=7c38fbb194f1cc59462b195b9acdfb04&amp;t=216999&amp;page=5\" title=\"с 81 по 94 из 94\">5</a></td>   <td class=\"alt1\"><a rel=\"next\" class=\"smallfont\" href=\"showthread.php?s=7c38fbb194f1cc59462b195b9acdfb04&amp;t=216999&amp;page=2\" title=\"Следующая страница - с 21 по 40 из 94\">&gt;</a></td>      <td class=\"vbmenu_control\" title=\"showthread.php?s=7c38fbb194f1cc59462b195b9acdfb04&amp;t=216999\"><a name=\"PageNav\"></a></td>  </tr>  </table>  }";

    public ExtractTagProcessorTest(String _s) {
        super(_s);
    }

    // первоначальный тест
    public void testSmoke() throws VariableProcessor.VariableProcessorException {
        OccurrenceSet occurrenceSet = new OccurrenceSet();
        ExtractTagProcessor processor = new ExtractTagProcessor("", "<b>", occurrenceSet, "");
        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, "<b>content</b>");

        processor.process(variables);

        assertEquals("content", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
    }

    // тест сложного тега
    public void testComplexTag() throws VariableProcessor.VariableProcessorException {
        OccurrenceSet occurrenceSet = new OccurrenceSet();
        ExtractTagProcessor processor = new ExtractTagProcessor("", "<td colspan=\"\\d\">", occurrenceSet, "");
        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, FIXTURE);

        processor.process(variables);

        assertEquals("<span class=\"postbody\">asdfrg</span>aaas", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
    }

    // тест на выборку нужного вхождения
    public void testOccurence() throws VariableProcessor.VariableProcessorException {
        OccurrenceSet occurrenceSet = new OccurrenceSet();
        occurrenceSet.add(1);
        ExtractTagProcessor processor = new ExtractTagProcessor("", "<td colspan=\"\\d\">", occurrenceSet, "");
        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, FIXTURE);

        processor.process(variables);

        assertEquals("<span class=\"postbody\">asdfrg2</span>aaQQQas", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
    }

    // тест на выборку несуществующего вхождения
    public void testNonExistentOccurence() throws VariableProcessor.VariableProcessorException {
        OccurrenceSet occurrenceSet = new OccurrenceSet();
        occurrenceSet.add(50);
        ExtractTagProcessor processor = new ExtractTagProcessor("", "<td colspan=\"\\d\">", occurrenceSet, "");
        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, FIXTURE);

        processor.process(variables);

        assertEquals("", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
    }

    // тест на выборку списка вхождений
    public void testListOccurences() throws VariableProcessor.VariableProcessorException {
        OccurrenceSet occurrenceSet = new OccurrenceSet();
        occurrenceSet.add(0);
        occurrenceSet.add(2);
        ExtractTagProcessor processor = new ExtractTagProcessor("", "<td colspan=\"\\d\">", occurrenceSet, "");
        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, LIST_FIXTURE);

        processor.process(variables);

        assertEquals(2, variables.getSize(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
        assertEquals("asdfrg1", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 0));
        assertEquals("asdfrg3", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 1));
    }

    // реальный тест на выборку
    public void testReal001() throws VariableProcessor.VariableProcessorException {
        OccurrenceSet occurrenceSet = new OccurrenceSet();
        occurrenceSet.add(0);
        ExtractTagProcessor processor = new ExtractTagProcessor("", "<td class=\"vbmenu_control\" style=\"font-weight:normal\">", occurrenceSet, "");
        Variables variables = new Variables();
        variables.put(Variables.DEFAULT_INPUT_VARIABLE_NAME, REAL_FIXTURE_001);

        processor.process(variables);

        assertEquals(1, variables.getSize(Variables.DEFAULT_OUTPUT_VARIABLE_NAME));
        assertEquals("Страница 1 из 5", variables.get(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, 0));
    }

    // тест снапшота с заданными вхождениями
    public void testSnapshotWithOccurrences() {
        OccurrenceSet occurrenceSet = new OccurrenceSet();
        occurrenceSet.add(0);
        occurrenceSet.add(2);
        occurrenceSet.add(789);

        ExtractTagProcessor processor = new ExtractTagProcessor("test", "pattern", occurrenceSet, "content2");

        ProcessorSnapshot snapshot = (ProcessorSnapshot) processor.getSnapshot();

        assertEquals(ExtractTagProcessor.EXTRACT_TAG_PROCESSOR_NAME, snapshot.getName());

        List<NameValuePair> parameters = snapshot.getParameters();

        assertEquals(4, parameters.size());

        assertEquals(Variables.DEFAULT_INPUT_VARIABLE_NAME, parameters.get(0).getName());
        assertEquals("test", parameters.get(0).getValue());

        assertEquals(ExtractTagProcessor.PATTERN_PARAMETER_NAME, parameters.get(1).getName());
        assertEquals("pattern", parameters.get(1).getValue());

        assertEquals(ExtractTagProcessor.OCCURRENCES_PARAMETER_NAME, parameters.get(2).getName());
        assertEquals("[0, 2, 789]", parameters.get(2).getValue());

        assertEquals(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, parameters.get(3).getName());
        assertEquals("content2", parameters.get(3).getValue());
    }

    // тест снапшота "подходит все"
    public void testSnapshotAllAcceptable() {
        OccurrenceSet occurrenceSet = new OccurrenceSet();
        occurrenceSet.add(-1);

        ExtractTagProcessor processor = new ExtractTagProcessor("test", "pattern", occurrenceSet, "content2");

        ProcessorSnapshot snapshot = (ProcessorSnapshot) processor.getSnapshot();

        assertEquals(ExtractTagProcessor.EXTRACT_TAG_PROCESSOR_NAME, snapshot.getName());

        List<NameValuePair> parameters = snapshot.getParameters();

        assertEquals(4, parameters.size());

        assertEquals(Variables.DEFAULT_INPUT_VARIABLE_NAME, parameters.get(0).getName());
        assertEquals("test", parameters.get(0).getValue());

        assertEquals(ExtractTagProcessor.PATTERN_PARAMETER_NAME, parameters.get(1).getName());
        assertEquals("pattern", parameters.get(1).getValue());

        assertEquals(ExtractTagProcessor.OCCURRENCES_PARAMETER_NAME, parameters.get(2).getName());
        assertEquals(OccurrenceSet.ALL_ACCEPTABLE_TOKEN, parameters.get(2).getValue());

        assertEquals(Variables.DEFAULT_OUTPUT_VARIABLE_NAME, parameters.get(3).getName());
        assertEquals("content2", parameters.get(3).getValue());
    }
}
