package http.data;

import http.Data;
import util.Assert;

import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Igor Usenko
 *         Date: 11.10.2008
 */
public class DataUtil {

    private static final Pattern CHARSET_PATTERN = Pattern.compile("charset=(.+?)\"", Pattern.CASE_INSENSITIVE);
    private static final Pattern ENCODING_PATTERN = Pattern.compile("encoding=\"(.+?)\"", Pattern.CASE_INSENSITIVE);

    private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

    public static String getString(final Data _data) throws Data.DataException {
        Assert.notNull(_data);

        String result = null;

        String innerCharSet = detectCharSet(new String(_data.getData()));

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

    public static String getStringWithoutCrLfTab(final Data _data) throws Data.DataException {
        Assert.notNull(_data);

        String result = getString(_data);

        if (result != null) {
            result = result.replaceAll("[\\t|\\n|\\r|\\n\\r]", " ");
        }

        return result;
    }

    public static String getDataImage(final Data _data) {
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

    public static String detectCharSet(final String _data) {
        Assert.isValidString(_data, "Data is invalid");

        String result = null;

        Matcher charsetPatternMatcher = CHARSET_PATTERN.matcher(_data);

        if (charsetPatternMatcher.find()) {
            result = charsetPatternMatcher.group(1);
        } else {
            Matcher encodingPatternMatcher = ENCODING_PATTERN.matcher(_data);

            if (encodingPatternMatcher.find()) {
                result = encodingPatternMatcher.group(1);
            }
        }

        return result;
    }

    public static String convertToUtf8(final String _string) {
        Assert.isValidString(_string, "String is not valid");

        byte[] bytes = _string.getBytes(UTF8_CHARSET);

        return new String(bytes, UTF8_CHARSET);
    }

    private static String encode(final Data _data, final String _charSet) {
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
