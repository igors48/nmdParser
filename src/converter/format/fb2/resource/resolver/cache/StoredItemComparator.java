package converter.format.fb2.resource.resolver.cache;

import java.util.Comparator;

/**
 * Компаратор (по дате) элементов данных кэша
 *
 * @author Igor Usenko
 *         Date: 05.11.2009
 */
public class StoredItemComparator implements Comparator {

    public int compare(final Object _first, final Object _second) {
        int result = 0;

        if (_first != null && _second != null) {
            StoredItem first = (StoredItem) _first;
            StoredItem second = (StoredItem) _second;

            result = Long.valueOf(first.getCreated()).compareTo(second.getCreated());
        }

        return result;
    }
}
