package work.comp.downloader;

import downloader.banned.BannedList;
import junit.framework.TestCase;

/**
 * @author Igor Usenko
 *         Date: 06.05.2009
 */
public class BannedListTest extends TestCase {
    
    private static final String SITE01 = "site01";
    private static final String SITE02 = "site02";
    private static final String SITE03 = "site03";
    private static final String SITE04 = "site04";

    public BannedListTest(final String _s) {
        super(_s);
    }

    // �������������� ����
    public void testSmoke(){
        BannedList list = new BannedList(2, 3);

        assertFalse(list.isBanned(SITE01));
    }

    // ���� �� ��, ��� ���� �� �������, ���� �� ���� ������������ ����������
    public void testComplainsNotEnough(){
        BannedList list = new BannedList(2, 3);

        list.complain(SITE01);

        assertFalse(list.isBanned(SITE01));
    }
    
    // ���� �� ��, ��� ���� �������, ���� ���������� ����������
    public void testComplainsEnough(){
        BannedList list = new BannedList(2, 3);

        list.complain(SITE01);
        list.complain(SITE01);

        assertTrue(list.isBanned(SITE01));
    }

    // ���� �� ��, ��� �� ���� ������ ������� ����������, ���� ���������� ����������
    public void testComplainsEnoughRightly(){
        BannedList list = new BannedList(2, 3);

        list.complain(SITE01);
        list.complain(SITE02);
        list.complain(SITE01);

        assertTrue(list.isBanned(SITE01));
        assertFalse(list.isBanned(SITE02));
    }

    // ���� �� �������� ������� ������
    public void testLimit(){
        BannedList list = new BannedList(2, 2);

        list.complain(SITE01);
        list.complain(SITE02);
        list.complain(SITE02);
        list.complain(SITE01);

        assertTrue(list.isBanned(SITE01));
        assertTrue(list.isBanned(SITE02));

        list.complain(SITE03);
        list.complain(SITE04);

        assertEquals(2, list.size());

        assertFalse(list.isBanned(SITE01));
        assertFalse(list.isBanned(SITE02));

        list.complain(SITE03);
        list.complain(SITE04);

        assertTrue(list.isBanned(SITE03));
        assertTrue(list.isBanned(SITE04));
    }
}
