package work.unit.processmanager;

import junit.framework.TestCase;
import app.workingarea.process.ProcessManagerUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Igor Usenko
 *         Date: 22.11.2009
 */
public class ProcessManagerUtilsTest extends TestCase {

    public ProcessManagerUtilsTest(final String _s) {
        super(_s);
    }

    // строка без плейсхолдеров не меняется
    public void testSmoke(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("1", "1val");

        assertEquals("test", ProcessManagerUtils.createCommandLine("test", map));
    }

    // тест одного плейсхолдера и одного значения
    public void testOneValueOneHolder(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("d", "1val");

        assertEquals("test1val", ProcessManagerUtils.createCommandLine("test%d", map));
    }

    // тест двух плейсхолдеров и одного значения
    public void testOneValueTwoHolder(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("d", "1val");

        assertEquals("1valtest1val", ProcessManagerUtils.createCommandLine("%dtest%d", map));
    }

    // тест двух плейсхолдеров и двух значений
    public void testTwoValueTwoHolder(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("d", "1val");
        map.put("f", "2val");

        assertEquals("2valtest1val", ProcessManagerUtils.createCommandLine("%ftest%d", map));
    }

    // тест двух разных плейсхолдеров и одного значений
    public void testOneValueTwoDiffHolder(){
        Map<String, String> map = new HashMap<String, String>();
        map.put("d", "1val");

        assertEquals("%ftest1val", ProcessManagerUtils.createCommandLine("%ftest%d", map));
    }

    // тест точек в шаблоне и параметрах
    public void testWithPoints() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("f", "bash.fb2");
        map.put("d", "E:\\Users\\Igor\\IdeaProjects\\Nomad\\.\\workarea\\root\\");
        map.put("n", "E:\\Users\\Igor\\IdeaProjects\\Nomad\\.\\workarea\\root\\bash.fb2");

        assertEquals("bash.fb2 E:\\Users\\Igor\\IdeaProjects\\Nomad\\.\\workarea\\root\\ E:\\Users\\Igor\\IdeaProjects\\Nomad\\.\\workarea\\root\\bash.fb2", ProcessManagerUtils.createCommandLine("%f %d %n", map));
    }
}
