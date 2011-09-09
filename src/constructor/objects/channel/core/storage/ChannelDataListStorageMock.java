package constructor.objects.channel.core.storage;

import constructor.objects.channel.core.ChannelDataListStorage;
import constructor.objects.channel.core.stream.ChannelDataList;
import util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Igor Usenko
 *         Date: 10.03.2009
 */
public class ChannelDataListStorageMock implements ChannelDataListStorage {

    private final Map<String, ChannelDataList> map;

    public ChannelDataListStorageMock() {
        this.map = new HashMap<String, ChannelDataList>();
    }

    public synchronized void store(String _channelId, ChannelDataList _list) throws ChannelDataListStorageException {
        Assert.isValidString(_channelId);
        Assert.notNull(_list);

        this.map.put(_channelId, _list);
    }

    public synchronized ChannelDataList load(String _channelId) throws ChannelDataListStorageException {
        Assert.isValidString(_channelId);

        ChannelDataList result = this.map.get(_channelId);

        if (result == null) {
            result = new ChannelDataList();
        }

        return result;
    }

    public synchronized void remove(String _channelId) throws ChannelDataListStorageException {
        Assert.isValidString(_channelId);

        this.map.remove(_channelId);
    }
}
