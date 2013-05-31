package app.cli.parser;

import org.apache.commons.cli.CommandLine;
import util.Assert;

import java.util.*;

import static app.cli.parser.OptionNameTable.FORCED_MODE_OPTION_SHORT_NAME;
import static util.CollectionUtils.newHashMap;

/**
 * Утилиты для парсера опций
 *
 * @author Igor Usenko
 *         Date: 26.07.2009
 */
public final class OptionParserUtils {

    public static int parseForcedDays(final CommandLine _commandLine) {
        Assert.notNull(_commandLine, "Command line is null");

        int result = 0;

        try {
            result = Integer.valueOf(_commandLine.getOptionValue(FORCED_MODE_OPTION_SHORT_NAME, "0"));
        } catch (NumberFormatException e) {
            result = 0;
        }

        return result;
    }

    public static List<String> getOptionsList(final CommandLine _commandLine, final String _name) {
        Assert.notNull(_commandLine, "Command line is null");
        Assert.isValidString(_name, "Name is not valid");

        String[] masksArray = _commandLine.getOptionValues(_name);

        return masksArray == null ? new ArrayList<String>() : Arrays.asList(masksArray);
    }

    public static Map<String, String> convert(final Properties _properties) {
        Assert.notNull(_properties, "Properties is null");

        Map<String, String> result = newHashMap();

        for (Object key : _properties.keySet()) {
            result.put((String) key, _properties.getProperty((String) key));
        }

        return result;
    }

    private OptionParserUtils() {
        // empty
    }
}
