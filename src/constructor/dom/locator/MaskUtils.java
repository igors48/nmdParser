package constructor.dom.locator;

import util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Утилиты для работы масок
 *
 * @author Igor Usenko
 *         Date: 11.11.2009
 */
public final class MaskUtils {

    private static final String EXCEPT_PREFIX = "^";

    public static boolean accepted(final String _value, final List<String> _masks, final boolean _exactly) {
        Assert.isValidString(_value, "Value is not valid");
        Assert.notNull(_masks, "Mask list is null");

        return _masks.isEmpty() || (_exactly ? foundExactly(_value, _masks) : foundStarted(_value, _masks));
    }

    public static boolean excepted(final String _value, final List<String> _masks, final boolean _exactly) {
        Assert.isValidString(_value, "Value is not valid");
        Assert.notNull(_masks, "Mask list is null");

        return !_masks.isEmpty() && (_exactly ? foundExactly(_value, _masks) : foundStarted(_value, _masks));
    }

    public static Mask createMask(final List<String> _masks) {
        Assert.notNull(_masks, "Masks list is null");

        return new Mask(parseAccepted(_masks), parseExcepted(_masks));
    }

    public static List<String> parseAccepted(final List<String> _masks) {
        Assert.notNull(_masks, "Masks list is null");

        List<String> result = new ArrayList<String>();

        for (String current : _masks) {

            if (!current.startsWith(EXCEPT_PREFIX)) {
                result.add(current);
            }
        }

        return result;
    }

    public static List<String> parseExcepted(final List<String> _masks) {
        Assert.notNull(_masks, "Masks list is null");

        List<String> result = new ArrayList<String>();

        for (String current : _masks) {

            if (current.startsWith(EXCEPT_PREFIX)) {
                result.add(current.substring(1));
            }
        }

        return result;
    }

    private static boolean foundStarted(final String _value, final List<String> _masks) {
        boolean result = false;

        for (String mask : _masks) {

            if (_value.startsWith(mask)) {
                result = true;
                break;
            }
        }

        return result;
    }

    private static boolean foundExactly(final String _value, final List<String> _masks) {
        boolean result = false;

        for (String mask : _masks) {

            if (_value.equalsIgnoreCase(mask)) {
                result = true;
                break;
            }
        }

        return result;
    }

    private MaskUtils() {
        // empty
    }
}
