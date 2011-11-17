package constructor.objects.interpreter.core.data;

import dated.DatedItem;
import util.Assert;
import util.DateTools;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  онтейнерный класс хран€щий данные полученные от интерпретатора
 * в ходе его работы
 *
 * @author Igor Usenko
 *         Date: 18.12.2008
 */
public class InterpreterData {

    private String id;
    private List<DatedItem> items;

    public InterpreterData(final String _id, final List<DatedItem> _items) {
        setId(_id);
        setItems(_items);
    }

    public InterpreterData() {
        this(" ", new ArrayList<DatedItem>());
    }

    public InterpreterData(List<DatedItem> _items) {
        this(" ", _items);
    }

    public void setId(final String _id) {
        Assert.isValidString(_id, "Interpreter data id is not valid.");
        this.id = _id;
    }

    public void setItems(final List<DatedItem> _items) {
        Assert.notNull(_items, "Interpreter data items is null.");
        this.items = _items;
    }

    public String getId() {
        return this.id;
    }

    public List<DatedItem> getItems() {
        return this.items;
    }

    public List<DatedItem> getForDate(final Date _date) {
        Assert.notNull(_date, "Date is null");

        Date testDate = DateTools.getDateWithoutTime(_date);

        List<DatedItem> result = new ArrayList<DatedItem>();

        for (DatedItem candidate : this.items) {
            Date candidateDate = DateTools.getDateWithoutTime(candidate.getDate());

            if (candidateDate.equals(testDate)) {
                result.add(candidate);
            }
        }

        return result;
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }
}
