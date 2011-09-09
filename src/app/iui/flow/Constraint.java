package app.iui.flow;

import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 06.12.2010
 */
public class Constraint {

    private static final int LARGE_BUTTON = 200;
    private static final int MEDIUM_BUTTON = 140;

    private static final int MAX_LIST_HEIGHT = 275;
    private static final int LESS_MAX_LIST_HEIGHT = 255;

    private static final int PROGRESS_BAR_HEIGHT = 22;

    private static final int VERTICAL_GAP = 5;

    private final int panelWidth;

    private StringBuilder constraint;

    public Constraint(final int _width) {
        Assert.greater(_width, 0, "Width <= 0");
        this.panelWidth = _width;

        this.constraint = new StringBuilder();
    }

    public Constraint wrap() {
        return append("wrap");
    }

    public Constraint largeButton() {
        return width(LARGE_BUTTON);
    }

    public Constraint mediumButton() {
        return width(MEDIUM_BUTTON);
    }

    public Constraint maximumWidth() {
        return width(this.panelWidth - 14);
    }

    public Constraint progressBarHeight() {
        return height(PROGRESS_BAR_HEIGHT);
    }

    public Constraint listHeight() {
        return height(MAX_LIST_HEIGHT);
    }

    public Constraint smallerListHeight() {
        return height(LESS_MAX_LIST_HEIGHT);
    }

    public Constraint width(final int _width) {
        Assert.greater(_width, 0, "Width <= 0");

        return append("w " + String.valueOf(_width) + "!");
    }

    public Constraint height(final int _height) {
        Assert.greater(_height, 0, "Height <= 0");

        return append("h " + String.valueOf(_height) + "!");
    }

    public Constraint verticalGap() {
        return gapY(VERTICAL_GAP);
    }

    public Constraint gapY(final int _gap) {
        Assert.greaterOrEqual(_gap, 0, "gapY < 0");

        return append("gapy " + String.valueOf(_gap));
    }

    public Constraint alignRight() {
        return append("align right");
    }

    public Constraint alignCenter() {
        return append("align center");
    }

    public Constraint split(int _count) {
        Assert.greater(_count, 0, "Count <= 0");

        return append("split " + String.valueOf(_count));
    }

    public String toString() {
        return this.constraint.toString();
    }

    private Constraint append(final String _constraint) {

        if (this.constraint.length() != 0) {
            this.constraint.append(", ");
        }

        this.constraint.append(_constraint);

        return this;
    }
}
