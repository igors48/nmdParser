package util.sequense;

import util.Assert;

/**
 * Контейнер для передачи параметров генерации последовательностей
 *
 * @author Igor Usenko
 *         Date: 28.02.2010
 */
public class SequenceGenerationParams {
    private final String pattern;
    private final int from;
    private final int to;
    private final int step;
    private final int mult;
    private final int len;
    private final String padding;

    public SequenceGenerationParams(final String _pattern, final int _from, final int _to, final int _step, final int _mult, final int _len, final String _padding) {
        Assert.isValidString(_pattern, "Pattern is not valid");
        this.pattern = _pattern;

        this.from = _from;
        this.to = _to;
        this.step = _step;
        this.mult = _mult;

        Assert.greaterOrEqual(_len, 0, "Length < 0");
        this.len = _len;

        Assert.notNull(_padding, "Padding is null");
        this.padding = _padding;
    }

    public SequenceGenerationParams(final String _pattern) {
        this(_pattern, 1, 1, 1, 1, 1, "");
    }

    public int getFrom() {
        return this.from;
    }

    public int getLen() {
        return this.len;
    }

    public int getMult() {
        return this.mult;
    }

    public String getPadding() {
        return this.padding;
    }

    public String getPattern() {
        return this.pattern;
    }

    public int getStep() {
        return this.step;
    }

    public int getTo() {
        return this.to;
    }
}
