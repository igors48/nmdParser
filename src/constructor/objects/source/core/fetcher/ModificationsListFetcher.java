package constructor.objects.source.core.fetcher;

import constructor.objects.source.core.ModificationFetcher;
import dated.item.modification.Modification;
import util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 05.03.2009
 */
public class ModificationsListFetcher implements ModificationFetcher {

    private final List<Modification> list;

    public ModificationsListFetcher(final List<Modification> _list) {
        Assert.notNull(_list, "List is null");
        this.list = _list;
    }

    public List<Modification> getModifications() {
        return this.list;
    }
    
}
