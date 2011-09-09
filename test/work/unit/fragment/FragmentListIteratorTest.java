package work.unit.fragment;

import junit.framework.TestCase;
import util.fragment.ListFragmentIterator;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Igor Usenko
 *         Date: 28.06.2009
 */
public class FragmentListIteratorTest extends TestCase {

    public FragmentListIteratorTest(final String _s) {
        super(_s);
    }

    // �������������� ���� - ���������� ��������� �� ������ ������� ���������
    public void testSmoke(){
        List<String> fixture = new ArrayList<String>();
        fixture.add("1");
        fixture.add("2");
        fixture.add("3");

        ListFragmentIterator<String> iterator = new ListFragmentIterator<String>(fixture, 2);

        assertTrue(iterator.hasNext());
        List<String> fragment = iterator.getNext();

        assertEquals(2, fragment.size());
        assertEquals("1", fragment.get(0));
        assertEquals("2", fragment.get(1));

        assertTrue(iterator.hasNext());
        fragment = iterator.getNext();

        assertEquals(1, fragment.size());
        assertEquals("3", fragment.get(0));

        assertFalse(iterator.hasNext());
    }

    // ���������� ��������� ������ ������� ���������
    public void testMult(){
        List<String> fixture = new ArrayList<String>();
        fixture.add("1");
        fixture.add("2");
        fixture.add("3");
        fixture.add("4");

        ListFragmentIterator<String> iterator = new ListFragmentIterator<String>(fixture, 2);

        assertTrue(iterator.hasNext());
        List<String> fragment = iterator.getNext();

        assertEquals(2, fragment.size());
        assertEquals("1", fragment.get(0));
        assertEquals("2", fragment.get(1));

        assertTrue(iterator.hasNext());
        fragment = iterator.getNext();

        assertEquals(2, fragment.size());
        assertEquals("3", fragment.get(0));
        assertEquals("4", fragment.get(1));

        assertFalse(iterator.hasNext());
    }

    // ������ ��������� ������ ��� ����� ���������� ���������
    public void testBigFragment(){
        List<String> fixture = new ArrayList<String>();
        fixture.add("1");
        fixture.add("2");

        ListFragmentIterator<String> iterator = new ListFragmentIterator<String>(fixture, 4);

        assertTrue(iterator.hasNext());
        List<String> fragment = iterator.getNext();

        assertEquals(2, fragment.size());
        assertEquals("1", fragment.get(0));
        assertEquals("2", fragment.get(1));

        assertFalse(iterator.hasNext());
    }

    // ������ ������
    public void testEmpty(){
        List<String> fixture = new ArrayList<String>();

        ListFragmentIterator<String> iterator = new ListFragmentIterator<String>(fixture, 4);

        assertFalse(iterator.hasNext());
    }
}
