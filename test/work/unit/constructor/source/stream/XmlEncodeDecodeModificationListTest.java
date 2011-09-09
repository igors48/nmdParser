package work.unit.constructor.source.stream;

import dated.item.modification.Modification;
import dated.item.modification.stream.ModificationList;
import junit.framework.TestCase;
import static work.testutil.ModificationTestUtils.assertModificationEquals;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * @author Igor Usenko
 *         Date: 04.03.2009
 */
public class XmlEncodeDecodeModificationListTest extends TestCase {

    public XmlEncodeDecodeModificationListTest(String s) {
        super(s);
    }

    // проверка записи/чтения списка модификаций
    public void testSmoke() throws IOException {
        Modification modification01 = new Modification(new Date(), "URL01", "TITLE01", "DESCRIPTION01");
        Modification modification02 = new Modification(new Date(), "URL02");
        ModificationList list = new ModificationList();
        list.add(modification01);
        list.add(modification02);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        XMLEncoder encoder = new XMLEncoder(outputStream);
        encoder.writeObject(list);
        encoder.close();
        outputStream.close();

        byte[] buffer = outputStream.toByteArray();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(buffer);
        XMLDecoder decoder = new XMLDecoder(inputStream);
        ModificationList restored = (ModificationList) decoder.readObject();
        decoder.close();
        inputStream.close();

        assertEquals(list.size(), restored.size());
        assertModificationEquals(list.get(0), restored.get(0));
        assertModificationEquals(list.get(1), restored.get(1));
    }
}
