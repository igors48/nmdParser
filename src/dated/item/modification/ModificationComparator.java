package dated.item.modification;

import util.Assert;

import java.util.Comparator;
import java.util.Date;

/**
 * Компаратор модификаций. Сравнивает не только по дате, а и по урлу
 *
 * @author Igor Usenko
 *         Date: 15.10.2009
 */
public class ModificationComparator implements Comparator {

    public int compare(Object _first, Object _second) {
        Assert.notNull(_first, "First object is null.");
        Assert.notNull(_second, "Second object is null.");

        Date first = ((Modification) _first).getDate();
        Date second = ((Modification) _second).getDate();

        int result = first.compareTo(second);

        if (result == 0) {
            String firstUrl = ((Modification) _first).getUrl();
            String secondUrl = ((Modification) _second).getUrl();

            result = firstUrl.compareToIgnoreCase(secondUrl);
        }

        return result;
    }
}
