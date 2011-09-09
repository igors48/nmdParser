package constructor.dom;

import app.templater.Template;
import constructor.dom.locator.Mask;
import constructor.objects.simpler.configuration.SimplerConfiguration;

import java.io.InputStream;
import java.util.List;

/**
 * Интерфейс поиска сохраненных объектов
 *
 * @author Igor Usenko
 *         Date: 21.02.2009
 */
public interface Locator {

    /**
     * По имени и типу возвращает полный путь к исходному файлу объекта
     *
     * @param _id   имя
     * @param _type тип
     * @return полный путь к исходному файлу объекта
     * @throws LocatorException если не получилось найти требуемый объект
     */
    String getSourceFile(final String _id, final ObjectType _type) throws LocatorException;

    /**
     * По имени и типу возвращает поток из которого может быть считан объект
     *
     * @param _id   имя
     * @param _type тип
     * @return экземпляр объекта
     * @throws LocatorException если не получилось найти требуемый объект
     */
    InputStream locate(final String _id, final ObjectType _type) throws LocatorException;

    /**
     * Возвращает список имен всех найденных объектов заданного типа
     *
     * @param _type тип объекта
     * @param _mask маска отбора объектов
     * @return список имен
     * @throws LocatorException если не получилось
     */
    List<String> locateAll(final ObjectType _type, final Mask _mask) throws LocatorException;

    /**
     * Сохраняет переданный комплект шаблонов в подкаталоге с указанным именем
     *
     * @param _name      имя подкаталога
     * @param _templates комплект шаблонов
     * @throws LocatorException если не получилось
     */
    void storeTemplates(final String _name, final List<Template> _templates) throws LocatorException;

    /**
     * Удаляет файл с конфигурацией симплера (файл определяется по идентификатору)
     *
     * @param _configuration конфигурация симплера
     * @throws LocatorException если не получилось
     */
    void removeSimplerConfiguration(final SimplerConfiguration _configuration) throws LocatorException;

    /**
     * Сохраняет конфигурацию симплера в файле (файл определяется по идентификатору)
     *
     * @param _configuration конфигурация симплера
     * @throws LocatorException если не получилось
     */
    void storeSimplerConfiguration(final SimplerConfiguration _configuration) throws LocatorException;

    public class LocatorException extends Exception {

        public LocatorException() {
        }

        public LocatorException(String s) {
            super(s);
        }

        public LocatorException(String s, Throwable throwable) {
            super(s, throwable);
        }

        public LocatorException(Throwable throwable) {
            super(throwable);
        }
    }
}