package constructor.objects.source.core;

import dated.item.modification.stream.ModificationList;

/**
 * Интерфейс хранилища списков модификаций
 *
 * @author Igor Usenko
 *         Date: 05.03.2009
 */
public interface ModificationListStorage {

    /**
     * Сохраняет список модификаций указанного источника
     *
     * @param _sourceId идентификатор источника
     * @param _list     список
     * @throws ModificationListStorageException
     *          если не получилось
     */
    void store(String _sourceId, ModificationList _list) throws ModificationListStorageException;

    /**
     * Читает список модификаций указанного источника. Если для
     * требуемого источника списка не нашлось - возвращается пустой список
     *
     * @param _sourceId идентификатор источника
     * @return список
     * @throws ModificationListStorageException
     *          если не получилось
     */
    ModificationList load(String _sourceId) throws ModificationListStorageException;

    /**
     * Удаляет список модификаций указанного источника
     *
     * @param _sourceId идентификатор источника
     * @throws ModificationListStorageException
     *          если не получилось
     */
    void remove(String _sourceId) throws ModificationListStorageException;

    public class ModificationListStorageException extends Exception {
        public ModificationListStorageException() {
        }

        public ModificationListStorageException(String _s) {
            super(_s);
        }

        public ModificationListStorageException(String _s, Throwable _throwable) {
            super(_s, _throwable);
        }

        public ModificationListStorageException(Throwable _throwable) {
            super(_throwable);
        }
    }
}
