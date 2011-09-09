package work.unit.constructor.channel.stream;

import junit.framework.TestCase;
import constructor.objects.channel.core.ChannelData;
import constructor.objects.channel.core.stream.ChannelDataList;
import work.testutil.ChannelDataTestUtils;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.beans.XMLEncoder;
import java.beans.XMLDecoder;

/**
 * @author Igor Usenko
 *         Date: 10.03.2009
 */
public class XmlEncoderDecoderChannelDataListTest extends TestCase {

    public XmlEncoderDecoderChannelDataListTest(String s) {
        super(s);
    }

    // тест записи/чтения списка данных канала
    public void testWriteRead() throws IOException {
        ChannelData channelData01 = ChannelDataTestUtils.createChannelDataFixture01();
        ChannelData channelData02 = ChannelDataTestUtils.createChannelDataFixture02();
        ChannelData channelData03 = ChannelDataTestUtils.createChannelDataFixture03();

        ChannelDataList original = new ChannelDataList();
        original.add(channelData01);
        original.add(channelData02);
        original.add(channelData03);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        XMLEncoder encoder = new XMLEncoder(outputStream);
        encoder.writeObject(original);
        encoder.close();
        outputStream.close();

        byte[] buffer = outputStream.toByteArray();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(buffer);
        XMLDecoder decoder = new XMLDecoder(inputStream);
        ChannelDataList restored = (ChannelDataList) decoder.readObject();
        decoder.close();
        inputStream.close();

        assertEquals(original.size(), restored.size());
        assertTrue(ChannelDataTestUtils.channelDataEquals(original.get(0), restored.get(0)));
        assertTrue(ChannelDataTestUtils.channelDataEquals(original.get(1), restored.get(1)));
        assertTrue(ChannelDataTestUtils.channelDataEquals(original.get(2), restored.get(2)));
    }
}
