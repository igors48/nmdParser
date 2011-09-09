package util;

import java.lang.reflect.Method;

/**
 * Утилитный класс для работы с reflection
 *
 * @author Igor Usenko
 *         Date: 03.03.2009
 */
public final class ReflecTools {

    /**
     * Ищет метод у указанного объекта с указанным именем полученным из префикса и имени
     *
     * @param _object объект
     * @param _prefix префикс имени метода
     * @param _name   имя метода
     * @return ссылка на метод
     * @throws NoSuchMethodException если метод не нашелся
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
