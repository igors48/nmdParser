package util.html;

import util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Утилитный класс для работы с кодом HTML тега
 *
 * @author Igor Usenko
 *         Date: 23.06.2009
 */
public final class HtmlTagTools {

    private static final Pattern SINGLE_QUOTED_PATTERN = Pattern.compile("[^=\\s]+\\s*=\\s*'.*?'");
    private static final Pattern DOUBLE_QUOTED_PATTERN = Pattern.compile("[^=\\s]+\\s*=\\s*\".*?\"");
    private static final Pattern NOT_QUOTED_PATTERN = Pattern.compile("[^=\\s]+\\s*=\\s*[^\\s\"\\']+");
    private static final Pattern TAG_PATTERN = Pattern.compile("\\b([^=\\s]+\\s*)=(.*?)$");

    private static final String EQUALS = "=";
    private static final String EQUALS_ESCAPEMENT = "$e$qu$a$";
    private static final Pattern SINGLE_QUOTS_PATTERN = Pattern.compile("=[\\s|^']*?'.*?(=).*?'");
    private static final Pattern DOUBLE_QUOTS_PATTERN = Pattern.compile("\".*?(=).*?\"");

    private static final Pattern ATTRIBUTE_PATTERN = Pattern.compile("(\\b\\w+\\s*?=)");
    private static final Pattern ATTRIBUTE_NAME_PATTERN = Pattern.compile("(\\b\\w+\\s*?)=");
    private static final Pattern ATTRIBUTE_VALUE_PATTERN = Pattern.compile("\\b\\w+\\s*?=(.*)");

    private static final String DOUBLE_QUOT = "\"";
    private static final String SINGLE_QUOT = "'";

    public static Map<String, String> parseAttributes(final String _image) {
        Assert.isValidString(_image, "Image is not valid.");

        String image = _image;
        Map<String, String> result = new HashMap<String, String>();

        List<String> singleQuoted = getMatches(image, SINGLE_QUOTED_PATTERN);
        image = removeSubstrings(image, singleQuoted);

        List<String> doubleQuoted = getMatches(image, DOUBLE_QUOTED_PATTERN);
        image = removeSubstrings(image, doubleQuoted);

        List<String> notQuoted = getMatches(image, NOT_QUOTED_PATTERN);
        removeSubstrings(image, notQuoted);

        parseTagImages(singleQuoted, result);
        parseTagImages(doubleQuoted, result);
        parseTagImages(notQuoted, result);

        return result;
    }

    private static List<String> getMatches(final String _data, final Pattern _pattern) {
        List<String> result = new ArrayList<String>();

        Matcher matcher = _pattern.matcher(_data);

        while (matcher.find()) {
            result.add(matcher.group().trim());
        }

        return result;
    }

    private static String removeSubstrings(final String _data, final List<String> _substrings) {
        String result = _data;

        for (String current : _substrings) {
            result = result.replace(current, "");
        }

        return result;
    }

    private static void parseTagImages(final List<String> _images, final Map<String, String> _result) {

        for (String current : _images) {
            Matcher matcher = TAG_PATTERN.matcher(current);

            if (matcher.find()) {
                String name = matcher.group(1).trim();
                String value = matcher.group(2).trim();

                _result.put(name, removeQuots(value));
            }
        }
    }

    private static String removeQuots(final String _data) {
        String result = _data;

        if (result.startsWith(SINGLE_QUOT) || result.startsWith(DOUBLE_QUOT)) {
            result = result.substring(1);
        }

        if (result.endsWith(SINGLE_QUOT) || result.endsWith(DOUBLE_QUOT)) {
            result = result.substring(0, result.length() - 1);
        }

        return result;
    }

    private HtmlTagTools() {
        // empty
    }
}
