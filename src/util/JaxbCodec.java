package util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.JAXBException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 29.07.2011
 */
public final class JaxbCodec<T> {

    private final JAXBContext context;

    public JaxbCodec(Class<T> clazz) throws JAXBException {
        this.context = JAXBContext.newInstance(clazz);
    }

    public String encode(T object) throws JAXBException {
        Assert.notNull(object, "Object is null");

        Writer writer = new StringWriter();

        try {
            Marshaller marshaller = this.context.createMarshaller();

            marshaller.marshal(object, writer);

            return writer.toString();
        } finally {
            IOTools.close(writer);
        }
    }

    @SuppressWarnings("unchecked")
    public T decode(String data) throws JAXBException {
        Assert.isValidString(data, "Data is not valid");

        Reader reader = new StringReader(data);

        try {
            Unmarshaller unmarshaller = this.context.createUnmarshaller();

            return (T) unmarshaller.unmarshal(reader);
        } finally {
            IOTools.close(reader);
        }
    }

}
