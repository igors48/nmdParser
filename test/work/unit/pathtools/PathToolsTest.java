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

    // проверка на неизменение нормализованного пути
    public void testNormalized() {
        String fixture = "E:\\s48\\";

        assertEquals(fixture, PathTools.normalize(fixture));
    }

    // проверка на нормализацию ненормализованного пути
    public void testNotNormalized() {
        String fixture = "E:\\s48";

        assertEquals("E:\\s48\\", PathTools.normalize(fixture));
    }

    // добавление времени в имя файла с расширением
    public void testAppendTimeWithExtension() {
        String fixture = "name.ext";
        String result = PathTools.appendDateTimeToName(fixture);

        assertTrue(fixture.length() < result.length());
    }

    // добавление времени в имя файла без расширения
    public void testAppendTimeWithoutExtension() {
        String fixture = "name";
        String result = PathTools.appendDateTimeToName(fixture);

        assertTrue(fixture.length() < result.length());
    }

    // добавление времени в имя файла с точками
    public void testAppendTimeWithPoints() {
        String fixture = "name.ext.ext";
        String result = PathTools.appendDateTimeToName(fixture);

        assertTrue(fixture.length() < result.length());
    }
}
