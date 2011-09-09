package constructor.objects.channel.core;

import constructor.objects.channel.core.stream.ChannelDataList;

/**
 * ��������� ��������� ������� ������ ������
 *
 * @author Igor Usenko
 *         Date: 10.03.2009
 */
public interface ChannelDataListStorage {

    /**
     * ��������� ������ ������ ���������� ������
     *
     * @param _channelId ������������� ������
     * @param _list      ������
     * @throws ChannelDataListStorageException
     *          ���� �� ����������
     */
    void store(String _channelId, ChannelDataList _list) throws ChannelDataListStorageException;

    /**
     * ������ ������ ������ ���������� ������. ���� ���
     * ���������� ��������� ������ �� ������� - ������������ ������ ������
     *
     * @param _channelId ������������� ������
     * @return ������
     * @throws ChannelDataListStorageException
     *          ���� �� ����������
     */
    ChannelDataList load(String _channelId) throws ChannelDataListStorageException;

    /**
     * ������� ������ ������ ���������� ������
     *
     * @param _channelId ������������� ������
     * @throws ChannelDataListStorageException
     *          ���� �� ����������
     */
    void remove(String _channelId) throws ChannelDataListStorageException;

    public class ChannelDataListStorageException extends Exception {

        public ChannelDataListStorageException() {
        }

        public ChannelDataListStorageException(String s) {
            super(s);
        }

        public ChannelDataListStorageException(String s, Throwable throwable) {
            super(s, throwable);
        }

        public ChannelDataListStorageException(Throwable throwable) {
            super(throwable);
        }
    }
}
