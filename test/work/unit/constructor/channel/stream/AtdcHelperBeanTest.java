package work.unit.constructor.channel.stream;

import junit.framework.TestCase;
import dated.item.atdc.AtdcItem;
import dated.item.atdc.stream.AtdcItemHelperBean;

import work.testutil.AtdcTestUtils;

/**
 * @author Igor Usenko
 *         Date: 09.03.2009
 */
public class AtdcHelperBeanTest extends TestCase {
    
    public AtdcHelperBeanTest(String s) {
        super(s);
    }

    // тест идентичности сохраненной и восстановленной единицы 
    public void testStoreRestore(){
        AtdcItem original = AtdcTestUtils.getAtdcFullItem(1);

        AtdcItemHelperBean bean = new AtdcItemHelperBean();
        bean.store(original);
        AtdcItem restored = (AtdcItem) bean.restore();

        assertTrue(AtdcTestUtils.atdcItemEquals(original, restored));
    }
}
