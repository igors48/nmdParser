package resource.split;

import util.Assert;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 08.01.2011
 */
public class ImageProcessingContext {
    private final int rotate;
    private final double scale;
    private final List<SplitItem> splitItems;

    public ImageProcessingContext(final int _rotate, final double _scale, final List<SplitItem> _splitItems) {
        Assert.greaterOrEqual(_rotate, 0, "Rotate angle < 0");
        this.rotate = _rotate;

        Assert.isTrue(_scale > 0, "Scale <= 0");
        this.scale = _scale;

        Assert.notNull(_splitItems, "Split items list is null");
        Assert.isFalse(_splitItems.isEmpty(), "Split items list is empty");
        this.splitItems = _splitItems;
    }

    public int getRotate() {
        return this.rotate;
    }

    public double getScale() {
        return this.scale;
    }

    public List<SplitItem> getSplitItems() {
        return this.splitItems;
    }
}
