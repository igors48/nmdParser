package app.workingarea.settings.properties;

import java.util.Properties;

/**
 * Интерфейс загрузчика пропертей по их идентификатору
 *
 * @author Igor Usenko
 *         Date: 28.03.2009
 */
public interface Loader {

    /**
     * Загружает свойства по идентификатору
     *
     * @param _id идентификатор комплекта свойств
     * @return комплект свойств соответствующий идентификатору
     * @throws LocatorException если не нашлось таких свойств
     */
    Properties load(String _id) throws LocatorException;

    public class LocatorException extends Exception {

        public LocatorException() {
        }

        public LocatorException(String _s) {
            super(_s);
        }

        public LocatorException(String _s, Throwable _throwable) {
            super(_s, _throwable);
        }

        public LocatorException(Throwable _throwable) {
            super(_throwable);
        }
    }
}
