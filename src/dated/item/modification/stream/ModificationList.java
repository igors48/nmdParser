package dated.item.modification.stream;

import dated.item.modification.Modification;
import util.Assert;

import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * Сериализуемый/десериализуемый список модификаций
 *
 * @author Igor Usenko
 *         Date: 05.03.2009
 */
public class ModificationList {

    private List<Modification> list;

    public ModificationList() {
        this.list = newArrayList();
    }

    public void add(Modification _modification) {
        Assert.notNull(_modification);
        this.list.add(_modification);
    }

    public void addList(List<Modification> _list) {
        Assert.notNull(_list);
        this.list.addAll(_list);
    }

    public Modification get(int _index) {
        return this.list.get(_index);
    }

    public Modification remove(int _index) {
        return this.list.remove(_index);
    }

    public int size() {
        return this.list.size();
    }

    public void clear() {
        this.list.clear();
    }

    public List<ModificationHelperBean> getList() {
        List<ModificationHelperBean> result = newArrayList();

        for (Modification modification : this.list) {
            ModificationHelperBean helperBean = new ModificationHelperBean();
            helperBean.store(modification);
            result.add(helperBean);
        }

        return result;
    }

    public void setList(List<ModificationHelperBean> _list) {
        Assert.notNull(_list);

        this.list.clear();

        for (ModificationHelperBean helperBean : _list) {
            this.list.add(helperBean.restore());
        }
    }
}
