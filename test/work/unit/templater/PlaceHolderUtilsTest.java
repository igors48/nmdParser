package work.unit.templater;

import junit.framework.TestCase;
import app.templater.PlaceHolderUtils;
import app.templater.PlaceHolderBoundary;
import app.templater.PlaceHolderInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 27.09.2009
 */
public class PlaceHolderUtilsTest extends TestCase {

    public PlaceHolderUtilsTest(final String _s) {
        super(_s);
    }

    // строка без плейсхолдеров не меняется
    public void testSmoke(){
        String result = PlaceHolderUtils.replace("test", new HashMap<String, String>());

        assertEquals("test", result);
    }

    // один плейсхолдер
    public void testOnePlaceHolder(){
        Map<String, String> values = new HashMap<String, String>();
        values.put("value", "data");

        String placeholder = PlaceHolderUtils.getPlaceholder("value");
        String result = PlaceHolderUtils.replace("test " + placeholder, values);

        assertEquals("test data", result);
    }

    // три одинаковых плейсхолдера
    public void testThreePlaceHolder(){
        Map<String, String> values = new HashMap<String, String>();
        values.put("value", "data");

        String result = PlaceHolderUtils.replace("test " + PlaceHolderUtils.getPlaceholder("value") +
                "+" + PlaceHolderUtils.getPlaceholder("value") + "=" + PlaceHolderUtils.getPlaceholder("value"), values);

        assertEquals("test data+data=data", result);
    }
    
    // при остутствии данных плейсхолдер не меняется
    public void testSomeDifferent(){
        Map<String, String> values = new HashMap<String, String>();
        values.put("value", "data");
        values.put("avg", "5");

        String result = PlaceHolderUtils.replace("test " + PlaceHolderUtils.getPlaceholder("value") + "+"
                + PlaceHolderUtils.getPlaceholder("mean") + "=" + PlaceHolderUtils.getPlaceholder("avg"), values);

        assertEquals("test data+" + PlaceHolderUtils.getPlaceholder("mean") +  "=5", result);
    }

    // генерация плейсхолдера со значением по умолчанию
    public void testGetPlaceHolderWithDefault() {
        String result = PlaceHolderUtils.getPlaceholder("name", "default");

        assertEquals("${name|default}", result);
    }
    
    // генерация плейсхолдера с пустым значением по умолчанию
    public void testGetPlaceHolderWithEmptyDefault() {
        String result = PlaceHolderUtils.getPlaceholder("name", "");

        assertEquals("${name|}", result);
    }

    // генерация плейсхолдера с null значением по умолчанию
    public void testGetPlaceHolderWithNullDefault() {
        String result = PlaceHolderUtils.getPlaceholder("name", null);

        assertEquals("${name}", result);
    }

    // извлечение значения по умолчанию
    public void testParseDefaultValue() {
        String result = PlaceHolderUtils.parseDefaultValue("name|val");

        assertEquals("val", result);
    }

    // извлечение пустого значения по умолчанию
    public void testParseEmptyDefaultValue() {
        String result = PlaceHolderUtils.parseDefaultValue("name|");

        assertEquals("", result);
    }
    
    // обработка отсутствия значения по умолчанию
    public void testParseNoDefaultValue() {
        String result = PlaceHolderUtils.parseDefaultValue("name");

        assertNull(result);
    }

    // обработка отсутствия имени
    public void testParseNoNameDefaultValue() {
        String result = PlaceHolderUtils.parseDefaultValue("|val");

        assertEquals("val", result);
    }
    
    // обработка отсутствия имени и значения по умолчанию
    public void testParseNoNameNoDefaultValue() {
        String result = PlaceHolderUtils.parseDefaultValue("|");

        assertEquals("", result);
    }

    // извлечение имени - задано имя и значение по умолчанию
    public void testParseNameWithDefaultValue() {
        String result = PlaceHolderUtils.parseName("name|val");

        assertEquals("name", result);
    }

    // извлечение имени - задано имя без значения по умолчанию
    public void testParseNameWithoutDefaultValue() {
        String result = PlaceHolderUtils.parseName("name");

        assertEquals("name", result);
    }
    
    // извлечение имени - задано имя с пустым значением по умолчанию
    public void testParseNameWithEmptyDefaultValue() {
        String result = PlaceHolderUtils.parseName("name|");

        assertEquals("name", result);
    }

    // извлечение имени - не задано имя и пустое значение по умолчанию
    public void testParseEmptyNameWithEmptyDefaultValue() {
        String result = PlaceHolderUtils.parseName("|");

        assertEquals("", result);
    }

    // извлечение границ плейсхолдера
    public void testGetBoundary() {
        PlaceHolderBoundary result = PlaceHolderUtils.getBoundary(new StringBuilder("01${45}7"), 0);

        assertNotNull(result);
        assertEquals(2, result.getStart());
        assertEquals(6, result.getStop());
    }

    // возврат null если плейсхолдеров нет
    public void testGetBoundaryNoPlaceHolders() {
        PlaceHolderBoundary result = PlaceHolderUtils.getBoundary(new StringBuilder("017"), 0);

        assertNull(result);
    }

    // malformed плейсхолдер не ловится - случай 1
    public void testMalformedNotDetectedVar1() {
        PlaceHolderBoundary result = PlaceHolderUtils.getBoundary(new StringBuilder("01${7sdfsdf"), 0);

        assertNull(result);
    }
    
    // malformed плейсхолдер не ловится - случай 2
    public void testMalformedNotDetectedVar2() {
        PlaceHolderBoundary result = PlaceHolderUtils.getBoundary(new StringBuilder("017sdf}sdf"), 0);

        assertNull(result);
    }

    // извлекаются границы первого попавшегося плейсхолдера
    public void testGetFirstBoundary() {
        PlaceHolderBoundary result = PlaceHolderUtils.getBoundary(new StringBuilder("01${45}7${asdf}asdfas"), 0);

        assertNotNull(result);
        assertEquals(2, result.getStart());
        assertEquals(6, result.getStop());
    }

    // ловится сначала первый плейсхолдер, а потом второй
    public void testGetBoundarySequental() {
        PlaceHolderBoundary result = PlaceHolderUtils.getBoundary(new StringBuilder("01${45}7${asdf}asdfas"), 0);

        int stop = result.getStop();

        result = PlaceHolderUtils.getBoundary(new StringBuilder("01${45}7${asdf}asdfas"), stop);

        assertNotNull(result);
        assertEquals(8, result.getStart());
        assertEquals(14, result.getStop());
    }

    // два плейсхолдера подряд
    public void testOneAfterOne() {
        PlaceHolderBoundary result = PlaceHolderUtils.getBoundary(new StringBuilder("asd${45}${asdf}asd"), 0);

        assertNotNull(result);
        assertEquals(3, result.getStart());
        assertEquals(7, result.getStop());

        int stop = result.getStop();

        result = PlaceHolderUtils.getBoundary(new StringBuilder("01${45}7${asdf}asdfas"), stop);

        assertNotNull(result);
        assertEquals(8, result.getStart());
        assertEquals(14, result.getStop());
    }

    // короткая строка два плейсхолдера подряд первый заменяется на один символ
    public void testOneAfterOneSmallData() {
        Map<String, String> values = new HashMap<String, String>();
        values.put("first", "f");
        values.put("second", "s");

        String result = PlaceHolderUtils.replace("${first}${second}", values);

        assertEquals("fs", result);
    }

    // тест получения информации о плейсхолдерах: плейсхолдеров нет
    public void testPlaceHolderInfoNoPlaceHolders() {
        List<PlaceHolderInfo> result = PlaceHolderUtils.getPlaceHolderInfos("abs");

        assertEquals(0, result.size());
    }

    // тест получения информации о плейсхолдерах: один плейсхолдер без дефолтного значения
    public void testPlaceHolderInfoOnePlaceHoldersWithoutDef() {
        List<PlaceHolderInfo> result = PlaceHolderUtils.getPlaceHolderInfos("abs${one}");

        assertEquals(1, result.size());
        assertEquals("one", result.get(0).getName());
        assertEquals(null, result.get(0).getDefValue());
    }

    // тест получения информации о плейсхолдерах: один плейсхолдер с дефолтным значением
    public void testPlaceHolderInfoOnePlaceHoldersWithDef() {
        List<PlaceHolderInfo> result = PlaceHolderUtils.getPlaceHolderInfos("abs${one|two}");

        assertEquals(1, result.size());
        assertEquals("one", result.get(0).getName());
        assertEquals("two", result.get(0).getDefValue());
    }

    // тест получения информации о плейсхолдерах: три плейсхолдера
    public void testPlaceHolderInfohThreePlaceHolders() {
        List<PlaceHolderInfo> result = PlaceHolderUtils.getPlaceHolderInfos("${one|two}asd${three}asdfasd${four|five}");

        assertEquals(3, result.size());
        assertEquals("one", result.get(0).getName());
        assertEquals("two", result.get(0).getDefValue());
        assertEquals("three", result.get(1).getName());
        assertEquals(null, result.get(1).getDefValue());
        assertEquals("four", result.get(2).getName());
        assertEquals("five", result.get(2).getDefValue());
    }
}
