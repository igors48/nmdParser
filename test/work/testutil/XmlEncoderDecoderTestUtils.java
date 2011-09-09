package work.testutil;

import util.Assert;

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.beans.XMLEncoder;
import java.beans.XMLDecoder;

/**
 * @author Igor Usenko
 *         Date: 18.03.2010
 */
public final class XmlEncoderDecoderTestUtils {

    public static Object storeLoad(final Object _fixture) throws IOException {
        Assert.notNull(_fixture, "Fixture is null");

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        XMLEncoder encoder = new XMLEncoder(output);

        encoder.writeObject(_fixture);

        encoder.close();
        output.close();

        ByteArrayInputStream input = new ByteArrayInputStream(output.toByteArray());
        XMLDecoder decoder = new XMLDecoder(input);

        Object readed = decoder.readObject();

        decoder.close();
        input.close();

        return readed;
    }

    private XmlEncoderDecoderTestUtils() {
        // empty
    }
}
