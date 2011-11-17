package constructor.objects.storage;

import constructor.objects.output.core.ForEachPostProcessor;
import constructor.objects.storage.local.core.StoreItem;

/**
 * ��������� ��������� ������. ��������� ���������� �����
 * � ��������� ������ ���������. ���� ����� ������������ ������
 * ��������� �� ������ ��� ���������� �������� ������, �� �
 * ��� ���������� �������� ���������� ������.
 *
 * @author Igor Usenko
 *         Date: 21.02.2009
 */
public interface Storage {

    /**
     * ��������� ���������. ��� ���������� ���������� � ���������� ����� ��������
     *
     * @throws StorageException ���� �� �����������
     */
    void open() throws StorageException;

    /**
     * ��������� ������� ������ � ���������.
     * <br>
     *
     * @param _item          ������� ������
     * @param _postProcessor ��������� ������������� ��������������� ���������
     * @return ���������� ������ ��� ������������ �����
     * @throws StorageException ���� �������� ��������
     */
    String store(StoreItem _item, ForEachPostProcessor _postProcessor) throws StorageException;

    /**
     * �������� ���������. ���������� ������ � ��� ���������� ������
     * �� ��� ��������
     */
    void close();

    /**
     * ������� ���� �� ����� ���������.
     *
     * @param _branch   �����
     * @param _fileName ��� �����
     * @throws StorageException ���� �� ����������
     */
    void remove(String _branch, String _fileName) throws StorageException;

    //todo deleteEmptyBranches

    public class StorageException extends Exception {

        public StorageException() {
            super();
        }

        public StorageException(String _message, Throwable _cause) {
            super(_message, _cause);
        }

        public StorageException(String _message) {
            super(_message);
        }

        public StorageException(Throwable _cause) {
            super(_cause);
        }

    }
}
