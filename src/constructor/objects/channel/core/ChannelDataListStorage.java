package constructor.objects.channel.core;

import constructor.objects.channel.core.stream.ChannelDataList;

/**
 * »нтерфейс хранилища списков данных канала
 *
 * @author Igor Usenko
 *         Date: 10.03.2009
 */
public interface ChannelDataListStorage {

    /**
     * —охран€ет список данных указанного канала
     *
     * @param _channelId идентификатор канала
     * @param _list      список
     * @throws ChannelDataListStorageException
     *          если не получилось
     */
    void store(String _channelId, ChannelDataList _list) throws ChannelDataListStorageException;

    /**
     * „итает список данных указанного канала. ≈сли дл€
     * требуемого источника списка не нашлось - возвращаетс€ пустой список
     *
     * @param _channelId идентификатор канала
     * @return список
     * @throws ChannelDataListStorageException
     *          если не получилось
     */
    ChannelDataList load(String _channelId) throws ChannelDataListStorageException;

    /**
     * ”дал€ет список данных указанного канала
     *
     * @param _channelId идентификатор канала
     * @throws ChannelDataListStorageException
     *          если не получилось
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
