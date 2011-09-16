package http.data;

import http.Data;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 11.10.2008
 */
public class DataUtil {

    private static final String CHARSET_TOKEN = "charset=";

    public static String getString(Data _data) throws Data.DataException {
        Assert.notNull(_data);

        String result = null;

        String innerCharSet = detectCharSet(_data);

        if (innerCharSet != null) {
            result = encode(_data, innerCharSet);
        }

        if (result == null) {

            if (_data.getCharsetName() != null) {
                result = encode(_data, _data.getCharsetName());
            }
        }

        if (result == null) {
            result = new String(_data.getData());
        }

        return result;
    }

    public static String getStringWithoutCrLfTab(Data _data) throws Data.DataException {
        Assert.notNull(_data);

        String result = getString(_data);

        if (result != null) {
            result = result.replaceAll("[\\t|\\n|\\r|\\n\\r]", " ");
        }

        return result;
    }

    public static String getDataImage(Data _data) {
        Assert.notNull(_data);
        String result = "";

        try {
            result = getStringWithoutCrLfTab(_data);

            if (result != null) {
                result = result.replaceAll("\\s+?", " ");
            }
        } catch (Exception e) {
            //empty
        }

        return result;
    }

    private static String detectCharSet(Data _data) throws Data.DataException {
        String result = null;

        String buffer = new String(_data.getData());
        int indexStart = buffer.indexOf(CHARSET_TOKEN);
        int indexEnd = buffer.indexOf("\"", indexStart);

        if (indexStart != -1 && indexEnd != -1) {
            result = buffer.substring(indexStart + CHARSET_TOKEN.length(), indexEnd);
        }

        return result;
    }

    private static String encode(Data _data, String _charSet) {
        String result = null;

        try {
            result = new String(_data.getData(), _charSet);
        } catch (Exception e) {
            // ignore
        }

        return result;
    }

    private DataUtil() {
        // empty
    }
}
