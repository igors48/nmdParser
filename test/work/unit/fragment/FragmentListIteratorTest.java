package work.unit.fragment;

import junit.framework.TestCase;
import util.fragment.ListFragmentIterator;

import java.util.ArrayList;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 28.06.2009
 */
public class FragmentListIteratorTest extends TestCase {

    public FragmentListIteratorTest(final String _s) {
        super(_s);
    }

    // первоначальный тест - количество элементов не кратно размеру фрагмента

    public void testSmoke() {
        List<String> fixture = newArrayList();
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

    // количество элементов кратно размеру фрагмента

    public void testMult() {
        List<String> fixture = newArrayList();
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

    // размер фрагмента больше чем общее колчиество элементов

    public void testBigFragment() {
        List<String> fixture = newArrayList();
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

    // пустой список

    public void testEmpty() {
        List<String> fixture = newArrayList();

        ListFragmentIterator<String> iterator = new ListFragmentIterator<String>(fixture, 4);

        assertFalse(iterator.hasNext());
    }
}
