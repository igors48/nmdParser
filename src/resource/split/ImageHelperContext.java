package resource.split;

import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 08.01.2011
 */
public class ImageHelperContext {
    private final int srcWidth;
    private final int srcHeight;
    private final int destWidth;
    private final int destHeight;
    private final double scaleTreshold;
    private final boolean rotateEnabled;
    private final boolean splitEnabled;
    private final int minOverlap;  // percents
    //private final boolean overlapAdjustable;

    public ImageHelperContext(final int _srcWidth,
                              final int _srcHeight,
                              final int _destWidth,
                              final int _destHeight,
                              final double _scaleTreshold,
                              final boolean _rotateEnabled,
                              final boolean _splitEnabled,
                              final int _minOverlap) {
        Assert.greater(_srcWidth, 0, "Source width < 0");
        this.srcWidth = _srcWidth;

        Assert.greater(_srcHeight, 0, "Source height < 0");
        this.srcHeight = _srcHeight;

        Assert.greater(_destWidth, 0, "Destination width < 0");
        this.destWidth = _destWidth;

        Assert.greater(_destHeight, 0, "Destination height < 0");
        this.destHeight = _destHeight;

        Assert.isTrue((_scaleTreshold > 0) && (_scaleTreshold < 0.75), "Scale treshold not in range");
        this.scaleTreshold = _scaleTreshold;

        this.rotateEnabled = _rotateEnabled;

        this.splitEnabled = _splitEnabled;

        Assert.inRangeInclusive(_minOverlap, 0, 50, "Minimum overlap not in range from 0 to 50");
        this.minOverlap = _minOverlap;
    }

    public int getSrcWidth() {
        return this.srcWidth;
    }

    public int getSrcHeight() {
        return this.srcHeight;
    }

    public int getDestWidth() {
        return this.destWidth;
    }

    public int getDestHeight() {
        return this.destHeight;
    }

    public double getScaleTreshold() {
        return this.scaleTreshold;
    }

    public boolean isRotateEnabled() {
        return this.rotateEnabled;
    }

    public boolean isSplitEnabled() {
        return this.splitEnabled;
    }

    public int getMinOverlap() {
        return this.minOverlap;
    }
}
