package constructor.objects.storage.local.core;

/**
 * Интерфейс единицы работы для хранилища
 *
 * @author Igor Usenko
 *         Date: 04.03.2009
 */
public interface StoreItem {

    /**
     * Возвращает идентификатор ветки хранилища
     *
     * @return идентификатор ветки
     */
    String getBranch();

    /**
     * Возвращает имя единицы работы
     *
     * @return имя единицы
     */
    String getName();

    /**
     * Возвращает размер единицы работы
     *
     * @return размер
     */
    long getSize();

    /**
     * Возвращает должна ли быть удалена эта единица после успешного выполнения работы
     *
     * @return true если должна false
     */
    boolean isRemove();

    /**
     * Удаляет единицу работы
     *
     * @return true false
     */
    boolean remove();

    /**
     * Определяет поведение в случае если такая единица уже существует
     *
     * @return true удалить имеющуюся false переименовать имеющуюся
     */
    boolean isRemoveExists();
}
