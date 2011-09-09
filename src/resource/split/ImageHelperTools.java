package resource.split;

import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 08.01.2011
 */
public final class ImageHelperTools {

    public static int getSplitCount(final int _srcSize, final int _destSize, final int _overlapSize) {
        Assert.greater(_srcSize, 0, "Source size <= 0");
        Assert.greater(_destSize, 0, "Destination size <= 0");
        Assert.greaterOrEqual(_overlapSize, 0, "Overlap size < 0");

        return (int) Math.ceil((double) (_srcSize - _overlapSize) / (double) (_destSize - _overlapSize));
    }

    public static int getSplitStart(final int _destSize, final int _overlapSize, final int _splitCount) {
        Assert.greater(_destSize, 0, "Destination size <= 0");
        Assert.greaterOrEqual(_overlapSize, 0, "Overlap size < 0");
        Assert.greaterOrEqual(_splitCount, 0, "Split count < 0");

        return (_destSize - _overlapSize) * _splitCount;
    }

    public static int getOverlapSizeForSplitCount(final int _sourceSize, final int _destSize, final int _splitCount) {
        Assert.greater(_sourceSize, 0, "Source size <= 0");
        Assert.greater(_destSize, 0, "Destination size <= 0");
        Assert.greaterOrEqual(_splitCount, 0, "Split count < 0");

        return (_splitCount * _destSize - _sourceSize) / (1 + _splitCount);
    }

    public static int getAngle(final boolean rotated) {
        return rotated ? 90 : 0;
    }

    public static boolean isFitAsIs(final int _srcWidth,
                                    final int _srcHeight,
                                    final int _destWidth,
                                    final int _destHeight) {
        assertDimensionsValid(_srcWidth, _srcHeight, _destWidth, _destHeight);

        return _srcWidth <= _destWidth && _srcHeight <= _destHeight;
    }

    public static boolean isFitRotated(final int _srcWidth,
                                       final int _srcHeight,
                                       final int _destWidth,
                                       final int _destHeight) {
        assertDimensionsValid(_srcWidth, _srcHeight, _destWidth, _destHeight);

        return _srcWidth <= _destHeight && _srcHeight <= _destWidth;
    }

    public static boolean needRotate(final int _srcWidth,
                                     final int _srcHeight,
                                     final int _destWidth,
                                     final int _destHeight) {
        assertDimensionsValid(_srcWidth, _srcHeight, _destWidth, _destHeight);

        double scaleAsIs = getScaleForAsIs(_srcWidth, _srcHeight, _destWidth, _destHeight);
        double scaleRotated = getScaleForRotated(_srcWidth, _srcHeight, _destWidth, _destHeight);

        return scaleAsIs < scaleRotated;
    }

    public static double getMaxScale(final int _srcWidth,
                                     final int _srcHeight,
                                     final int _destWidth,
                                     final int _destHeight) {
        assertDimensionsValid(_srcWidth, _srcHeight, _destWidth, _destHeight);

        return Math.max(getScaleForAsIs(_srcWidth, _srcHeight, _destWidth, _destHeight), getScaleForRotated(_srcWidth, _srcHeight, _destWidth, _destHeight));
    }

    public static double getScaleForAsIs(final int _srcWidth,
                                         final int _srcHeight,
                                         final int _destWidth,
                                         final int _destHeight) {
        assertDimensionsValid(_srcWidth, _srcHeight, _destWidth, _destHeight);

        return Math.min((double) _destWidth / (double) _srcWidth, (double) _destHeight / (double) _srcHeight);
    }

    public static double getScaleForRotated(final int _srcWidth,
                                            final int _srcHeight,
                                            final int _destWidth,
                                            final int _destHeight) {
        assertDimensionsValid(_srcWidth, _srcHeight, _destWidth, _destHeight);

        return Math.min((double) _destWidth / (double) _srcHeight, (double) _destHeight / (double) _srcWidth);
    }

    private static void assertDimensionsValid(int _srcWidth, int _srcHeight, int _destWidth, int _destHeight) {
        Assert.greater(_srcWidth, 0, "Source width < 0");
        Assert.greater(_srcHeight, 0, "Source height < 0");
        Assert.greater(_destWidth, 0, "Destination width < 0");
        Assert.greater(_destHeight, 0, "Destination height < 0");
    }

    private ImageHelperTools() {
        // empty
    }
}
