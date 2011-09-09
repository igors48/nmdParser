package constructor.objects.interpreter.core;

import dated.item.modification.Modification;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 04.12.2008
 */
public class PageListItem {

    private final Modification modification;
    private final String url;

    public PageListItem(final Modification _modification, final String _url) {
        Assert.notNull(_modification);
        Assert.isValidString(_url);

        this.modification = _modification;
        this.url = _url;
    }

    public String getUrl() {
        return this.url;
    }

    public Modification getModification() {
        return this.modification;
    }
}
