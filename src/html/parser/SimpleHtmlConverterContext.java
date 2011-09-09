package html.parser;

/**
 * Контекст HTML конвертора
 *
 * @author Igor Usenko
 *         Date: 11.10.2009
 */
public class SimpleHtmlConverterContext {

    private final boolean resolveImageLinks;

    public SimpleHtmlConverterContext(final boolean resolveImageLinks) {
        this.resolveImageLinks = resolveImageLinks;
    }

    public boolean isResolveImageLinks() {
        return this.resolveImageLinks;
    }
}
