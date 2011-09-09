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

    // ������ ��� ������������� �� ��������
    public void testSmoke(){
        String result = PlaceHolderUtils.replace("test", new HashMap<String, String>());

        assertEquals("test", result);
    }

    // ���� �����������
    public void testOnePlaceHolder(){
        Map<String, String> values = new HashMap<String, String>();
        values.put("value", "data");

        String placeholder = PlaceHolderUtils.getPlaceholder("value");
        String result = PlaceHolderUtils.replace("test " + placeholder, values);

        assertEquals("test data", result);
    }

    // ��� ���������� ������������
    public void testThreePlaceHolder(){
        Map<String, String> values = new HashMap<String, String>();
        values.put("value", "data");

        String result = PlaceHolderUtils.replace("test " + PlaceHolderUtils.getPlaceholder("value") +
                "+" + PlaceHolderUtils.getPlaceholder("value") + "=" + PlaceHolderUtils.getPlaceholder("value"), values);

        assertEquals("test data+data=data", result);
    }
    
    // ��� ���������� ������ ����������� �� ��������
    public void testSomeDifferent(){
        Map<String, String> values = new HashMap<String, String>();
        values.put("value", "data");
        values.put("avg", "5");

        String result = PlaceHolderUtils.replace("test " + PlaceHolderUtils.getPlaceholder("value") + "+"
                + PlaceHolderUtils.getPlaceholder("mean") + "=" + PlaceHolderUtils.getPlaceholder("avg"), values);

        assertEquals("test data+" + PlaceHolderUtils.getPlaceholder("mean") +  "=5", result);
    }

    // ��������� ������������ �� ��������� �� ���������
    public void testGetPlaceHolderWithDefault() {
        String result = PlaceHolderUtils.getPlaceholder("name", "default");

        assertEquals("${name|default}", result);
    }
    
    // ��������� ������������ � ������ ��������� �� ���������
    public void testGetPlaceHolderWithEmptyDefault() {
        String result = PlaceHolderUtils.getPlaceholder("name", "");

        assertEquals("${name|}", result);
    }

    // ��������� ������������ � null ��������� �� ���������
    public void testGetPlaceHolderWithNullDefault() {
        String result = PlaceHolderUtils.getPlaceholder("name", null);

        assertEquals("${name}", result);
    }

    // ���������� �������� �� ���������
    public void testParseDefaultValue() {
        String result = PlaceHolderUtils.parseDefaultValue("name|val");

        assertEquals("val", result);
    }

    // ���������� ������� �������� �� ���������
    public void testParseEmptyDefaultValue() {
        String result = PlaceHolderUtils.parseDefaultValue("name|");

        assertEquals("", result);
    }
    
    // ��������� ���������� �������� �� ���������
    public void testParseNoDefaultValue() {
        String result = PlaceHolderUtils.parseDefaultValue("name");

        assertNull(result);
    }

    // ��������� ���������� �����
    public void testParseNoNameDefaultValue() {
        String result = PlaceHolderUtils.parseDefaultValue("|val");

        assertEquals("val", result);
    }
    
    // ��������� ���������� ����� � �������� �� ���������
    public void testParseNoNameNoDefaultValue() {
        String result = PlaceHolderUtils.parseDefaultValue("|");

        assertEquals("", result);
    }

    // ���������� ����� - ������ ��� � �������� �� ���������
    public void testParseNameWithDefaultValue() {
        String result = PlaceHolderUtils.parseName("name|val");

        assertEquals("name", result);
    }

    // ���������� ����� - ������ ��� ��� �������� �� ���������
    public void testParseNameWithoutDefaultValue() {
        String result = PlaceHolderUtils.parseName("name");

        assertEquals("name", result);
    }
    
    // ���������� ����� - ������ ��� � ������ ��������� �� ���������
    public void testParseNameWithEmptyDefaultValue() {
        String result = PlaceHolderUtils.parseName("name|");

        assertEquals("name", result);
    }

    // ���������� ����� - �� ������ ��� � ������ �������� �� ���������
    public void testParseEmptyNameWithEmptyDefaultValue() {
        String result = PlaceHolderUtils.parseName("|");

        assertEquals("", result);
    }

    // ���������� ������ ������������
    public void testGetBoundary() {
        PlaceHolderBoundary result = PlaceHolderUtils.getBoundary(new StringBuilder("01${45}7"), 0);

        assertNotNull(result);
        assertEquals(2, result.getStart());
        assertEquals(6, result.getStop());
    }

    // ������� null ���� ������������� ���
    public void testGetBoundaryNoPlaceHolders() {
        PlaceHolderBoundary result = PlaceHolderUtils.getBoundary(new StringBuilder("017"), 0);

        assertNull(result);
    }

    // malformed ����������� �� ������� - ������ 1
    public void testMalformedNotDetectedVar1() {
        PlaceHolderBoundary result = PlaceHolderUtils.getBoundary(new StringBuilder("01${7sdfsdf"), 0);

        assertNull(result);
    }
    
    // malformed ����������� �� ������� - ������ 2
    public void testMalformedNotDetectedVar2() {
        PlaceHolderBoundary result = PlaceHolderUtils.getBoundary(new StringBuilder("017sdf}sdf"), 0);

        assertNull(result);
    }

    // ����������� ������� ������� ����������� ������������
    public void testGetFirstBoundary() {
        PlaceHolderBoundary result = PlaceHolderUtils.getBoundary(new StringBuilder("01${45}7${asdf}asdfas"), 0);

        assertNotNull(result);
        assertEquals(2, result.getStart());
        assertEquals(6, result.getStop());
    }

    // ������� ������� ������ �����������, � ����� ������
    public void testGetBoundarySequental() {
        PlaceHolderBoundary result = PlaceHolderUtils.getBoundary(new StringBuilder("01${45}7${asdf}asdfas"), 0);

        int stop = result.getStop();

        result = PlaceHolderUtils.getBoundary(new StringBuilder("01${45}7${asdf}asdfas"), stop);

        assertNotNull(result);
        assertEquals(8, result.getStart());
        assertEquals(14, result.getStop());
    }

    // ��� ������������ ������
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

    // �������� ������ ��� ������������ ������ ������ ���������� �� ���� ������
    public void testOneAfterOneSmallData() {
        Map<String, String> values = new HashMap<String, String>();
        values.put("first", "f");
        values.put("second", "s");

        String result = PlaceHolderUtils.replace("${first}${second}", values);

        assertEquals("fs", result);
    }

    // ���� ��������� ���������� � �������������: ������������� ���
    public void testPlaceHolderInfoNoPlaceHolders() {
        List<PlaceHolderInfo> result = PlaceHolderUtils.getPlaceHolderInfos("abs");

        assertEquals(0, result.size());
    }

    // ���� ��������� ���������� � �������������: ���� ����������� ��� ���������� ��������
    public void testPlaceHolderInfoOnePlaceHoldersWithoutDef() {
        List<PlaceHolderInfo> result = PlaceHolderUtils.getPlaceHolderInfos("abs${one}");

        assertEquals(1, result.size());
        assertEquals("one", result.get(0).getName());
        assertEquals(null, result.get(0).getDefValue());
    }

    // ���� ��������� ���������� � �������������: ���� ����������� � ��������� ���������
    public void testPlaceHolderInfoOnePlaceHoldersWithDef() {
        List<PlaceHolderInfo> result = PlaceHolderUtils.getPlaceHolderInfos("abs${one|two}");

        assertEquals(1, result.size());
        assertEquals("one", result.get(0).getName());
        assertEquals("two", result.get(0).getDefValue());
    }

    // ���� ��������� ���������� � �������������: ��� ������������
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
