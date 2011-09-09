package util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ”тилитный класс дл€ работы с пут€ми
 *
 * @author Igor Usenko
 *         Date: 31.03.2009
 */
public final class PathTools {

    private static final String ILLEGALS_LIST = "[\\*;|\"|~|Е|\\s|(|)|\\[|\\]|'|\u0097|-|+|%|&|^|=|?|!|$|.|,|@|:|/|\\\\]";
    private static final String ILLEGALS_REPLACEMENT = "_";

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("_yyyyMMddHHmmss_S");

    /**
     * ѕросто по окончанию строки пытаетс€ определить €вл€етс€ ли это именем графического файла
     *
     * @param _text строка
     * @return true если это похоже на им€ графического файла false если нет
     */
    public static boolean imageLink(final String _text) {
        String text = _text.toUpperCase().trim();

        return text.endsWith(".JPG") || text.endsWith(".PNG") || text.endsWith(".GIF") || text.endsWith(".JPEG");
    }

    /**
     * ƒописывает финальный разделитель если его там нет
     *
     * @param _path исходный путь
     * @return нормализованный путь
     */
    public static String normalize(final String _path) {
        Assert.isValidString(_path, "Path is not valid.");

        String result = _path;

        if (!normalized(_path)) {
            result += getDelimiter(_path);
        }

        return result;
    }

    /**
     * ”дал€ет неправильные (с точки зрени€ пути/имени) символы из строки
     *
     * @param _source исходна€ строка
     * @return почищена€ строка
     */
    public static String removeIllegals(final String _source) {
        Assert.isValidString(_source, "Path is not valid.");

        return _source.replaceAll(ILLEGALS_LIST, ILLEGALS_REPLACEMENT);
    }

    /**
     * ¬озвращает разделитель примен€ющийс€ в данном пути
     *
     * @param _address путь
     * @return разделитель
     */
    public static String getDelimiter(final String _address) {
        Assert.isValidString(_address, "Address is not valid");
        String delimiter = System.getProperty("file.separator");

        if (_address.contains("\\")) {
            delimiter = "\\";
        }

        if (_address.contains("/")) {
            delimiter = "/";
        }

        return delimiter;
    }

    public static String appendDateTimeToName(final String _name) {
        Assert.isValidString(_name, "Name is not valid");

        int point = _name.lastIndexOf(".");
        point = point == -1 ? _name.length() : point;
        StringBuilder result = new StringBuilder(_name);
        result.insert(point, getCurrentDateTimePart());

        return result.toString();
    }

    private static String getCurrentDateTimePart() {
        return DATE_FORMATTER.format(new Date(System.currentTimeMillis()));
    }

    private static boolean normalized(final String _path) {
        Assert.isValidString(_path, "Path is not valid");
        return _path.endsWith("\\") || _path.endsWith("/");
    }

    private PathTools() {
        // empty
    }
}
