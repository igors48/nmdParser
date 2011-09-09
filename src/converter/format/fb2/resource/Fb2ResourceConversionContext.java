package converter.format.fb2.resource;

import util.Assert;

/**
 * Контекст преобразования ресурсов перед вставкой их в ФБ2
 *
 * @author Igor Usenko
 *         Date: 19.02.2010
 */
public class Fb2ResourceConversionContext {
    private final int maxWidth;
    private final int maxHeight;
    private final boolean grayscale;
    private final byte[] dummy;

    private final double scaleTreshold;
    private final boolean rotateEnabled;
    private final boolean splitEnabled;
    private final int minOverlap;  // percents

    public Fb2ResourceConversionContext(final int _maxWidth,
                                        final int _maxHeight,
                                        final boolean _grayscale,
                                        final byte[] _dummy,
                                        final double _scaleTreshold,
                                        final boolean _rotateEnabled,
                                        final boolean _splitEnabled,
                                        final int _minOverlap) {

        Assert.greater(_maxWidth, 1, "Maximum width <= 1");
        this.maxWidth = _maxWidth;

        Assert.greater(_maxHeight, 1, "Maximum height <= 1");
        this.maxHeight = _maxHeight;

        this.grayscale = _grayscale;

        Assert.notNull(_dummy, "Dummy is null");
        this.dummy = _dummy;

        Assert.isTrue((_scaleTreshold > 0) && (_scaleTreshold < 0.75), "Scale treshold not in range");
        this.scaleTreshold = _scaleTreshold;

        this.rotateEnabled = _rotateEnabled;

        this.splitEnabled = _splitEnabled;

        Assert.inRangeInclusive(_minOverlap, 0, 50, "Minimum overlap not in range from 0 to 50");
        this.minOverlap = _minOverlap;
    }

    public boolean isGrayscale() {
        return this.grayscale;
    }

    public int getMaxHeight() {
        return this.maxHeight;
    }

    public int getMaxWidth() {
        return this.maxWidth;
    }

    public byte[] getDummy() {
        return this.dummy;
    }

    public int getMinOverlap() {
        return this.minOverlap;
    }

    public boolean isRotateEnabled() {
        return this.rotateEnabled;
    }

    public double getScaleTreshold() {
        return this.scaleTreshold;
    }

    public boolean isSplitEnabled() {
        return this.splitEnabled;
    }
}
