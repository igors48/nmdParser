package util.html;

import util.Assert;

import java.util.regex.Pattern;

/**
 * Утилитный класс работы с HTML
 *
 * @author Igor Usenko
 *         Date: 25.10.2009
 */
public final class HtmlUtils {

    public static final String OPEN_TAG = "<(\\w+)(\\s*[^>]*)>";
    public static final String CLOSE_TAG = "<\\/(\\w+)>";

    public static final Pattern OPEN_TAG_PATTERN = Pattern.compile(OPEN_TAG);
    public static final Pattern CLOSE_TAG_PATTERN = Pattern.compile(CLOSE_TAG);

    public static String cleanUpTags(final String _content) {
        Assert.isValidString(_content, "Content is not valid");

        String result = _content.replaceAll(OPEN_TAG, "");

        return result.replaceAll(CLOSE_TAG, "");
    }

    private HtmlUtils() {
        // empty
    }
}
