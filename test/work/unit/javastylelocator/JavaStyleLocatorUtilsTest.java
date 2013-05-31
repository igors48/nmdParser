package work.unit.javastylelocator;

import constructor.dom.locator.JavaStyleLocatorUtils;
import constructor.dom.locator.Mask;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 26.07.2009
 */
public class JavaStyleLocatorUtilsTest extends TestCase {

    public JavaStyleLocatorUtilsTest(final String _s) {
        super(_s);
    }

    // �������������� ����

    public void testSmoke() {
        List<String> list = newArrayList();
        list.add("test");
        list.add("supertest");

        Mask mask = new Mask(new ArrayList<String>(), new ArrayList<String>());

        List<String> result = JavaStyleLocatorUtils.select(list, mask);

        assertEquals(2, result.size());
        assertTrue(result.contains("test"));
        assertTrue(result.contains("supertest"));
    }

    // ���� c ����� ������

    public void testWithOneMask() {
        List<String> list = newArrayList();
        list.add("test.test.object");
        list.add("supertest");

        List<String> masks = newArrayList();
        masks.add("test.test");
        Mask mask = new Mask(masks, new ArrayList<String>());

        List<String> result = JavaStyleLocatorUtils.select(list, mask);

        assertEquals(1, result.size());
        assertTrue(result.contains("test.test.object"));
    }

    // ���� c ����� ��� ����� ������

    public void testWithManyMask() {
        List<String> list = newArrayList();
        list.add("test.test.object");
        list.add("real.test.object");
        list.add("supertest");

        List<String> masks = newArrayList();
        masks.add("test.test");
        masks.add("real");
        Mask mask = new Mask(masks, new ArrayList<String>());

        List<String> result = JavaStyleLocatorUtils.select(list, mask);

        assertEquals(2, result.size());
        assertTrue(result.contains("test.test.object"));
        assertTrue(result.contains("real.test.object"));
    }

    // ����������� ���� � �������� ���� �� �� � ����� ��������

    public void testGetNotRootSimplerPath() {
        assertEquals("one/two/three/", JavaStyleLocatorUtils.getPathToSimpler("one.two.three.simpler"));
    }

    // ����������� ���� � �������� ���� �� � ����� ��������

    public void testGetRootSimplerPath() {
        assertEquals("", JavaStyleLocatorUtils.getPathToSimpler("simpler"));
    }

    // ����������� ����� �������� ���� �� �� � ����� ��������

    public void testGetNotRootSimplerFileName() {
        assertEquals("simpler.xml", JavaStyleLocatorUtils.getSimplerFileName("one.two.three.simpler"));
    }

    // ����������� ����� �������� ���� �� � ����� ��������

    public void testGetRootSimplerFileName() {
        assertEquals("simpler.xml", JavaStyleLocatorUtils.getSimplerFileName("simpler"));
    }
}