package util.sequense;

import util.Assert;

/**
 * Генератор числовой последовательности. Последовательность
 * определяется началом, концом, шагом и множителем.
 *
 * @author Igor Usenko
 *         Date: 04.06.2009
 */
public class NumericSequencer {

    private final int stop;
    private final int step;
    private final int mult;

    private int current;

    public NumericSequencer(final int _start, final int _stop, final int _step, final int _mult) {
        Assert.isTrue(_stop >= _start, "Start < Stop.");
        Assert.greater(_step, 0, "Step <= 0.");
        Assert.greaterOrEqual(_mult, 1, "Mult < 1.");

        this.stop = _stop;
        this.step = _step;
        this.mult = _mult;

        this.current = _start;
    }

    public boolean hasNext() {
        return this.current <= this.stop;
    }

    public int getNext() {
        int result = this.current;
        this.current += this.step;

        return result * this.mult;
    }
}
