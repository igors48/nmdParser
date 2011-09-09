package downloader;

/**
 * @author Igor Usenko
 *         Date: 25.09.2008
 */
public enum Result {
    /**
     * Все обработано без ошибок
     */
    OK,
    /**
     * Произошла ошибка
     */
    ERROR,
    /**
     * Процесс был прерван
     */
    CANCEL
}
