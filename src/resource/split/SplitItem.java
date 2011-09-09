package resource.split;

import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 07.01.2011
 */
public class SplitItem {
    private final int top;
    private final int left;
    private final int width;
    private final int height;

    public SplitItem(final int _top, final int _left, final int _width, final int _height) {
        Assert.greaterOrEqual(_top, 0, "Top < 0");
        this.top = _top;

        Assert.greaterOrEqual(_left, 0, "Left < 0");
        this.left = _left;

        Assert.greater(_width, 0, "Width < 0");
        this.width = _width;

        Assert.greater(_height, 0, "Height < 0");
        this.height = _height;
    }

    public int getTop() {
        return this.top;
    }

    public int getLeft() {
        return this.left;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
