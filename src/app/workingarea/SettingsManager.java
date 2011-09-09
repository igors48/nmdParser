package app.workingarea;

/**
 * ��������� ��������� ������� ���������
 *
 * @author Igor Usenko
 *         Date: 04.04.2009
 */
public interface SettingsManager {

    /**
     * ��������� �������� ������� ��������� �� �� ��������������
     *
     * @param _id �������������
     * @return ��������� ������� ���������
     * @throws SettingsManagerException ���� �� ����������
     */
    Settings getSettings(String _id) throws SettingsManagerException;

    public class SettingsManagerException extends Exception {

        public SettingsManagerException() {
        }

        public SettingsManagerException(String _s) {
            super(_s);
        }

        public SettingsManagerException(String _s, Throwable _throwable) {
            super(_s, _throwable);
        }

        public SettingsManagerException(Throwable _throwable) {
            super(_throwable);
        }
    }
}