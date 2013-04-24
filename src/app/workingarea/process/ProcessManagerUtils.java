package app.workingarea.process;

import util.Assert;
import util.TextTools;

import java.util.Map;

/**
 * @author Igor Usenko
 *         Date: 22.11.2009
 */
public final class ProcessManagerUtils {

    public static final String DIRECTORY_KEY = "d";
    public static final String FILE_NAME_KEY = "f";
    public static final String FULL_FILE_NAME_KEY = "n";

    private static final String PLACEHOLDER_PREFIX = "%";

    public static String createCommandLine(final String _template, final Map<String, String> _parms) {
        Assert.isValidString(_template, "Template is not valid");
        Assert.notNull(_parms, "Parameter map is null");

        String result = _template;

        for (String name : _parms.keySet()) {
            result = TextTools.replaceAll(result, PLACEHOLDER_PREFIX + name, _parms.get(name));
        }

        return result;
    }

    private ProcessManagerUtils() {
        // empty
    }
}
