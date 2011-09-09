package work.unit.sequenser;

import junit.framework.TestCase;
import util.sequense.NumericSequencer;

/**
 * @author Igor Usenko
 *         Date: 04.06.2009
 */
public class NumericSequencerTest extends TestCase {

    public NumericSequencerTest(final String _s) {
        super(_s);
    }

    // �������������� ����
    public void testSmoke(){
        NumericSequencer sequencer = new NumericSequencer(0, 10, 1, 1);

        assertTrue(sequencer.hasNext());
    }

    // ���� ������������������ �� ���� ���������
    public void testTwoElements(){
        NumericSequencer sequencer = new NumericSequencer(0, 1, 1, 1);

        assertTrue(sequencer.hasNext());
        assertEquals(0, sequencer.getNext());
        assertTrue(sequencer.hasNext());
        assertEquals(1, sequencer.getNext());
        assertFalse(sequencer.hasNext());
    }

    // ���� ������������������ �� ������ ��������
    public void testOneElement(){
        NumericSequencer sequencer = new NumericSequencer(0, 1, 100, 1);

        assertTrue(sequencer.hasNext());
        assertEquals(0, sequencer.getNext());
        assertFalse(sequencer.hasNext());
    }

    // ���� ������������������ �� ���� ��������� � ����������
    public void testTwoElementsWithMult(){
        NumericSequencer sequencer = new NumericSequencer(0, 1, 1, 20);

        assertTrue(sequencer.hasNext());
        assertEquals(0, sequencer.getNext());
        assertTrue(sequencer.hasNext());
        assertEquals(20, sequencer.getNext());
        assertFalse(sequencer.hasNext());
    }

    // ���� ����� ����� �����
    public void testStartStopEquals(){
        NumericSequencer sequencer = new NumericSequencer(1, 1, 1, 1);

        assertTrue(sequencer.hasNext());
        assertEquals(1, sequencer.getNext());
        assertFalse(sequencer.hasNext());
    }

}
