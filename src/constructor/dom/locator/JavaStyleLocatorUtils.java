package constructor.dom.locator;

import app.templater.Template;
import constructor.dom.Locator;
import constructor.objects.simpler.configuration.SimplerConfiguration;
import constructor.objects.simpler.configuration.SimplerConfigurationTools;
import util.Assert;
import util.IOTools;
import util.PathTools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Утилиты для JavaStyleLocator
 *
 * @author Igor Usenko
 *         Date: 26.07.2009
 */
public final class JavaStyleLocatorUtils {

    public static final String XML_EXTENSION = ".xml";
    public static final String PATH_DIVIDERS = "[/|\\\\]";
    public static final String INDEX_DIVIDER = "*";
    public static final String INDEX_DIVIDER_REGEXP = "\\*";
    public static final String NAME_DIVIDER_REGEXP = "\\.";
    public static final String PATH_DIVIDER = "/";
    private static final String POINT_DIVIDER = ".";

    public static List<String> select(final List<String> _names, final Mask _mask) {
        Assert.notNull(_names, "Names list is null;");
        Assert.notNull(_mask, "Mask is null;");

        List<String> result = new ArrayList<String>();

        for (String current : _names) {

            if (_mask.accept(current)) {
                result.add(current);
            }
        }

        return result;
    }

    public static void storeTemplate(final File _directory, final Template _template) throws Locator.LocatorException {
        Assert.notNull(_directory, "Directory is null");
        Assert.notNull(_template, "Template is null");

        PrintWriter writer = null;

        try {
            File file = new File(_directory, _template.getName() + XML_EXTENSION);
            writer = new PrintWriter(file);

            for (String line : _template.getImage()) {
                writer.println(line);
            }
        } catch (FileNotFoundException e) {
            throw new Locator.LocatorException(e);
        } finally {
            IOTools.close(writer);
        }
    }

    public static String pathToIndex(final String _path) {
        Assert.isValidString(_path, "Path is not valid");

        return _path.replaceAll(PATH_DIVIDERS, INDEX_DIVIDER);
    }

    public static String indexToPath(final String _index) {
        Assert.isValidString(_index, "Index is not valid");

        return _index.replaceAll(INDEX_DIVIDER_REGEXP, PATH_DIVIDER);
    }

    public static String nameToPath(final String _name) {
        Assert.isValidString(_name, "Name is not valid");

        return _name.replaceAll(NAME_DIVIDER_REGEXP, PATH_DIVIDER);
    }

    public static String getPathToSimpler(final String _id) {
        Assert.isValidString(_id, "Simpler id is not valid");

        String result = "";

        int lastPointIndex = _id.lastIndexOf(POINT_DIVIDER);

        if (lastPointIndex != -1) {
            String pathPart = _id.substring(0, lastPointIndex);
            result = PathTools.normalize(nameToPath(pathPart));
        }

        return result;
    }

    public static String getSimplerFileName(final String _id) {
        Assert.isValidString(_id, "Simpler id is not valid");

        String namePart = _id;

        int lastPointIndex = _id.lastIndexOf(POINT_DIVIDER);

        if (lastPointIndex != -1) {
            namePart = _id.substring(lastPointIndex + 1);
        }

        return namePart + XML_EXTENSION;
    }

    public static void storeSimplerConfiguration(final File _file, final SimplerConfiguration _configuration) throws Locator.LocatorException {
        Assert.notNull(_file, "Simpler destination file is null");
        Assert.notNull(_configuration, "Simpler configuration is null");

        List<String> rendered = SimplerConfigurationTools.render(_configuration);

        PrintWriter writer = null;

        try {
            writer = new PrintWriter(_file);

            for (String line : rendered) {
                writer.println(line);
            }

        } catch (FileNotFoundException e) {
            throw new Locator.LocatorException(e);
        } finally {
            IOTools.close(writer);
        }
    }

    private JavaStyleLocatorUtils() {
        // empty
    }
}
