package downloader.httpadapter;

/**
 * Интерфейс владельца треда
 *
 * @author Igor Usenko
 *         Date: 03.04.2010
 */
public interface HttpAdapterThreadOwner {

    /**
     * Возвращает информацию о режиме работы владельца
     *
     * @return true если владелец в режиме отмены запросов false если владалец в обычном режиме
     */
    boolean cancelling();
}
