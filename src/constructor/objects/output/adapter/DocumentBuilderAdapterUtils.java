package constructor.objects.output.adapter;

import util.Assert;
import util.PathTools;

/**
 * Утилиты для строителя документов
 *
 * @author Igor Usenko
 *         Date: 30.10.2009
 */
public final class DocumentBuilderAdapterUtils {

    public static String getName(final String _name, final int _maxLen) {
        Assert.isValidString(_name, "Name is not valid");
        Assert.greater(_maxLen, 0, "Maximum length <= 0");

        String name = PathTools.removeIllegals(_name);

        return name.length() > _maxLen ? name.substring(0, _maxLen) : name;
    }

    private DocumentBuilderAdapterUtils() {
        // empty
    }
}
