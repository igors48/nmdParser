package constructor.objects.interpreter.core;

import dated.item.modification.Modification;
import util.Assert;

/**
 * Фрагмент веб страницы
 *
 * @author Igor Usenko
 *         Date: 04.12.2008
 */
public class Fragment {

    private final Modification modification;
    private final String url;
    private final String image;
    private final String base;

    public Fragment(final Modification _modification, final String _url, final String _image, final String _base) {
        Assert.notNull(_modification, "Fragment modification is null.");
        Assert.notNull(_url, "Fragment url is null.");
        Assert.notNull(_image, "Fragment image is null.");
        Assert.notNull(_base, "Fragment base is null.");

        this.modification = _modification;
        this.url = _url;
        this.image = _image;
        this.base = _base;
    }

    public String getImage() {
        return this.image;
    }

    public String getUrl() {
        return this.url;
    }

    public Modification getModification() {
        return this.modification;
    }

    public String getBase() {
        return this.base;
    }
}
