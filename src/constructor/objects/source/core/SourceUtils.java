package constructor.objects.source.core;

import constructor.objects.strategies.StoreStrategy;
import constructor.objects.strategies.store.StoreNeverStrategy;
import dated.item.modification.Modification;
import dated.item.modification.stream.ModificationList;
import util.Assert;

import java.util.LinkedList;
import java.util.List;

/**
 * Различные утилиты для источника модификаций
 *
 * @author Igor Usenko
 *         Date: 06.03.2009
 */
public final class SourceUtils {

    public static ModificationList mergeAndRemoveExpired(final ModificationList _list, final List<Modification> _modifications, final StoreStrategy _strategy) {
        Assert.notNull(_list, "Modification list is null");
        Assert.notNull(_modifications, "Modification list is null");
        Assert.notNull(_strategy, "Store strategy is null");

        //TODO крывувато...
        if (_strategy instanceof StoreNeverStrategy) {
            _list.clear();
        }

        return removeExpired(mergeModifications(_list, _modifications), _strategy);
    }

    public static ModificationList removeExpired(final ModificationList _list, final StoreStrategy _strategy) {
        Assert.notNull(_list, "Modification list is null");
        Assert.notNull(_strategy, "Store strategy is null");

        ModificationList result = new ModificationList();

        for (int index = 0; index < _list.size(); ++index) {
            Modification item = _list.get(index);

            if (_strategy.accepted(item)) {
                result.add(item);
            }
        }

        return result;
    }

    public static ModificationList mergeModifications(final ModificationList _list, final List<Modification> _modifications) {
        Assert.notNull(_list, "Modification list is null");
        Assert.notNull(_modifications, "Modification list is null");

        ModificationList result = new ModificationList();

        LinkedList<Modification> merged = new LinkedList<Modification>();

        for (int index = 0; index < _list.size(); ++index) {
            mergeItem(merged, _list.get(index));
        }

        for (Modification modification : _modifications) {
            mergeItem(merged, modification);
        }

        result.addList(merged);

        return result;
    }

    private static void mergeItem(final LinkedList<Modification> _list, final Modification _item) {

        if (_list.contains(_item)) {
            _list.remove(_item);
            _list.addLast(_item);
        } else {
            Modification existent = findExistentByUrl(_list, _item);

            if (putNeeded(_item, existent)) {

                if (existent != null) {
                    _list.remove(existent);
                }

                _list.addLast(_item);
            }
        }
    }

    private static Modification findExistentByUrl(final LinkedList<Modification> _list, final Modification _item) {
        Modification result = null;

        for (Modification current : _list) {

            if (current.urlEquals(_item)) {
                result = current;
                break;
            }
        }

        return result;
    }

    private static boolean putNeeded(final Modification _new, final Modification _existent) {
        return _existent == null || _existent.getDate().getTime() < _new.getDate().getTime();
    }

    private SourceUtils() {
        // empty
    }

}
