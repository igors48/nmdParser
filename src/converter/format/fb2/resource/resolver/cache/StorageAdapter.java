package converter.format.fb2.resource.resolver.cache;

import downloader.Data;

import java.util.Map;

/**
 * Адаптер хранилища ресурсов
 *
 * @author Igor Usenko
 *         Date: 03.11.2009
 */
public interface StorageAdapter {
    /**
     * Сохранение таблицы содержания кэша
     *
     * @param _entries элементы таблицы
     * @throws StorageAdapterException если не получилось
     */
    public void storeToc(Map<String, CacheEntry> _entries) throws StorageAdapterException;

    /**
     * Загрузка элементов таблицы содержания
     *
     * @return элементы таблицы
     * @throws StorageAdapterException если не получилось
     */
    public Map<String, CacheEntry> loadToc() throws StorageAdapterException;

    /**
     * Сохраняет данные в хранилище
     *
     * @param _data данные
     * @return идентификатор сохраненных данных
     * @throws StorageAdapterException если не получилось
     */
    public String store(Data _data) throws StorageAdapterException;

    /**
     * Загружает данные из хранилища
     *
     * @param _name идентификатор данных
     * @return данные
     * @throws StorageAdapterException если не получилось
     */
    public Data load(String _name) throws StorageAdapterException;

    /**
     * Удаляет данные из хранилища
     *
     * @param _name идентификатор данных
     * @throws StorageAdapterException если не получилось
     */
    public void remove(String _name) throws StorageAdapterException;

    /**
     * Возвращает карту сохраненных данных
     *
     * @return карта
     */
    public Map<String, StoredItem> getMap();

    /**
     * Возвращает объем используемого адаптером места
     *
     * @return объем в байтах
     */
    public long getUsedSpace();

    /**
     * Возвращает объем свободного места, которым располагает адаптер
     *
     * @return объем в байтах
     */
    public long getFreeSpace();

    public class StorageAdapterException extends Exception {

        public StorageAdapterException() {
        }

        public StorageAdapterException(final String _s) {
            super(_s);
        }

        public StorageAdapterException(final String _s, final Throwable _throwable) {
            super(_s, _throwable);
        }

        public StorageAdapterException(final Throwable _throwable) {
            super(_throwable);
        }
    }
}
