package debug.console;

import debug.DebugConsoleUpdater;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;
import util.IOTools;
import util.PathTools;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 30.08.2009
 */
public class FileDebugConsoleUpdater implements DebugConsoleUpdater {

    private static String FILE_POSTFIX = ".debug.txt";

    private final String directory;

    private final Log log;

    public FileDebugConsoleUpdater(final String _directory) {
        Assert.isValidString(_directory, "Debug directory name is invalid");
        Assert.isTrue((new File(_directory)).exists(), "Debug directory [ " + _directory + " ] does not exists");
        this.directory = PathTools.normalize(_directory);

        this.log = LogFactory.getLog(getClass());
    }

    public void update(final String _name, final List<String> _image) throws DebugConsoleUpdaterException {
        Assert.isValidString(_name, "Name is not valid");
        Assert.notNull(_image, "Image is null");

        String fileName = createName(_name);
        prepareFile(fileName);
        PrintWriter writer = null;

        try {
            writer = new PrintWriter(fileName);
            int index = 0;

            for (String current : _image) {
                writer.println(generateLine(index++, current));
            }

        } catch (FileNotFoundException e) {
            throw new DebugConsoleUpdaterException(e);
        } finally {
            IOTools.close(writer);
        }
    }

    public void clean() {
        File directory = new File(this.directory);
        File[] files = directory.listFiles(new FileOnlyFilter());

        for (File file : files) {

            if (!file.delete()) {
                this.log.error("Error deleting file [ " + file.getName() + " ] from debug console directory");
            }
        }
    }

    private String generateLine(final int _index, final String _current) {
        return String.valueOf(_index) + " : " + _current;
    }

    private String createName(final String _name) {
        return this.directory + _name + FILE_POSTFIX;
    }

    private void prepareFile(final String _name) throws DebugConsoleUpdaterException {
        File file = new File(_name);

        if (file.exists()) {

            if (!file.delete()) {
                throw new DebugConsoleUpdaterException("Can not delete file [ " + _name + " ].");
            }
        }
    }

    private class FileOnlyFilter implements FileFilter {

        public boolean accept(final File _file) {
            Assert.notNull(_file, "File is null");
            return _file.isFile();
        }
    }
}
