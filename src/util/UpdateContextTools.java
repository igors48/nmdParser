package util;

import java.util.Map;

/**
 * @author Igor Usenko
 *         Date: 26.12.2010
 */
public final class UpdateContextTools {

    public static String contextToString(final Map<String, String> _context) {
        Assert.notNull(_context, "Context is null");

        StringBuilder result = new StringBuilder("{");

        for (String key : _context.keySet()) {
            result.append(key).append("=").append(_context.get(key)).append(" ");
        }

        return result.append("}").toString();
    }

    private UpdateContextTools() {
        // empty
    }
}
