package work.unit.constructor.interpreter.stream;

import junit.framework.TestCase;
import dated.item.atdc.AtdcItem;
import dated.DatedItem;
import work.testutil.AtdcTestUtils;
import work.testutil.InterpreterDataTestUtils;

import java.util.List;
import java.util.ArrayList;

import constructor.objects.interpreter.core.data.InterpreterData;
import constructor.objects.interpreter.core.data.stream.InterpreterDataHelperBean;

/**
 * @author Igor Usenko
 *         Date: 09.03.2009
 */
public class InterpreterDataHelperBeanTest extends TestCase {

    public InterpreterDataHelperBeanTest(String s) {
        super(s);
    }

    // тест сохранения/восстановления
    public void testStoreRestore(){
        AtdcItem item01 = AtdcTestUtils.getAtdcFullItem(1);
        AtdcItem item02 = AtdcTestUtils.getAtdcFullItem(2);

        List<DatedItem> originalList = new ArrayList<DatedItem>();
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
