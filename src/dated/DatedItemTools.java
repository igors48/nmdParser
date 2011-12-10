package dated;

import util.Assert;
import util.DateTools;

import java.util.List;

/**
 * ��������� �����
 *
 * @author Igor Usenko
 *         Date: 26.04.2009
 */
public final class DatedItemTools {

    /**
     * ���������� ����� ������� ������� � ������
     *
     * @param _items ������
     * @return ����� ������� �������
     */
    public static DatedItem getLatestItem(final List<DatedItem> _items) {
        Assert.notNull(_items, "Items list is null.");

        DatedItem result = null;

        if (!_items.isEmpty()) {
            result = _items.get(0);

            for (Dated item : _items) {
                result = (DatedItem) DatedTools.latest(result, item);
            }
        }

        return result;
    }

    public static String getStringInfo(final DatedItem _item) {
        Assert.notNull(_item, "Dated item is null.");

        return DateTools.format(_item.getDate());
    }

    private DatedItemTools() {
        //empty
    }
}
