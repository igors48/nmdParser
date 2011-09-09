package util;

import java.lang.reflect.Method;

/**
 * ��������� ����� ��� ������ � reflection
 *
 * @author Igor Usenko
 *         Date: 03.03.2009
 */
public final class ReflecTools {

    /**
     * ���� ����� � ���������� ������� � ��������� ������ ���������� �� �������� � �����
     *
     * @param _object ������
     * @param _prefix ������� ����� ������
     * @param _name   ��� ������
     * @return ������ �� �����
     * @throws NoSuchMethodException ���� ����� �� �������
     */
    public static Method findMethod(Object _object, String _prefix, String _name) throws NoSuchMethodException {
        Assert.notNull(_object);
        Assert.isValidString(_prefix);
        Assert.isValidString(_name);

        String name = _prefix + TextTools.capsFirst(_name);

        return _object.getClass().getMethod(name, String.class);
    }

    private ReflecTools() {
        // empty
    }
}
