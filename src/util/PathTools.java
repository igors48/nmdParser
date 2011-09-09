package util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ��������� ����� ��� ������ � ������
 *
 * @author Igor Usenko
 *         Date: 31.03.2009
 */
public final class PathTools {

    private static final String ILLEGALS_LIST = "[\\*;|\"|~|�|\\s|(|)|\\[|\\]|'|\u0097|-|+|%|&|^|=|?|!|$|.|,|@|:|/|\\\\]";
    private static final String ILLEGALS_REPLACEMENT = "_";

    private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("_yyyyMMddHHmmss_S");

    /**
     * ������ �� ��������� ������ �������� ���������� �������� �� ��� ������ ������������ �����
     *
     * @param _text ������
     * @return true ���� ��� ������ �� ��� ������������ ����� false ���� ���
     */
    public static boolean imageLink(final String _text) {
        String text = _text.toUpperCase().trim();

        return text.endsWith(".JPG") || text.endsWith(".PNG") || text.endsWith(".GIF") || text.endsWith(".JPEG");
    }

    /**
     * ���������� ��������� ����������� ���� ��� ��� ���
     *
     * @param _path �������� ����
     * @return ��������������� ����
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
     * ������� ������������ (� ����� ������ ����/�����) ������� �� ������
     *
     * @param _source �������� ������
     * @return ��������� ������
     */
    public static String removeIllegals(final String _source) {
        Assert.isValidString(_source, "Path is not valid.");

        return _source.replaceAll(ILLEGALS_LIST, ILLEGALS_REPLACEMENT);
    }

    /**
     * ���������� ����������� ������������� � ������ ����
     *
     * @param _address ����
     * @return �����������
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
