package work.unit.constructor.source.stream;

import dated.item.modification.Modification;
import dated.item.modification.stream.ModificationHelperBean;
import junit.framework.TestCase;

import java.util.Date;

import static work.testutil.ModificationTestUtils.assertModificationEquals;

/**
 * @author Igor Usenko
 *         Date: 05.03.2009
 */
public class ModificationHelperBeanTest extends TestCase {

    public ModificationHelperBeanTest(String s) {
        super(s);
    }

    // �������� �� ������������ ����������� � ��������������� �����������

    public void testStoreRestore() {
        Modification modification = new Modification(new Date(), "url", "title", "description");

        ModificationHelperBean helperBean = new ModificationHelperBean();
        helperBean.store(modification);
        Modification restored = helperBean.restore();

        assertModificationEquals(modification, restored);
    }

    // �������� �� ������������ ����������� � ��������������� �������� �����������

    public void testStoreRestoreBrief() {
        Modification modification = new Modification(new Date(), "url");

        ModificationHelperBean helperBean = new ModificationHelperBean();
        helperBean.store(modification);
        Modification restored = helperBean.restore();

        assertModificationEquals(modification, restored);
    }


}
