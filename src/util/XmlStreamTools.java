package util;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;

/**
 * Утилиты для XML кодера / декодера
 *
 * @author Igor Usenko
 *         <p/>
 *         Date: 18.03.2010
 */
public final class XmlStreamTools {

    public static void close(final XMLDecoder _decoder) {

        if (_decoder != null) {
            _decoder.close();
        }
    }

    public static void close(final XMLEncoder _encoder) {

        if (_encoder != null) {
            _encoder.close();
        }
    }

    public XmlStreamTools() {
        // empty
    }

}
