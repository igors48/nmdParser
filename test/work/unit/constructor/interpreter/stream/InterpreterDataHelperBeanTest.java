package work.unit.constructor.interpreter.stream;

import constructor.objects.interpreter.core.data.InterpreterData;
import constructor.objects.interpreter.core.data.stream.InterpreterDataHelperBean;
import dated.DatedItem;
import dated.item.atdc.AtdcItem;
import junit.framework.TestCase;
import work.testutil.AtdcTestUtils;
import work.testutil.InterpreterDataTestUtils;

import java.util.ArrayList;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 09.03.2009
 */
public class InterpreterDataHelperBeanTest extends TestCase {

    public InterpreterDataHelperBeanTest(String s) {
        super(s);
    }

    // тест сохранения/восстановления

    public void testStoreRestore() {
        AtdcItem item01 = AtdcTestUtils.getAtdcFullItem(1);
        AtdcItem item02 = AtdcTestUtils.getAtdcFullItem(2);

        List<DatedItem> originalList = newArrayList();
        originalList.add(item01);
        originalList.add(item02);

        InterpreterData original = new InterpreterData();
        original.setId("Id");
        original.setItems(originalList);

        InterpreterDataHelperBean bean = new InterpreterDataHelperBean();
        bean.store(original);
        InterpreterData restored = bean.restore();

        assertTrue(InterpreterDataTestUtils.interpreterDataEquals(original, restored));
    }
}
