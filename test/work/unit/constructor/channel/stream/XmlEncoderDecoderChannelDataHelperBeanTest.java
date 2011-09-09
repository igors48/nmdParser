package work.unit.constructor.channel.stream;

import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.beans.XMLEncoder;
import java.beans.XMLDecoder;

import constructor.objects.channel.core.ChannelData;
import constructor.objects.channel.core.stream.ChannelDataHelperBean;
import work.testutil.ChannelDataTestUtils;

/**
 * @author Igor Usenko
 *         Date: 09.03.2009
 */
public class XmlEncoderDecoderChannelDataHelperBeanTest extends TestCase {

    public XmlEncoderDecoderChannelDataHelperBeanTest(String s) {
        super(s);
    }

    // проверка записи/чтения данных канала
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
