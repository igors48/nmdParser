package converter.format.fb2.resource.resolver.cache;

import http.Data;

import java.util.Map;

/**
 * ������� ��������� ��������
 *
 * @author Igor Usenko
 *         Date: 03.11.2009
 */
public interface StorageAdapter {
    /**
     * ���������� ������� ���������� ����
     *
     * @param _entries �������� �������
     * @throws StorageAdapterException ���� �� ����������
     */
    public void storeToc(Map<String, CacheEntry> _entries) throws StorageAdapterException;

    /**
     * �������� ��������� ������� ����������
     *
     * @return �������� �������
     * @throws StorageAdapterException ���� �� ����������
     */
    public Map<String, CacheEntry> loadToc() throws StorageAdapterException;

    /**
     * ��������� ������ � ���������
     *
     * @param _data ������
     * @return ������������� ����������� ������
     * @throws StorageAdapterException ���� �� ����������
     */
    public String store(Data _data) throws StorageAdapterException;

    /**
     * ��������� ������ �� ���������
     *
     * @param _name ������������� ������
     * @return ������
     * @throws StorageAdapterException ���� �� ����������
     */
    public Data load(String _name) throws StorageAdapterException;

    /**
     * ������� ������ �� ���������
     *
     * @param _name ������������� ������
     * @throws StorageAdapterException ���� �� ����������
     */
    public void remove(String _name) throws StorageAdapterException;

    /**
     * ���������� ����� ����������� ������
     *
     * @return �����
     */
    public Map<String, StoredItem> getMap();

    /**
     * ���������� ����� ������������� ��������� �����
     *
     * @return ����� � ������
     */
    public long getUsedSpace();

    /**
     * ���������� ����� ���������� �����, ������� ����������� �������
     *
     * @return ����� � ������
     */
    public long getFreeSpace();

    public class StorageAdapterException extends Exception {

        public StorageAdapterException() {
        }

        public StorageAdapterException(final String _s) {
            super(_s);
        }

        public StorageAdapterException(final String _s, final Throwable _throwable) {
            super(_s, _throwable);
        }

        public StorageAdapterException(final Throwable _throwable) {
            super(_throwable);
        }
    }
}
