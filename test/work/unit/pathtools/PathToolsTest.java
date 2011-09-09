package work.unit.pathtools;

import junit.framework.TestCase;
import util.PathTools;

/**
 * @author Igor Usenko
 *         Date: 31.03.2009
 */
public class PathToolsTest extends TestCase {

    public PathToolsTest(String _s) {
        super(_s);
    }

    // �������� �� ����������� ���������������� ����
    public void testNormalized() {
        String fixture = "E:\\s48\\";

        assertEquals(fixture, PathTools.normalize(fixture));
    }

    // �������� �� ������������ ������������������ ����
    public void testNotNormalized() {
        String fixture = "E:\\s48";

        assertEquals("E:\\s48\\", PathTools.normalize(fixture));
    }

    // ���������� ������� � ��� ����� � �����������
    public void testAppendTimeWithExtension() {
        String fixture = "name.ext";
        String result = PathTools.appendDateTimeToName(fixture);

        assertTrue(fixture.length() < result.length());
    }

    // ���������� ������� � ��� ����� ��� ����������
    public void testAppendTimeWithoutExtension() {
        String fixture = "name";
        String result = PathTools.appendDateTimeToName(fixture);

        assertTrue(fixture.length() < result.length());
    }

    // ���������� ������� � ��� ����� � �������
    public void testAppendTimeWithPoints() {
        String fixture = "name.ext.ext";
        String result = PathTools.appendDateTimeToName(fixture);

        assertTrue(fixture.length() < result.length());
    }
}
