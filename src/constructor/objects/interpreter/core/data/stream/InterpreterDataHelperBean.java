package constructor.objects.interpreter.core.data.stream;

import constructor.objects.StreamHelperBean;
import constructor.objects.interpreter.core.data.InterpreterData;
import dated.DatedItem;
import util.Assert;

import java.util.ArrayList;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * Вспомогательный класс для сериализации/десериализации InterpreterData
 *
 * @author Igor Usenko
 *         Date: 09.03.2009
 */
public class InterpreterDataHelperBean implements StreamHelperBean {

    private String id;
    private List<DatedItem> list;

    public InterpreterDataHelperBean() {
        this.id = "";
        this.list = newArrayList();
    }

    public String getId() {
        return id;
    }

    public void setId(String _id) {
        this.id = _id;
    }

    public List<StreamHelperBean> getList() {
        List<StreamHelperBean> result = newArrayList();

        for (DatedItem item : this.list) {
            StreamHelperBean bean = item.getHelperBean();
            bean.store(item);
            result.add(bean);
        }

        return result;
    }

    public void setList(List<StreamHelperBean> _list) {
        Assert.notNull(_list);

        this.list.clear();

        for (StreamHelperBean bean : _list) {
            DatedItem item = (DatedItem) bean.restore();
            this.list.add(item);
        }
    }

    public void store(Object _data) {
        Assert.notNull(_data);
        Assert.isTrue(_data instanceof InterpreterData, "This is not interpreter data");

        setId(((InterpreterData) _data).getId());
        this.list = copyList(((InterpreterData) _data).getItems());
    }

    public InterpreterData restore() {
        List<DatedItem> items = copyList(this.list);
        InterpreterData result = new InterpreterData();
        result.setItems(items);
        result.setId(getId());

        return result;
    }

    private List<DatedItem> copyList(List<DatedItem> _list) {
        List<DatedItem> result = newArrayList();

        for (DatedItem item : _list) {
            StreamHelperBean helper = item.getHelperBean();
            helper.store(item);
            DatedItem buffer = (DatedItem) helper.restore();
            result.add(buffer);
        }

        return result;
    }

}
