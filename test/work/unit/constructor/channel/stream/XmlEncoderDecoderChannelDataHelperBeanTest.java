package work.unit.constructor.channel.stream;

import constructor.objects.channel.core.ChannelData;
import constructor.objects.channel.core.stream.ChannelDataHelperBean;
import junit.framework.TestCase;
import work.testutil.ChannelDataTestUtils;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Igor Usenko
 *         Date: 09.03.2009
 */
public class XmlEncoderDecoderChannelDataHelperBeanTest extends TestCase {

    public XmlEncoderDecoderChannelDataHelperBeanTest(String s) {
        super(s);
    }

    // �������� ������/������ ������ ������

    public void testSmoke() throws IOException {
        ChannelData original = ChannelDataTestUtils.createChannelDataFixture01();

        ChannelDataHelperBean helperBean = new ChannelDataHelperBean();
        helperBean.store(original);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        XMLEncoder encoder = new XMLEncoder(outputStream);
        encoder.writeObject(helperBean);
        encoder.close();
        outputStream.close();

        byte[] buffer = outputStream.toByteArray();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(buffer);
        XMLDecoder decoder = new XMLDecoder(inputStream);
        ChannelDataHelperBean restored = (ChannelDataHelperBean) decoder.readObject();
        decoder.close();
        inputStream.close();

        assertTrue(restored != null);
    }

}
