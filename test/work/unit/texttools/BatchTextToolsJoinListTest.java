package work.unit.texttools;

import junit.framework.TestCase;
import util.batchtext.Boundary;
import util.batchtext.BatchTextTools;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Igor Usenko
 *         Date: 24.05.2009
 */
public class BatchTextToolsJoinListTest extends TestCase {

    public BatchTextToolsJoinListTest(final String _s) {
        super(_s);
    }

    // первоначальный тест
    public void testSmoke(){
        Boundary boundary01 = new Boundary(2, 10);
        Boundary boundary02 = new Boundary(8, 12);

        List<Boundary> list = new ArrayList<Boundary>();
        list.add(boundary01);
        list.add(boundary02);

        List<Boundary> result = BatchTextTools.joinList(list);

        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getStart());
        assertEquals(12, result.get(0).getEnd());
    }

    // тест на пустой список
    public void testEmpty(){
        List<Boundary> list = new ArrayList<Boundary>();

        List<Boundary> result = BatchTextTools.joinList(list);

        assertEquals(0, result.size());
    }

    // тест непересекающихся
    public void testNonIntercepted(){
        Boundary boundary01 = new Boundary(2, 6);
        Boundary boundary02 = new Boundary(8, 12);

        List<Boundary> list = new ArrayList<Boundary>();
        list.add(boundary01);
        list.add(boundary02);

        List<Boundary> result = BatchTextTools.joinList(list);

        assertEquals(2, result.size());
        assertEquals(2, result.get(0).getStart());
        assertEquals(6, result.get(0).getEnd());
        assertEquals(8, result.get(1).getStart());
        assertEquals(12, result.get(1).getEnd());
    }

    // тест поглощения
    public void testConsumed(){
        Boundary boundary01 = new Boundary(2, 6);
        Boundary boundary02 = new Boundary(8, 12);
        Boundary boundary03 = new Boundary(4, 10);

        List<Boundary> list = new ArrayList<Boundary>();
        list.add(boundary01);
        list.add(boundary02);
        list.add(boundary03);

        List<Boundary> result = BatchTextTools.joinList(list);

        assertEquals(1, result.size());
        assertEquals(2, result.get(0).getStart());
        assertEquals(12, result.get(0).getEnd());
    }
}
