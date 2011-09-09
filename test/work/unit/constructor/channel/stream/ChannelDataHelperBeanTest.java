package work.unit.constructor.channel.stream;

import constructor.objects.channel.core.ChannelData;
import constructor.objects.channel.core.stream.ChannelDataHelperBean;
import junit.framework.TestCase;
import work.testutil.ChannelDataTestUtils;

/**
 * @author Igor Usenko
 *         Date: 09.03.2009
 */
public class ChannelDataHelperBeanTest extends TestCase {

    public ChannelDataHelperBeanTest(String s) {
        super(s);
    }

    // тест идентичности сохраненной и восстановленной единицы
    public void testStoreRestore() {
        ChannelData original = ChannelDataTestUtils.createChannelDataFixture01();
        ChannelDataHelperBean bean = new ChannelDataHelperBean();
        bean.store(original);
        ChannelData restored = (ChannelData) bean.restore();

        assertTrue(restored != null);

        assertTrue(ChannelDataTestUtils.channelDataEquals(original, restored));
    }
}
