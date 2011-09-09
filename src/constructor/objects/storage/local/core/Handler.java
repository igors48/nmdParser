package constructor.objects.storage.local.core;

/**
 * Интерфейс взаимодействия с нечтом понимающим каталоги
 * и файлы в этих каталогах
 *
 * @author Igor Usenko
 *         Date: 21.02.2009
 */
public interface Handler {

    /**
     * Проверяет факт существования каталога
     *
     * @param _directory имя каталога
     * @return true - если каталог существует false - если нет
     */
    boolean directoryExists(String _directory);

    /**
     * Проверяет факт существования файла в указанном каталоге
     *
     * @param _directory имя каталога
     * @param _name      имя файла
     * @return true - если файл в каталоге существует false - если нет
     */
    boolean fileExists(String _directory, String _name);

    /**
     * Создает указанный каталог
     *
     * @param _directory имя каталога
     * @throws HandlerException если не получилось
     */
    void createDirectory(String _directory) throws HandlerException;

    /**
     * Удаляет файл
     *
     * @param _directory имя каталога
     * @param _name      имя файла
     * @throws HandlerException если не получилось
     */
    void deleteFile(String _directory, String _name) throws HandlerException;

    /**
     * Переименовывает файл, дописывая к его имени текущее время
     *
     * @param _directory имя каталога
     * @param _name      имя файла
     * @throws HandlerException если не получилось
     */
    void renameExistentFile(String _directory, String _name) throws HandlerException;

    /**
     * Копирует исходный файл в указанный файл ыв указанном каталоге
     *
     * @param _directory целевой каталог
     * @param _name      целевой файд
     * @param _source    исходный файл
     * @throws HandlerException если не получилось
     */
    void storeFile(String _directory, String _name, String _source) throws HandlerException;

    /**
     * Возвращает свободное место в указанном каталоге
     *
     * @param _directory каталог
     * @return свободной место в байтах
     */
    long freeSpace(String _directory);

    /**
     * Удаляет из хранилища файлы старше указанного возраста и пустые каталоги
     *
     * @param _root   каталог откуда начинать
     * @param _maxAge максимальный возможный возраст файла
     */
    void clean(String _root, long _maxAge);

    public class HandlerException extends Exception {

        public HandlerException() {
            super();
        }

        public HandlerException(String _message, Throwable _cause) {
            super(_message, _cause);
        }

        public HandlerException(String _message) {
            super(_message);
        }

        public HandlerException(Throwable _cause) {
            super(_cause);
        }

    }
}
