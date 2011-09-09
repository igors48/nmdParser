package util.html;

import util.Assert;

/**
 * Контейнерный класс содержащий результат работы HtmlTagExtractor
 *
 * @author Igor Usenko
 *         Date: 11.03.2009
 */
public class HtmlTagBounds {
    /**
     * Собственно тег
     */
    private final TagImage tag;
    /**
     * Стартовая позиция в документе
     */
    private final int start;
    /**
     * Конечная позиция с учетом длины конечного тэга
     */
    private final int end;
    /**
     * Стартовая позиция контента
     */
    private final int contentStart;
    /**
     * Длина закрывающего тега
     */
    private final int closingTagLength;

    public HtmlTagBounds(TagImage _tag, int _start, int _end, int _contentStart, int _closingTagLength) {
        Assert.notNull(_tag, "Tag is null");
        Assert.greaterOrEqual(_start, 0, "Tag start < 0.");
        Assert.greater(_end, _start, "Tag start < end.");
        Assert.greater(_contentStart, _start, "Tag contentStart < Start.");
        Assert.greaterOrEqual(_closingTagLength, 0, "ClosingTagLength <= 0.");

        this.tag = _tag;
        this.start = _start;
        this.end = _end;
        this.contentStart = _contentStart;
        this.closingTagLength = _closingTagLength;
    }

    public int getEnd() {
        return this.end;
    }

    public int getStart() {
        return this.start;
    }

    public TagImage getTag() {
        return this.tag;
    }

    public int getClosingTagLength() {
        return this.closingTagLength;
    }

    public int getContentStart() {
        return this.contentStart;
    }
}
