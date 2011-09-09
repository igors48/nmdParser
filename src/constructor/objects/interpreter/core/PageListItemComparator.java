package constructor.objects.interpreter.core;

import util.Assert;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Компаратор для PageListItem
 *
 * @author Igor Usenko
 *         Date: 29.06.2009
 */
public class PageListItemComparator implements Comparator, Serializable {

    public int compare(final Object _first, final Object _second) {
        Assert.notNull(_first, "First object is null.");
        Assert.notNull(_second, "Second object is null.");

        return ((PageListItem) _first).getUrl().compareTo(((PageListItem) _second).getUrl());
    }
}
