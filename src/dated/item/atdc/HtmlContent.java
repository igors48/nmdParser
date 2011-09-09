package dated.item.atdc;

import util.Assert;

/**
 * Контейнерный класс. Строка с контентом и адрес откуда контент получен
 *
 * @author Igor Usenko
 *         Date: 07.03.2009
 */
public class HtmlContent {

    private final String url;
    private final String content;
    private final String base;

    public HtmlContent(final String _url, final String _content, final String _base) {
        Assert.isValidString(_content, "Content is not valid.");
        this.content = _content;

        Assert.isValidString(_url, "Content URL is not valid.");
        this.url = _url;

        Assert.isValidString(_base, "Content base is not valid.");
        this.base = _base;
    }

    public String getContent() {
        return this.content;
    }

    public String getUrl() {
        return this.url;
    }

    public String getBase() {
        return this.base;
    }
}
