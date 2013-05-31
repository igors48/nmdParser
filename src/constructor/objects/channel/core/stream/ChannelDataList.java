package constructor.objects.channel.core.stream;

import constructor.objects.channel.core.ChannelData;
import util.Assert;

import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * Сериализуемый/десериализуемый список данных канала
 *
 * @author Igor Usenko
 *         Date: 10.03.2009
 */
public class ChannelDataList {

    private List<ChannelData> list;

    public ChannelDataList() {
        this.list = newArrayList();
    }

    public void add(final ChannelData _channelData) {
        Assert.notNull(_channelData);
        this.list.add(_channelData);
    }

    public void addList(final List<ChannelData> _list) {
        Assert.notNull(_list);
        this.list.addAll(_list);
    }

    public ChannelData get(final int _index) {
        return this.list.get(_index);
    }

    public ChannelData remove(final int _index) {
        return this.list.remove(_index);
    }

    public int size() {
        return this.list.size();
    }

    public boolean isEmpty() {
        boolean result = true;

        if (!this.list.isEmpty()) {

            for (ChannelData current : this.list) {

                if (!current.isEmpty()) {
                    result = false;
                    break;
                }
            }
        }

        return result;

    }

    public List<ChannelDataHelperBean> getList() {
        List<ChannelDataHelperBean> result = newArrayList();

        for (ChannelData data : this.list) {
            ChannelDataHelperBean bean = new ChannelDataHelperBean();
            bean.store(data);
            result.add(bean);
        }

        return result;
    }

    public void setList(final List<ChannelDataHelperBean> _list) {
        Assert.notNull(_list);

        this.list = newArrayList();

        for (ChannelDataHelperBean bean : _list) {
            this.list.add((ChannelData) bean.restore());
        }
    }
}
