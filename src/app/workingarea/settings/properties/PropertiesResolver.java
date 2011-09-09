package app.workingarea.settings.properties;

import util.Assert;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Разруливатель пропертей с поддержкой наследования на уровне комплектов
 *
 * @author Igor Usenko
 *         Date: 28.03.2009
 */
public class PropertiesResolver {

    private final Loader loader;
    private static final String EXTENDS_KEY = "extends";

    public PropertiesResolver(Loader _loader) {
        Assert.notNull(_loader, "Property locator is null.");
        this.loader = _loader;
    }

    /**
     * Возвращает комплект пропертей по их идентификатору.
     * Обрабатывает случаи наследования.
     *
     * @param _id идентификатор комплекта
     * @return комплект пропертей
     * @throws PropertyResolverException если не получилось
     */
    public Properties resolve(final String _id) throws PropertyResolverException {
        Assert.isValidString(_id, "Properties id invalid.");

        try {
            return mergeHierarchy(buildHierarchy(this.loader.load(_id)));
        } catch (Loader.LocatorException e) {
            throw new PropertyResolverException(e);
        }
    }

    private List<Properties> buildHierarchy(Properties _end) throws Loader.LocatorException {
        LinkedList<Properties> result = new LinkedList<Properties>();
        result.add(_end);

        Properties current = _end;
        String parent = current.getProperty(EXTENDS_KEY);

        while (parent != null) {
            current = this.loader.load(parent);
            result.addFirst(current);
            parent = current.getProperty(EXTENDS_KEY);
        }

        return result;
    }

    private Properties mergeHierarchy(List<Properties> _hierarchy) {
        Properties result = new Properties();

        for (Properties current : _hierarchy) {
            merge(result, current);
        }

        return result;
    }

    private Properties merge(Properties _base, Properties _new) {

        for (Object key : _new.keySet().toArray(new Object[_new.size()])) {
            _base.setProperty((String) key, _new.getProperty((String) key));
        }

        return _base;
    }

    public static class PropertyResolverException extends Exception {

        public PropertyResolverException() {
        }

        public PropertyResolverException(String _s) {
            super(_s);
        }

        public PropertyResolverException(String _s, Throwable _throwable) {
            super(_s, _throwable);
        }

        public PropertyResolverException(Throwable _throwable) {
            super(_throwable);
        }
    }
}
