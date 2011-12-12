package app.templater;

import util.Assert;

/**
 * Индексы первого и следующего за последним символов плейсхолдера
 *
 * @author Igor Usenko
 *         Date: 15.12.2009
 */
public class PlaceHolderBoundary {

    private final int start;
    private final int stop;

    public PlaceHolderBoundary(final int _start, final int _stop) {
        Assert.greaterOrEqual(_start, 0, "Start index < 0");
        Assert.greater(_stop, _start, "Stop index <= start index");

        this.start = _start;
        this.stop = _stop;
    }

    public int getStart() {
        return this.start;
    }

    public int getStop() {
        return this.stop;
    }
    
}
