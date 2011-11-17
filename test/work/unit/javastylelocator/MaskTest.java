package work.unit.javastylelocator;

import constructor.dom.locator.Mask;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 11.11.2009
 */
public class MaskTest extends TestCase {

    public MaskTest(final String s) {
        super(s);
    }

    // �������������� ���� ���� ��� ������

    public void testSmoke() {
        List<String> accepted = new ArrayList<String>();
        accepted.add("test");

        List<String> excepted = new ArrayList<String>();
        excepted.add("test.conf");

        Mask mask = new Mask(accepted, excepted);

        assertTrue(mask.accept("test.impl"));
        assertFalse(mask.accept("test.conf.true"));
    }

    // ������ ������

    public void testEmptyLists() {
        List<String> accepted = new ArrayList<String>();

        List<String> excepted = new ArrayList<String>();

        Mask mask = new Mask(accepted, excepted);

        assertTrue(mask.accept("test.impl"));
    }

    // ���� ������ ���������� ������ ����������� ����

    public void testAccepted() {
        List<String> accepted = new ArrayList<String>();
        accepted.add("test");

        List<String> excepted = new ArrayList<String>();

        Mask mask = new Mask(accepted, excepted);

        assertTrue(mask.accept("test.sample"));
        assertFalse(mask.accept("conf.sample"));
    }

    // ���� ������ ����������� ������ ���������� ����

    public void testExcepted() {
        List<String> accepted = new ArrayList<String>();

        List<String> excepted = new ArrayList<String>();
        excepted.add("test");

        Mask mask = new Mask(accepted, excepted);

        assertTrue(mask.accept("conf.sample"));
        assertFalse(mask.accept("test.sample"));
    }

    // ����� ������� ����������� �����

    public void testExactly() {
        Mask mask = new Mask("mask");

        assertTrue(mask.accept("mask"));
        assertFalse(mask.accept("masko"));
    }

}
