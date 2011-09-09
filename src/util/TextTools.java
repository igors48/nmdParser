package util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Igor Usenko
 *         Date: 02.11.2008
 */
public final class TextTools {

    private static final String OPEN_TAG_PATTERN = "<(\\w+)(\\s*[^>]*)>";
    private static final String CLOSE_TAG_PATTERN = "<\\/(\\w+)>";

    public static String removeEscapedAmpersands(final String _data) {
        Assert.isValidString(_data, "Data is not valid");

        return _data.replaceAll("&amp;", "&").replaceAll("&AMP;", "&");
    }

    public static String escapeAmpersands(final String _data) {
        Assert.isValidString(_data, "Data is not valid");

        StringBuilder result = new StringBuilder(_data);
        int index = 0;

        while ((index = result.indexOf("&", index)) != -1) {

            String rest = result.substring(index + 1);

            if (!rest.toUpperCase().startsWith("AMP")) {
                result.replace(index, index + 1, "&amp;");
            }

            index += 4;
        }

        return result.toString();
    }

    public static String replaceAll(final String _template, final String _name, final String _value) {
        Assert.isValidString(_template, "Template is not valid");
        Assert.isValidString(_name, "Name is not valid");
        Assert.notNull(_value, "Value is null");

        StringBuilder result = new StringBuilder(_template);

        int i;

        while ((i = result.indexOf(_name)) != -1) {
            result.replace(i, i + _name.length(), _value);
        }

        return result.toString();
    }

    public static String weld(final List<String> _list, final String _divider) {
        Assert.notNull(_list, "List is null");
        Assert.notNull(_divider, "Divider is null");

        StringBuilder result = new StringBuilder();

        for (String current : _list) {
            result.append(current);

            if (!_divider.isEmpty()) {

                if (_list.indexOf(current) != _list.size() - 1) {
                    result.append(_divider);
                }
            }
        }

        return result.toString();
    }

    public static String capsFirst(final String _name) {
        Assert.isValidString(_name, "Name is null");

        String firstChar = _name.substring(0, 1).toUpperCase();

        return firstChar + _name.substring(1);
    }

    public static String intelliCut(final String _string, final int _maxLen) {
        Assert.isValidString(_string, "String is not valid");
        Assert.greater(_maxLen, 0, "Maximum length <= 0");
        String tmpString = _string.trim() + " ";

        StringBuilder buffer = new StringBuilder();

        Matcher matcher = Pattern.compile("\\S+?[\\s|.|,|;]").matcher(tmpString);

        while (matcher.find()) {
            String current = matcher.group().trim();

            if (((buffer.length() + current.length()) <= _maxLen) || (buffer.length() == 0)) {
                buffer.append(current).append(" ");
            } else {
                break;
            }
        }

        String result = buffer.toString();

        if (buffer.length() > _maxLen) {
            result = buffer.substring(0, _maxLen);
        }

        return result.trim();
    }

    public static String removeHtmlTags(final String _data) {
        Assert.notNull(_data, "Data is not valid.");

        String result = _data.replaceAll(CLOSE_TAG_PATTERN, "");

        return result.replaceAll(OPEN_TAG_PATTERN, "");
    }

    public static long parseLong(final String _value) {
        Assert.notNull(_value, "Value is null");

        long result = 0;

        try {
            result = Long.valueOf(_value);
        } catch (Exception e) {
            // ignore
        }

        return result;
    }

    private TextTools() {
        //empty
    }
}
