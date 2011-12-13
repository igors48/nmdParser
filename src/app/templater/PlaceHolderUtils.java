package app.templater;

import util.Assert;

import java.util.List;
import java.util.Map;

import static util.CollectionUtils.newArrayList;

/**
 * Утилитный класс работы со строками содержащими плейсхолдеры типа ${name}
 *
 * @author Igor Usenko
 *         Date: 27.09.2009
 */
public final class PlaceHolderUtils {

    public static final String PLACEHOLDER_PREFIX = "${";
    public static final String PLACEHOLDER_POSTFIX = "}";
    public static final String PLACEHOLDER_DEFAULT_VALUE_DIVIDER = "|";

    public static List<PlaceHolderInfo> getPlaceHolderInfos(final String _template) {
        Assert.isValidString(_template, "Placeholder template is not valid");

        List<PlaceHolderInfo> result = newArrayList();
        StringBuilder template = new StringBuilder(_template);

        PlaceHolderBoundary boundary = getBoundary(template, 0);

        while (boundary != null) {
            String body = getBody(template, boundary);

            result.add(new PlaceHolderInfo(parseName(body), parseDefaultValue(body)));

            boundary = getBoundary(template, boundary.getStop());
        }

        return result;
    }

    public static List<String> replace(final List<String> _templates, final Map<String, String> _values) {
        Assert.notNull(_templates, "Templates list is null");
        Assert.notNull(_values, "Values map is null");

        List<String> result = newArrayList();

        for (String template : _templates) {
            result.add(replace(template, _values));
        }

        return result;
    }

    public static String replace(final String _template, final Map<String, String> _values) {
        Assert.isValidString(_template, "Placeholder template is not valid");
        Assert.notNull(_values, "Values map is null");

        StringBuilder result = new StringBuilder(_template);

        PlaceHolderBoundary boundary = getBoundary(result, 0);
        String body;
        String defaultValue;
        String requestedValue;

        while (boundary != null) {
            body = getBody(result, boundary);

            defaultValue = parseDefaultValue(body);
            requestedValue = _values.get(parseName(body));
            requestedValue = requestedValue == null ? defaultValue : requestedValue;

            if (requestedValue != null) {
                result.replace(boundary.getStart(), boundary.getStop() + 1, requestedValue);
            }

            boundary = getBoundary(result, requestedValue == null ? boundary.getStop() : 0);
        }

        return result.toString();
    }

    private static String getBody(final StringBuilder _result, final PlaceHolderBoundary _boundary) {
        return _result.substring(_boundary.getStart() + PLACEHOLDER_PREFIX.length(), _boundary.getStop());
    }

    public static String getPlaceholder(final String _name) {
        Assert.isValidString(_name, "Name is not valid");
        return PLACEHOLDER_PREFIX + _name.trim() + PLACEHOLDER_POSTFIX;
    }

    public static String getPlaceholder(final String _name, final String _default) {
        Assert.isValidString(_name, "Name is not valid");
        return _default == null ? getPlaceholder(_name) : PLACEHOLDER_PREFIX + _name.trim() + PLACEHOLDER_DEFAULT_VALUE_DIVIDER + _default.trim() + PLACEHOLDER_POSTFIX;
    }

    public static boolean containsPlaceholder(final String _data) {
        Assert.isValidString(_data, "Data is not valid");
        return _data.contains(PLACEHOLDER_PREFIX);
    }

    public static PlaceHolderBoundary getBoundary(final StringBuilder _data, final int _from) {
        Assert.isValidString(_data.toString(), "Data is not valid");
        Assert.greaterOrEqual(_from, 0, "From index < 0");

        PlaceHolderBoundary result = null;

        int startIndex = _data.indexOf(PLACEHOLDER_PREFIX, _from);

        if (startIndex != -1) {
            int stopIndex = _data.indexOf(PLACEHOLDER_POSTFIX, startIndex);

            if (stopIndex != -1) {
                result = new PlaceHolderBoundary(startIndex, stopIndex);
            }
        }

        return result;
    }

    public static String parseName(final String _placeholder) {
        Assert.isValidString(_placeholder, "Placeholder is not valid");

        String result = _placeholder;
        int index = _placeholder.indexOf(PLACEHOLDER_DEFAULT_VALUE_DIVIDER);

        if (index != -1) {
            result = _placeholder.substring(0, index);
        }

        return result;
    }

    public static String parseDefaultValue(final String _placeholder) {
        Assert.isValidString(_placeholder, "Placeholder is not valid");

        String result = null;
        int index = _placeholder.indexOf(PLACEHOLDER_DEFAULT_VALUE_DIVIDER);

        if (index != -1) {
            result = _placeholder.substring(index + 1);
        }

        return result;
    }

    private PlaceHolderUtils() {
        // empty
    }

}
