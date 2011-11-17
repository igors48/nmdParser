package constructor.objects.storage;

import constructor.objects.output.core.ForEachPostProcessor;
import constructor.objects.storage.local.core.StoreItem;

/**
 * Интерфейс хранилища файлов. Сохраняет переданные файлы
 * в указанных ветках хранилища. Есть мысль использовать данный
 * интерфейс не только для сохранения выходных файлов, но и
 * для системного хранения внутренних данных.
 *
 * @author Igor Usenko
 *         Date: 21.02.2009
 */
public interface Storage {

    /**
     * Открывает хранилище. При нормальном завершении с хранилищем можно работать
     *
     * @throws StorageException если не открывается
     */
    void open() throws StorageException;

    /**
     * Сохраняет единицу работы в хранилище.
     * <br>
     *
     * @param _item          единица работы
     * @param _postProcessor процессор постобработки сформированного документа
     * @return возвращает полное имя сохраненного файла
     * @throws StorageException если возникли проблемы
     */
    String store(StoreItem _item, ForEachPostProcessor _postProcessor) throws StorageException;

    /**
     * Закрытие хранилища. Дальнейшая работа с ним невозможна вплоть
     * до его открытия
     */
    void close();

    /**
     * Удаляет файл из ветки хранилища.
     *
     * @param _branch   ветка
     * @param _fileName имя файла
     * @throws StorageException если не получилось
     */
    void remove(String _branch, String _fileName) throws StorageException;

    //todo deleteEmptyBranches

    public class StorageException extends Exception {

        public StorageException() {
            super();
        }

        public StorageException(String _message, Throwable _cause) {
            super(_message, _cause);
        }

        public StorageException(String _message) {
            super(_message);
        }

        public StorageException(Throwable _cause) {
            super(_cause);
        }

    }
}
