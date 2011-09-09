package flowtext;

import util.Assert;

/**
 * Контекст строителя FTF документа
 *
 * @author Igor Usenko
 *         Date: 11.10.2009
 */
public class DocumentBuilderContext {
    private final String baseUrl;
    private final boolean resolveImageLinks;

    public DocumentBuilderContext(final String _baseUrl, final boolean _resolveImageLinks) {
        Assert.isValidString(_baseUrl, "Base URL is not valid");
        this.baseUrl = _baseUrl;

        this.resolveImageLinks = _resolveImageLinks;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public boolean isResolveImageLinks() {
        return this.resolveImageLinks;
    }
}
