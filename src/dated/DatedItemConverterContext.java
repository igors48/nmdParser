package dated;

/**
 * Контекст Dated item конвертора
 *
 * @author Igor Usenko
 *         Date: 11.10.2009
 */
public class DatedItemConverterContext {

    private final boolean resolveImageLinks;
    private final boolean insertDate;

    public DatedItemConverterContext(final boolean resolveImageLinks, final boolean _insertDate) {
        this.resolveImageLinks = resolveImageLinks;
        this.insertDate = _insertDate;
    }

    public boolean isResolveImageLinks() {
        return this.resolveImageLinks;
    }

    public boolean isInsertDate() {
        return this.insertDate;
    }
}