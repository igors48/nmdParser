package constructor.objects.storage.local.core;

/**
 * ��������� �������������� � ������ ���������� ��������
 * � ����� � ���� ���������
 *
 * @author Igor Usenko
 *         Date: 21.02.2009
 */
public interface Handler {

    /**
     * ��������� ���� ������������� ��������
     *
     * @param _directory ��� ��������
     * @return true - ���� ������� ���������� false - ���� ���
     */
    boolean directoryExists(String _directory);

    /**
     * ��������� ���� ������������� ����� � ��������� ��������
     *
     * @param _directory ��� ��������
     * @param _name      ��� �����
     * @return true - ���� ���� � �������� ���������� false - ���� ���
     */
    boolean fileExists(String _directory, String _name);

    /**
     * ������� ��������� �������
     *
     * @param _directory ��� ��������
     * @throws HandlerException ���� �� ����������
     */
    void createDirectory(String _directory) throws HandlerException;

    /**
     * ������� ����
     *
     * @param _directory ��� ��������
     * @param _name      ��� �����
     * @throws HandlerException ���� �� ����������
     */
    void deleteFile(String _directory, String _name) throws HandlerException;

    /**
     * ��������������� ����, ��������� � ��� ����� ������� �����
     *
     * @param _directory ��� ��������
     * @param _name      ��� �����
     * @throws HandlerException ���� �� ����������
     */
    void renameExistentFile(String _directory, String _name) throws HandlerException;

    /**
     * �������� �������� ���� � ��������� ���� �� ��������� ��������
     *
     * @param _directory ������� �������
     * @param _name      ������� ����
     * @param _source    �������� ����
     * @throws HandlerException ���� �� ����������
     */
    void storeFile(String _directory, String _name, String _source) throws HandlerException;

    /**
     * ���������� ��������� ����� � ��������� ��������
     *
     * @param _directory �������
     * @return ��������� ����� � ������
     */
    long freeSpace(String _directory);

    /**
     * ������� �� ��������� ����� ������ ���������� �������� � ������ ��������
     *
     * @param _root   ������� ������ ��������
     * @param _maxAge ������������ ��������� ������� �����
     */
    void clean(String _root, long _maxAge);

    public class HandlerException extends Exception {

        public HandlerException() {
            super();
        }

        public HandlerException(String _message, Throwable _cause) {
            super(_message, _cause);
        }

        public HandlerException(String _message) {
            super(_message);
        }

        public HandlerException(Throwable _cause) {
            super(_cause);
        }

    }
}
