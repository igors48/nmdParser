package constructor.dom.locator;

import util.Assert;

import java.io.File;
import java.io.FilenameFilter;

/**
 * Фильтр ловли файлов с расширением xml
 *
 * @author Igor Usenko
 *         Date: 01.04.2009
 */
public class XmlFileNameFilter implements FilenameFilter {

    private static final String XML_EXTENSION = ".XML";

    public boolean accept(File _file, String _s) {
        Assert.notNull(_file, "File is null.");
        Assert.isValidString(_s, "File name is not valid.");

        return _s.toUpperCase().endsWith(XML_EXTENSION);
    }
}
