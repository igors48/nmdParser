package work.unit.imagehelper;

import junit.framework.TestCase;
import resource.split.ImageHelper;
import resource.split.ImageHelperContext;
import resource.split.ImageProcessingContext;
import resource.split.SplitItem;

/**
 * @author Igor Usenko
 *         Date: 08.01.2011
 */
public class ImageHelperTest extends TestCase {

    // просто подходит. поворот и разбивка запрещены

    public void testFitAsIsNoRotateNoSplit() {
        ImageHelperContext context = new ImageHelperContext(10, 10, 100, 100, 0.7, false, false, 5);

        ImageProcessingContext result = ImageHelper.handle(context);

        assertBaseParametersValid(result, 0, 1, 1);
        assertSplitItemValid(result.getSplitItems().get(0), 0, 0, 10, 10);
    }

    // просто подходит. поворот разрешен разбивка запрещена

    public void testFitAsIsWithRotateNoSplit() {
        ImageHelperContext context = new ImageHelperContext(10, 10, 100, 100, 0.7, true, false, 5);

        ImageProcessingContext result = ImageHelper.handle(context);

        assertBaseParametersValid(result, 0, 1, 1);
        assertSplitItemValid(result.getSplitItems().get(0), 0, 0, 10, 10);
    }

    // просто подходит. поворот разрешен разбивка разрешена

    public void testFitAsIsWithRotateWithSplit() {
        ImageHelperContext context = new ImageHelperContext(10, 10, 100, 100, 0.7, true, true, 5);

        ImageProcessingContext result = ImageHelper.handle(context);

        assertBaseParametersValid(result, 0, 1, 1);
        assertSplitItemValid(result.getSplitItems().get(0), 0, 0, 10, 10);
    }

    // не подходит по высоте. поворот и разбивка запрещены

    public void testDoesNotFitHeightNoRotateNoSplit() {
        ImageHelperContext context = new ImageHelperContext(10, 200, 100, 100, 0.7, false, false, 5);

        ImageProcessingContext result = ImageHelper.handle(context);

        assertBaseParametersValid(result, 0, 0.5, 1);
        assertSplitItemValid(result.getSplitItems().get(0), 0, 0, 10, 200);
    }

    // не подходит по высоте. поворот разрешен разбивка запрещена

    public void testDoesNotFitHeightWithRotateNoSplit() {
        ImageHelperContext context = new ImageHelperContext(10, 200, 200, 100, 0.7, true, false, 5);

        ImageProcessingContext result = ImageHelper.handle(context);

        assertBaseParametersValid(result, 90, 1.0, 1);
        assertSplitItemValid(result.getSplitItems().get(0), 0, 0, 10, 200);
    }

    // не подходит по ширине. поворот и разбивка запрещены

    public void testDoesNotFitWidthNoRotateNoSplit() {
        ImageHelperContext context = new ImageHelperContext(200, 10, 100, 100, 0.7, false, false, 5);

        ImageProcessingContext result = ImageHelper.handle(context);

        assertBaseParametersValid(result, 0, 0.5, 1);
        assertSplitItemValid(result.getSplitItems().get(0), 0, 0, 200, 10);
    }

    // не подходит ни по ширине ни по высоте. поворот и разбивка запрещены

    public void testDoesNotFitWidthAndHeightNoRotateNoSplit() {
        ImageHelperContext context = new ImageHelperContext(200, 400, 100, 100, 0.7, false, false, 5);

        ImageProcessingContext result = ImageHelper.handle(context);

        assertBaseParametersValid(result, 0, 0.25, 1);
        assertSplitItemValid(result.getSplitItems().get(0), 0, 0, 200, 400);
    }

    // не подходит ни по ширине ни по высоте. поворот разрешен разбивка запрещена

    public void testDoesNotFitWidthAndHeightWithRotateNoSplit() {
        ImageHelperContext context = new ImageHelperContext(400, 200, 200, 100, 0.7, true, false, 5);

        ImageProcessingContext result = ImageHelper.handle(context);

        assertBaseParametersValid(result, 0, 0.5, 1);
        assertSplitItemValid(result.getSplitItems().get(0), 0, 0, 400, 200);
    }

    // не подходит ни по ширине ни по высоте. поворот разрешен разбивка запрещена
    // изображение д.б. повернуто т.к. так меньше уменьшение

    public void testDoesNotFitWidthAndHeightWithRotateNoSplitRotateForLessScale() {
        ImageHelperContext context = new ImageHelperContext(200, 400, 200, 100, 0.7, true, false, 5);

        ImageProcessingContext result = ImageHelper.handle(context);

        assertBaseParametersValid(result, 90, 0.5, 1);
        assertSplitItemValid(result.getSplitItems().get(0), 0, 0, 200, 400);
    }

    public void testSplitNoOverlap() {
        ImageHelperContext context = new ImageHelperContext(400, 400, 200, 200, 0.7, true, true, 0);

        ImageProcessingContext result = ImageHelper.handle(context);

        assertBaseParametersValid(result, 0, 1, 4);
        assertSplitItemValid(result.getSplitItems().get(0), 0, 0, 200, 200);
        assertSplitItemValid(result.getSplitItems().get(1), 0, 200, 200, 200);
        assertSplitItemValid(result.getSplitItems().get(2), 200, 0, 200, 200);
        assertSplitItemValid(result.getSplitItems().get(3), 200, 200, 200, 200);
    }

    public void testSplitWithOverlap() {
        ImageHelperContext context = new ImageHelperContext(400, 400, 200, 200, 0.7, true, true, 10);

        ImageProcessingContext result = ImageHelper.handle(context);

        assertBaseParametersValid(result, 0, 1, 9);

        assertSplitItemValid(result.getSplitItems().get(0), 0, 0, 200, 200);
        assertSplitItemValid(result.getSplitItems().get(1), 0, 190, 200, 200);
        assertSplitItemValid(result.getSplitItems().get(2), 0, 380, 200, 200);
        assertSplitItemValid(result.getSplitItems().get(3), 190, 0, 200, 200);
        assertSplitItemValid(result.getSplitItems().get(4), 190, 190, 200, 200);
        assertSplitItemValid(result.getSplitItems().get(5), 190, 380, 200, 200);
        assertSplitItemValid(result.getSplitItems().get(6), 380, 0, 200, 200);
        assertSplitItemValid(result.getSplitItems().get(7), 380, 190, 200, 200);
        assertSplitItemValid(result.getSplitItems().get(8), 380, 380, 200, 200);
    }

    private void assertSplitItemValid(final SplitItem _splitItem, final int _top, final int _left, final int _width, final int _height) {
        assertEquals(_top, _splitItem.getTop());
        assertEquals(_left, _splitItem.getLeft());
        assertEquals(_width, _splitItem.getWidth());
        assertEquals(_height, _splitItem.getHeight());
    }

    private void assertBaseParametersValid(final ImageProcessingContext _result, final int _rotate, final double _scale, final int _itemsCount) {
        assertEquals(_rotate, _result.getRotate());
        assertEquals(_scale, _result.getScale());
        assertEquals(_itemsCount, _result.getSplitItems().size());
    }
}