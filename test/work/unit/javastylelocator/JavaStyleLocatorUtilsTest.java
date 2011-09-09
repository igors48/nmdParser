package work.unit.javastylelocator;

import constructor.dom.locator.JavaStyleLocatorUtils;
import constructor.dom.locator.Mask;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 26.07.2009
 */
public class JavaStyleLocatorUtilsTest extends TestCase {

    public JavaStyleLocatorUtilsTest(final String _s) {
        super(_s);
    }

    // первоначальный тест
    public void testSmoke() {
        List<String> list = new ArrayList<String>();
        list.add("test");
        list.add("supertest");

        Mask mask = new Mask(new ArrayList<String>(), new ArrayList<String>());

        List<String> result = JavaStyleLocatorUtils.select(list, mask);

        assertEquals(2, result.size());
        assertTrue(result.contains("test"));
        assertTrue(result.contains("supertest"));
    }

    // тест c одной маской
    public void testWithOneMask() {
        List<String> list = new ArrayList<String>();
        list.add("test.test.object");
        list.add("supertest");

        List<String> masks = new ArrayList<String>();
        masks.add("test.test");
        Mask mask = new Mask(masks, new ArrayList<String>());

        List<String> result = JavaStyleLocatorUtils.select(list, mask);

        assertEquals(1, result.size());
        assertTrue(result.contains("test.test.object"));
    }

    // тест c более чем одной маской
    public void testWithManyMask() {
        List<String> list = new ArrayList<String>();
        list.add("test.test.object");
        list.add("real.test.object");
        list.add("supertest");

        List<String> masks = new ArrayList<String>();
        masks.add("test.test");
        masks.add("real");
        Mask mask = new Mask(masks, new ArrayList<String>());

        List<String> result = JavaStyleLocatorUtils.select(list, mask);

        assertEquals(2, result.size());
        assertTrue(result.contains("test.test.object"));
        assertTrue(result.contains("real.test.object"));
    }

    // определение пути к симплеру если он не в корне локатора
    public void testGetNotRootSimplerPath() {
        assertEquals("one/two/three/", JavaStyleLocatorUtils.getPathToSimpler("one.two.three.simpler"));
    }

    // определение пути к симплеру если он в корне локатора
    public void testGetRootSimplerPath() {
        assertEquals("", JavaStyleLocatorUtils.getPathToSimpler("simpler"));
    }

    // определение имени симплера если он не в корне локатора
    public void testGetNotRootSimplerFileName() {
        assertEquals("simpler.xml", JavaStyleLocatorUtils.getSimplerFileName("one.two.three.simpler"));
    }

    // определение имени симплера если он в корне локатора
    public void testGetRootSimplerFileName() {
        assertEquals("simpler.xml", JavaStyleLocatorUtils.getSimplerFileName("simpler"));
    }
}