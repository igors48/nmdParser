package app.workingarea.settings.properties;

import java.util.Properties;

/**
 * ��������� ���������� ��������� �� �� ��������������
 *
 * @author Igor Usenko
 *         Date: 28.03.2009
 */
public interface Loader {

    /**
     * ��������� �������� �� ��������������
     *
     * @param _id ������������� ��������� �������
     * @return �������� ������� ��������������� ��������������
     * @throws LocatorException ���� �� ������� ����� �������
     */
    Properties load(String _id) throws LocatorException;

    public class LocatorException extends Exception {

        public LocatorException() {
        }

        public LocatorException(String _s) {
            super(_s);
        }

        public LocatorException(String _s, Throwable _throwable) {
            super(_s, _throwable);
        }

        public LocatorException(Throwable _throwable) {
            super(_throwable);
        }
    }
}
