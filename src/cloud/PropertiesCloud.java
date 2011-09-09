package cloud;

import constructor.dom.ObjectType;

import java.util.Properties;

/**
 * Облако пропертей - используется различными объектами для
 * сохранения/чтения различных свойств этих объектов
 *
 * @author Igor Usenko
 *         Date: 03.03.2009
 */
public interface PropertiesCloud {

    /**
     * Сохраняет свойства владельца
     *
     * @param _owner      идентификатор владельца
     * @param _type       тип владельца
     * @param _properties свойства владельца
     * @throws PropertyCloudException если не получилось
     */
    void storeProperties(final String _owner, final ObjectType _type, final Properties _properties) throws PropertyCloudException;

    /**
     * Возвращает свойства владельца
     *
     * @param _owner идентификатор владельца
     * @param _type  тип владельца
     * @return свойства или пустые свойства если таковых нет (ранее не создавалось)
     * @throws PropertyCloudException если не получилось
     */
    Properties readProperties(final String _owner, final ObjectType _type) throws PropertyCloudException;

    /**
     * Удаляет свойства владельца
     *
     * @param _owner идентификатор владельца
     * @param _type  тип владельца
     * @throws PropertyCloudException если не получилось
     */
    void removeProperties(final String _owner, final ObjectType _type) throws PropertyCloudException;

    public class PropertyCloudException extends Exception {
        public PropertyCloudException() {
        }

        public PropertyCloudException(final String _s) {
            super(_s);
        }

        public PropertyCloudException(final String _s, final Throwable _throwable) {
            super(_s, _throwable);
        }

        public PropertyCloudException(final Throwable _throwable) {
            super(_throwable);
        }
    }
}
