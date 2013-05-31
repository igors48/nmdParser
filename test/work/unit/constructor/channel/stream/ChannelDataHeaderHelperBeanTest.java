package work.unit.constructor.channel.stream;

import constructor.objects.channel.core.ChannelDataHeader;
import constructor.objects.channel.core.stream.ChannelDataHeaderHelperBean;
import junit.framework.TestCase;
import work.testutil.ChannelDataHeaderTestUtils;

/**
 * @author Igor Usenko
 *         Date: 09.03.2009
 */
public class ChannelDataHeaderHelperBeanTest extends TestCase {

    public ChannelDataHeaderHelperBeanTest(String s) {
        super(s);
    }

    // тест идентичности сохраненной и восстановленной единицы

    public void testStoreRestore() {
        ChannelDataHeader original = new ChannelDataHeader("TITLE", "FIRST", "LAST", "SOURCE", "COVER");

        ChannelDataHeaderHelperBean bean = new ChannelDataHeaderHelperBean();
        bean.store(original);
        ChannelDataHeader restored = (ChannelDataHeader) bean.restore();

        assertTrue(ChannelDataHeaderTestUtils.headerEquals(original, restored));
    }
}
