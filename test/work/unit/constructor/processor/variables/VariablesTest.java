package work.unit.constructor.processor.variables;

import debug.snapshot.ValueChangedPair;
import debug.snapshot.VariablesSnapshot;
import junit.framework.TestCase;
import variables.Variables;

import java.util.List;
import java.util.Map;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 20.05.2009
 */
public class VariablesTest extends TestCase {

    public VariablesTest(final String _s) {
        super(_s);
    }

    // первоначальный тест put/get

    public void testSmoke() {
        Variables variables = new Variables();

        variables.put("name", "value");

        assertEquals("value", variables.get("name"));
    }

    // тест чтения отстутствующей переменной

    public void testNonExistent() {
        Variables variables = new Variables();

        assertNull(variables.get("name"));
    }

    // тест на перезапись существующего значения

    public void testOwerwrite() {
        Variables variables = new Variables();

        variables.put("name", "value");
        variables.put("name", "overwrite");

        assertEquals("overwrite", variables.get("name"));
    }

    // тест на чтение/запись индексированной переменной

    public void testIndexedPutGet() {
        Variables variables = new Variables();

        variables.put("name", 48, "value");

        assertEquals("value", variables.get("name", 48));
    }

    // тест чтения отстутствующей индексированной переменной

    public void testIndexedNonExistent() {
        Variables variables = new Variables();

        assertNull(variables.get("name", 48));
    }

    // тест на перезапись существующего значения индексированной переменной

    public void testIndexedOwerwrite() {
        Variables variables = new Variables();

        variables.put("name", 48, "value");
        variables.put("name", 48, "overwrite");

        assertEquals("overwrite", variables.get("name", 48));
    }

    // тест на нулевой размер несуществующей переменной

    public void testNonExistentZeroSize() {
        Variables variables = new Variables();

        assertEquals(0, variables.getSize("name"));
    }

    // тест на размер переменной

    public void testVariableSize() {
        Variables variables = new Variables();

        variables.put("name", 0, "value0");
        variables.put("name", 48, "value48");

        assertEquals(2, variables.getSize("name"));
    }

    // тест на правильность обработки списочной переменной

    public void testVariableList() {
        Variables variables = new Variables();

        variables.put("name", 0, "value0");
        variables.put("name", 48, "value48");

        assertEquals("value0", variables.get("name", 0));
        assertEquals("value48", variables.get("name", 48));
    }

    // тест сохранения в переменной списка целиком

    public void testPutAll() {
        List<String> datas = newArrayList();
        datas.add("1");
        datas.add("2");
        datas.add("3");

        Variables variables = new Variables();
        variables.putAll("test", datas);

        assertEquals(3, variables.getSize("test"));
        assertEquals("1", variables.get("test", 0));
        assertEquals("2", variables.get("test", 1));
        assertEquals("3", variables.get("test", 2));
    }

    // тест формирования снапшота

    public void testSnapshot() {
        List<String> firstDatas = newArrayList();
        firstDatas.add("1");

        Variables variables = new Variables();
        variables.putAll("first", firstDatas);
        variables.put("second", 48, "secondDatas");

        VariablesSnapshot snapshot = (VariablesSnapshot) variables.getSnapshot();

        assertNotNull(snapshot);

        Map<String, Map<Integer, ValueChangedPair>> items = snapshot.getItems();
        assertEquals(2, items.size());

        Map<Integer, ValueChangedPair> first = items.get("first");
        assertNotNull(first);
        ValueChangedPair firstPair = first.get(0);
        assertNotNull(firstPair);
        assertEquals("1", firstPair.getValue());
        assertTrue(firstPair.isChanged());

        Map<Integer, ValueChangedPair> second = items.get("second");
        assertNotNull(second);
        ValueChangedPair secondPair = second.get(48);
        assertNotNull(secondPair);
        assertEquals("secondDatas", secondPair.getValue());
        assertTrue(secondPair.isChanged());
    }

    // тест сброса флагов модификации после получения снапшота

    public void testChangeLogReset() {
        List<String> firstDatas = newArrayList();
        firstDatas.add("1");

        Variables variables = new Variables();
        variables.putAll("first", firstDatas);
        variables.put("second", 48, "secondDatas");

        VariablesSnapshot snapshot = (VariablesSnapshot) variables.getSnapshot();
        // так надо! смысл в двух запросах подряд
        snapshot = (VariablesSnapshot) variables.getSnapshot();

        assertNotNull(snapshot);

        Map<String, Map<Integer, ValueChangedPair>> items = snapshot.getItems();
        assertEquals(2, items.size());

        Map<Integer, ValueChangedPair> first = items.get("first");
        assertNotNull(first);
        ValueChangedPair firstPair = first.get(0);
        assertNotNull(firstPair);
        assertFalse(firstPair.isChanged());

        Map<Integer, ValueChangedPair> second = items.get("second");
        assertNotNull(second);
        ValueChangedPair secondPair = second.get(48);
        assertNotNull(secondPair);
        assertFalse(secondPair.isChanged());
    }

    // тест добавления списком

    public void testAppendList() {
        Variables variables = new Variables();
        variables.put("result", 0, "0e");
        variables.put("result", 48, "48e");

        List<String> appended = newArrayList();
        appended.add("1a");
        appended.add("2a");

        variables.appendAll("result", appended);

        assertEquals(4, variables.getSize("result"));

        assertEquals("0e", variables.get("result", 0));
        assertEquals("48e", variables.get("result", 48));
        assertEquals("1a", variables.get("result", 49));
        assertEquals("2a", variables.get("result", 50));
    }

    // тест добавления списком в несуществующую

    public void testAppendListToNonExistent() {
        Variables variables = new Variables();

        List<String> appended = newArrayList();
        appended.add("1a");
        appended.add("2a");

        variables.appendAll("result", appended);

        assertEquals(2, variables.getSize("result"));

        assertEquals("1a", variables.get("result", 0));
        assertEquals("2a", variables.get("result", 1));
    }

}
