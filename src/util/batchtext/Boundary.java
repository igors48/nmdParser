package util.batchtext;

import util.Assert;

/**
 * Диапазон данных в строке. Просто начало и конец
 *
 * @author Igor Usenko
 *         Date: 23.05.2009
 */
public class Boundary {

    private final int start;
    private final int end;

    public Boundary(final int _start, final int _end) {
        Assert.greaterOrEqual(_start, 0, "Start < 0");
        Assert.greaterOrEqual(_end, _start, "Start > End");

        this.start = _start;
        this.end = _end;
    }

    public int getEnd() {
        return this.end;
    }

    public int getStart() {
        return this.start;
    }
}
