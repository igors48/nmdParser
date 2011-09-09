package app.workingarea.settings.properties;

import util.Assert;
import util.IOTools;
import util.PathTools;

import java.io.*;
import java.util.Properties;

/**
 * Загрузчик пропертей из файла с пропертями.
 * Считает, что все файлы пропертей лежат в указанном ему каталоге
 * и только в нем одном.
 *
 * @author Igor Usenko
 *         Date: 17.04.2009
 */
public class PropertiesFileLoader implements Loader {

    private final String root;
    private static final String PROPERTIES_FILE_EXTENSION = ".properties";

    public PropertiesFileLoader(final String _root) {
        Assert.isValidString(_root, "Root path is not valid.");
        Assert.isTrue(new File(_root).exists(), "Root path does not exists.");

        this.root = PathTools.normalize(_root);
    }

    public Properties load(final String _id) throws LocatorException {
        Assert.isValidString(_id, "Properties id is not valid.");

        InputStream stream = null;

        try {
            Properties result = new Properties();

            File file = new File(this.root + _id + PROPERTIES_FILE_EXTENSION);
            stream = new FileInputStream(file);
            result.load(stream);

            return result;
        } catch (FileNotFoundException e) {
            throw new LocatorException(e);
        } catch (IOException e) {
            throw new LocatorException(e);
        } finally {
            IOTools.close(stream);
        }
    }
}
