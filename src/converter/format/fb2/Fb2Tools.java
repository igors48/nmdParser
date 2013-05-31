package converter.format.fb2;

import util.Assert;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static util.CollectionUtils.newHashMap;

/**
 * Различные утилиты работы с FB2
 *
 * @author Igor Usenko
 *         Date: 02.05.2009
 */
public final class Fb2Tools {

    private static final String AMPERSAND = "&";

    private static final String NUMERIC_ENTITY = "(^&#\\d+?;).*";
    private static final String CHARACTER_ENTITY = "(^&\\D\\D.*?;).*";

    private static final Pattern CHARACTER_ENTITY_PATTERN = Pattern.compile(CHARACTER_ENTITY);

    private static Map<String, String> characterEntityReplacements;

    private static final String UNSUPPORTED_ENTITY_SUBST = "[?]";

    static {
        characterEntityReplacements = newHashMap();

        characterEntityReplacements.put("&amp;", "&amp;");
        characterEntityReplacements.put("&lt;", "&lt;");
        characterEntityReplacements.put("&gt;", "&gt;");
        characterEntityReplacements.put("&nbsp;", "&#160;");
        characterEntityReplacements.put("&euml;", "ё");
        characterEntityReplacements.put("&deg;", "");
        characterEntityReplacements.put("&uuml;", "u");
        characterEntityReplacements.put("&auml;", "a");
        characterEntityReplacements.put("&ndash;", "-");
        characterEntityReplacements.put("&mdash;", "-");
        characterEntityReplacements.put("&apos;", "&quot;");

        characterEntityReplacements.put("&quot;", "&quot;");
        characterEntityReplacements.put("&laquo;", "&quot;");
        characterEntityReplacements.put("&raquo;", "&quot;");
        characterEntityReplacements.put("&lsquo;", "&quot;");
        characterEntityReplacements.put("&rsquo;", "&quot;");
        characterEntityReplacements.put("&sbquo;", "&quot;");
        characterEntityReplacements.put("&ldquo;", "&quot;");
        characterEntityReplacements.put("&rdquo;", "&quot;");
        characterEntityReplacements.put("&bdquo;", "&quot;");
    }

    public static String processEntities(final String _content) {
        Assert.isValidString(_content, "Content is not valid.");

        StringBuilder result = new StringBuilder(resolveAmps(_content));

        int index = result.indexOf(AMPERSAND, 0);

        while (index >= 0) {

            if (isCharacterEntity(result.toString(), index)) {
                String entity = getEntity(result.toString(), index, CHARACTER_ENTITY_PATTERN);

                if (!entity.isEmpty()) {
                    String replacement = characterEntityReplacements.get(entity);

                    if (replacement == null) {
                        replacement = UNSUPPORTED_ENTITY_SUBST;
                    }

                    result.replace(index, index + entity.length(), replacement);
                }
            }

            index = result.indexOf(AMPERSAND, index + 1);
        }

        return result.toString();
    }

    public static boolean isStandaloneAmp(final String _content, final int _index) {
        Assert.isValidString(_content, "Content is not valid.");
        Assert.greaterOrEqual(_index, 0, "Index <= 0.");

        boolean result = false;

        String work = _content.substring(_index);

        if (work.startsWith(AMPERSAND)) {
            boolean isNumericEntity = isNumericEntity(_content, _index);
            boolean isCharacterEntity = isCharacterEntity(_content, _index);

            result = !isNumericEntity && !isCharacterEntity;
        }

        return result;
    }

    public static boolean isNumericEntity(final String _content, final int _index) {
        Assert.isValidString(_content, "Content is not valid.");
        Assert.greaterOrEqual(_index, 0, "Index <= 0.");

        return matches(_content, _index, NUMERIC_ENTITY);
    }

    public static boolean isCharacterEntity(final String _content, final int _index) {
        Assert.isValidString(_content, "Content is not valid.");
        Assert.greaterOrEqual(_index, 0, "Index <= 0.");

        return matches(_content, _index, CHARACTER_ENTITY);
    }

    public static String resolveAmps(final String _content) {
        Assert.isValidString(_content, "Content is not valid.");

        StringBuilder result = new StringBuilder(_content);

        int index = result.indexOf(AMPERSAND);

        while (index >= 0) {

            if (isStandaloneAmp(result.toString(), index)) {
                result.replace(index, index + 1, "&amp;");
            }

            index = result.toString().indexOf(AMPERSAND, index + 1);
        }

        return result.toString();
    }

    private static boolean matches(final String _content, final int _index, final String _pattern) {
        int next = _content.indexOf(AMPERSAND, _index + 1);
        String work = next == -1 ? _content.substring(_index) : _content.substring(_index, next);

        return work.matches(_pattern);
    }

    private static String getEntity(final String _result, final int _index, final Pattern _pattern) {
        String result = "";

        String work = _result.substring(_index);

        Matcher matcher = _pattern.matcher(work);

        if (matcher.find()) {
            result = matcher.group(1);
        }

        return result;
    }

    private Fb2Tools() {
        // empty
    }
}
