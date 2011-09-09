package util.html;

import util.Assert;

/**
 * ������������ ����� ���������� ��������� ������ HtmlTagExtractor
 *
 * @author Igor Usenko
 *         Date: 11.03.2009
 */
public class HtmlTagBounds {
    /**
     * ���������� ���
     */
    private final TagImage tag;
    /**
     * ��������� ������� � ���������
     */
    private final int start;
    /**
     * �������� ������� � ������ ����� ��������� ����
     */
    private final int end;
    /**
     * ��������� ������� ��������
     */
    private final int contentStart;
    /**
     * ����� ������������ ����
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
