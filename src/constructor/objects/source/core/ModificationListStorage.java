package constructor.objects.source.core;

import dated.item.modification.stream.ModificationList;

/**
 * ��������� ��������� ������� �����������
 *
 * @author Igor Usenko
 *         Date: 05.03.2009
 */
public interface ModificationListStorage {

    /**
     * ��������� ������ ����������� ���������� ���������
     *
     * @param _sourceId ������������� ���������
     * @param _list     ������
     * @throws ModificationListStorageException
     *          ���� �� ����������
     */
    void store(String _sourceId, ModificationList _list) throws ModificationListStorageException;

    /**
     * ������ ������ ����������� ���������� ���������. ���� ���
     * ���������� ��������� ������ �� ������� - ������������ ������ ������
     *
     * @param _sourceId ������������� ���������
     * @return ������
     * @throws ModificationListStorageException
     *          ���� �� ����������
     */
    ModificationList load(String _sourceId) throws ModificationListStorageException;

    /**
     * ������� ������ ����������� ���������� ���������
     *
     * @param _sourceId ������������� ���������
     * @throws ModificationListStorageException
     *          ���� �� ����������
     */
    void remove(String _sourceId) throws ModificationListStorageException;

    public class ModificationListStorageException extends Exception {
        public ModificationListStorageException() {
        }

        public ModificationListStorageException(String _s) {
            super(_s);
        }

        public ModificationListStorageException(String _s, Throwable _throwable) {
            super(_s, _throwable);
        }

        public ModificationListStorageException(Throwable _throwable) {
            super(_throwable);
        }
    }
}
