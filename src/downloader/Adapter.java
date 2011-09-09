package downloader;

/**
 * @author Igor Usenko
 *         Date: 25.09.2008
 */
public interface Adapter {

    void start();

    void stop();

    long download(AdapterRequest _request);

    /**
     * ������������� � (���) �������� ��� �������������
     * � ��������� ���������� ������.
     * <p/>
     * ���������� ���������� ����� ���� ��� ��� �����������.
     */
    void cancel();

    public class AdapterException extends Exception {

        public AdapterException() {
        }

        public AdapterException(final String _s) {
            super(_s);
        }

        public AdapterException(final String _s, final Throwable _throwable) {
            super(_s, _throwable);
        }

        public AdapterException(final Throwable _throwable) {
            super(_throwable);
        }
    }
}
