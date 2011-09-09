package work.unit.imagehelper;

import junit.framework.TestCase;
import resource.split.ImageHelperTools;

/**
 * @author Igor Usenko
 *         Date: 15.01.2011
 */
public class ImageHelperToolsTest extends TestCase {

    public ImageHelperToolsTest(final String _s) {
        super(_s);
    }

    public void testGetSplitCountNoSplit() {
        assertEquals(1, ImageHelperTools.getSplitCount(20, 20, 10));
    }

    public void testGetSplitCountOneSplit() {
        assertEquals(2, ImageHelperTools.getSplitCount(30, 20, 5));
    }

    public void testGetSplitCountSplitWithoutOverlap() {
        assertEquals(4, ImageHelperTools.getSplitCount(70, 20, 0));
    }

    public void testGetSplitStartForFirstSplit() {
        assertEquals(0, ImageHelperTools.getSplitStart(40, 10, 0));
    }

    public void testGetSplitStartForThirdSplit() {
        assertEquals(60, ImageHelperTools.getSplitStart(40, 10, 2));
    }

    public void testGetOverlapNoOverlap() {
        assertEquals(0, ImageHelperTools.getOverlapSizeForSplitCount(40, 20, 2));
    }

    public void testGetOverlapWithOverlap() {
        assertEquals(5, ImageHelperTools.getOverlapSizeForSplitCount(40, 20, 3));
    }
}
