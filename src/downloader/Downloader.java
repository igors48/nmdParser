package downloader;

/**
 * ���������
 *
 * @author Igor Usenko
 *         Date: 01.10.2008
 */
public interface Downloader {
    
    void start() throws DownloaderException;

    void stop();

    /**
     * ������ ������ �������� � �������
     *
     * @param _requestList ������ ��������
     * @return ������������� ������� ��� -1 ���� ������ �� ������
     */
    long download(RequestList _requestList);

    /**
     * �������� ��� ������� ��������.
     * ��� ��� ���� �������� �� �������� ������������ �� �������� CANCEL
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
