package dated;

import util.Assert;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

/**
 * @author Igor Usenko
 *         Date: 05.01.2009
 */
public class DatedComparator implements Comparator {

    public int compare(final Object _first, final Object _second) {
        Assert.notNull(_first, "First object is null.");
        Assert.notNull(_second, "Second object is null.");

        Date first = ((Dated) _first).getDate();
        Date second = ((Dated) _second).getDate();

        return first.compareTo(second);
    }
}
