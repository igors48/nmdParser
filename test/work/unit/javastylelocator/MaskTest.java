package work.unit.javastylelocator;

import constructor.dom.locator.Mask;
import junit.framework.TestCase;

import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 11.11.2009
 */
public class MaskTest extends TestCase {

    public MaskTest(final String s) {
        super(s);
    }

    // первоначальный тест есть оба списка

    public void testSmoke() {
        List<String> accepted = newArrayList();
        accepted.add("test");

        List<String> excepted = newArrayList();
        excepted.add("test.conf");

        Mask mask = new Mask(accepted, excepted);

        assertTrue(mask.accept("test.impl"));
        assertFalse(mask.accept("test.conf.true"));
    }

    // пустые списки

    public void testEmptyLists() {
        List<String> accepted = newArrayList();

        List<String> excepted = newArrayList();

        Mask mask = new Mask(accepted, excepted);

        assertTrue(mask.accept("test.impl"));
    }

    // есть список включаемых список исключаемых пуст

    public void testAccepted() {
        List<String> accepted = newArrayList();
        accepted.add("test");

        List<String> excepted = newArrayList();

        Mask mask = new Mask(accepted, excepted);

        assertTrue(mask.accept("test.sample"));
        assertFalse(mask.accept("conf.sample"));
    }

    // есть список исключаемых список включаемых пуст

    public void testExcepted() {
        List<String> accepted = newArrayList();

        List<String> excepted = newArrayList();
        excepted.add("test");

        Mask mask = new Mask(accepted, excepted);

        assertTrue(mask.accept("conf.sample"));
        assertFalse(mask.accept("test.sample"));
    }

    // режим точного совпаднения маски

    public void testExactly() {
        Mask mask = new Mask("mask");

        assertTrue(mask.accept("mask"));
        assertFalse(mask.accept("masko"));
    }

}
