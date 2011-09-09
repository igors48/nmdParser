package constructor.objects.channel.core.stream;

import constructor.objects.StreamHelperBean;
import constructor.objects.channel.core.ChannelData;
import constructor.objects.channel.core.ChannelDataHeader;
import constructor.objects.channel.core.ChannelDataTools;
import constructor.objects.interpreter.core.data.InterpreterData;
import constructor.objects.interpreter.core.data.stream.InterpreterDataHelperBean;
import util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Вспомогательный класс для сериализаии/десериализации ChannelData
 *
 * @author Igor Usenko
 *         Date: 09.03.2009
 */
public class ChannelDataHelperBean implements StreamHelperBean {

    private ChannelDataHeader header;
    private List<InterpreterData> data;

    public ChannelDataHelperBean() {
        this.header = new ChannelDataHeader("", "", "", "", "", ChannelDataTools.DEFAULT_GENRES, "");
        this.data = new ArrayList<InterpreterData>();
    }

    public List<InterpreterDataHelperBean> getData() {
        List<InterpreterDataHelperBean> result = new ArrayList<InterpreterDataHelperBean>();

        for (InterpreterData interpreterData : this.data) {
            InterpreterDataHelperBean bean = new InterpreterDataHelperBean();
            bean.store(interpreterData);

            result.add(bean);
        }

        return result;
    }

    public void setData(List<InterpreterDataHelperBean> _data) {
        Assert.notNull(_data);

        List<InterpreterData> result = new ArrayList<InterpreterData>();

        for (InterpreterDataHelperBean bean : _data) {
            result.add(bean.restore());
        }

        this.data = result;
    }

    public ChannelDataHeaderHelperBean getHeader() {
        ChannelDataHeaderHelperBean result = new ChannelDataHeaderHelperBean();
        result.store(this.header);

        return result;
    }

    public void setHeader(ChannelDataHeaderHelperBean _header) {
        Assert.notNull(_header);

        this.header = (ChannelDataHeader) _header.restore();
    }

    public void store(Object _object) {
        Assert.notNull(_object, "Object is null.");
        Assert.isTrue(_object instanceof ChannelData, "This is not channel data.");

        ChannelData channelData = (ChannelData) _object;
        this.header = copyHeader(channelData.getHeader());
        this.data = copyList(channelData.getData());
    }

    public Object restore() {
        ChannelDataHeader itemHeader = copyHeader(this.header);
        List<InterpreterData> itemList = copyList(this.data);

        return new ChannelData(itemHeader, itemList);
    }

    private List<InterpreterData> copyList(List<InterpreterData> _list) {
        List<InterpreterData> result = new ArrayList<InterpreterData>();

        for (InterpreterData item : _list) {
            InterpreterDataHelperBean helper = new InterpreterDataHelperBean();
            helper.store(item);
            InterpreterData buffer = helper.restore();
            result.add(buffer);
        }

        return result;
    }

    private ChannelDataHeader copyHeader(ChannelDataHeader _source) {
        ChannelDataHeaderHelperBean bean = new ChannelDataHeaderHelperBean();
        bean.store(_source);

        return (ChannelDataHeader) bean.restore();
    }
}
