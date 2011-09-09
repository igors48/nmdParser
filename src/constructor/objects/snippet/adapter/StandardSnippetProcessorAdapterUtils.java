package constructor.objects.snippet.adapter;

import util.Assert;
import util.PathTools;

import java.io.File;

/**
 * Утилиты для работы стандартного адаптера обработчика сниппетов
 *
 * @author Igor Usenko
 *         Date: 13.12.2009
 */
public final class StandardSnippetProcessorAdapterUtils {

    private static String EXTENSION = ".timestamp";
    private static final String DOT = ".";

    public static String getPropertiesFileName(final String _name) {
        Assert.isValidString(_name, "Name is not valid");

        return new StringBuilder().append(getPath(_name)).append(getName(_name)).append(EXTENSION).toString();
    }

    private static String getName(final String _name) {
        String name = new File(_name).getName();
        int lastDot = name.lastIndexOf(DOT);

        return lastDot == -1 ? name : name.substring(0, lastDot);
    }

    private static String getPath(final String _name) {
        return PathTools.normalize(new File(_name).getParent());
    }

    private StandardSnippetProcessorAdapterUtils() {
        // empty
    }
}
