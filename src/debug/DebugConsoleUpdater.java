package debug;

import java.util.List;

/**
 * ��������� ����������� ����� �������
 *
 * @author Igor Usenko
 *         Date: 30.08.2009
 */
public interface DebugConsoleUpdater {

    /**
     * ��������� ���������� ����� ��� ���������� ���������������� �������
     *
     * @param _name  ������������� �������
     * @param _image ����� ����������� ������
     * @throws DebugConsoleUpdaterException ���� �� ����������
     */
    void update(String _name, List<String> _image) throws DebugConsoleUpdaterException;

    /**
     * ������� ����������� ������ ����� ����� �������
     */
    void clean();

    class DebugConsoleUpdaterException extends Exception {

        public DebugConsoleUpdaterException() {
            super();
        }

        public DebugConsoleUpdaterException(final String _s) {
            super(_s);
        }

        public DebugConsoleUpdaterException(final String _s, final Throwable _throwable) {
            super(_s, _throwable);
        }

        public DebugConsoleUpdaterException(final Throwable _throwable) {
            super(_throwable);
        }

    }
    
}
