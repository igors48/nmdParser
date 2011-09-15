package downloader;

/**
 * Загрузчик
 *
 * @author Igor Usenko
 *         Date: 01.10.2008
 */
public interface Downloader {
    
    void start() throws DownloaderException;

    void stop();

    /**
     * Ставит список запросов в очередь
     *
     * @param _requestList список запросов
     * @return идентификатор запроса или -1 если запрос не принят
     */
    long download(RequestList _requestList);

    /**
     * Отменяет все начатые процессы.
     * Все что было передано на загрузку возвращается со статусом CANCEL
     */
    void cancel();


    public class DownloaderException extends Exception {
        public DownloaderException() {
        }

        public DownloaderException(String s) {
            super(s);
        }

        public DownloaderException(String s, Throwable throwable) {
            super(s, throwable);
        }

        public DownloaderException(Throwable throwable) {
            super(throwable);
        }
    }

}
