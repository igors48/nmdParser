package resource.split;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static resource.split.ImageHelperTools.*;
import static util.CollectionUtils.newArrayList;

/**
 * @author Igor Usenko
 *         Date: 07.01.2011
 */
public final class ImageHelper {

    private static final Log log = LogFactory.getLog(ImageHelper.class);

    public static ImageProcessingContext handle(final ImageHelperContext _context) {
        Assert.notNull(_context, "Context is null");

        return _context.isRotateEnabled() ? handleWithRotate(_context) : handleWithoutRotate(_context);
    }

    private static ImageProcessingContext handleWithRotate(final ImageHelperContext _context) {
        ImageProcessingContext result;

        if (isFitAsIs(_context.getSrcWidth(), _context.getSrcHeight(), _context.getDestWidth(), _context.getDestHeight())) {
            SplitItem item = new SplitItem(0, 0, _context.getSrcWidth(), _context.getSrcHeight());

            result = new ImageProcessingContext(getAngle(false), 1, Arrays.asList(item));
        } else {

            if (isFitRotated(_context.getSrcWidth(), _context.getSrcHeight(), _context.getDestWidth(), _context.getDestHeight())) {
                SplitItem item = new SplitItem(0, 0, _context.getSrcWidth(), _context.getSrcHeight());

                result = new ImageProcessingContext(getAngle(true), 1, Arrays.asList(item));
            } else {
                result = fitOrSplit(_context);
            }
        }

        return result;
    }

    private static ImageProcessingContext handleWithoutRotate(final ImageHelperContext _context) {
        ImageProcessingContext result;

        if (isFitAsIs(_context.getSrcWidth(), _context.getSrcHeight(), _context.getDestWidth(), _context.getDestHeight())) {
            SplitItem item = new SplitItem(0, 0, _context.getSrcWidth(), _context.getSrcHeight());

            result = new ImageProcessingContext(getAngle(false), 1, Arrays.asList(item));
        } else {

            if (_context.isSplitEnabled()) {
                result = split(_context);
            } else {
                double scale = getScaleForAsIs(_context.getSrcWidth(), _context.getSrcHeight(), _context.getDestWidth(), _context.getDestHeight());
                SplitItem item = new SplitItem(0, 0, _context.getSrcWidth(), _context.getSrcHeight());

                result = new ImageProcessingContext(getAngle(false), scale, Arrays.asList(item));
            }
        }

        return result;
    }

    private static ImageProcessingContext fitOrSplit(final ImageHelperContext _context) {
        ImageProcessingContext result;

        double scale = getMaxScale(_context.getSrcWidth(), _context.getSrcHeight(), _context.getDestWidth(), _context.getDestHeight());

        if ((scale < _context.getScaleTreshold()) && (_context.isSplitEnabled())) {
            result = split(_context);
        } else {
            result = fit(_context);
        }

        return result;
    }

    private static ImageProcessingContext split(final ImageHelperContext _context) {
        int horSplitCount = ImageHelperTools.getSplitCount(_context.getSrcWidth(), _context.getDestWidth(), _context.getDestWidth() * _context.getMinOverlap() / 100);
        int verSplitCount = ImageHelperTools.getSplitCount(_context.getSrcHeight(), _context.getDestHeight(), _context.getDestHeight() * _context.getMinOverlap() / 100);

        log.debug(MessageFormat.format("Split source [ {0}, {1} ] to [ {2} ] horizontal and [ {3} ] vertical parts", _context.getSrcWidth(), _context.getSrcHeight(), horSplitCount, verSplitCount));

        List<SplitItem> items = newArrayList();

        for (int verSplit = 0; verSplit < verSplitCount; ++verSplit) {

            for (int horSplit = 0; horSplit < horSplitCount; ++horSplit) {
                int top = ImageHelperTools.getSplitStart(_context.getDestHeight(), _context.getMinOverlap(), verSplit);
                int left = ImageHelperTools.getSplitStart(_context.getDestWidth(), _context.getMinOverlap(), horSplit);

                int width = _context.getDestWidth(); // not done!!!
                int height = _context.getDestHeight();

                items.add(new SplitItem(top, left, _context.getDestWidth(), _context.getDestHeight()));

                log.debug(MessageFormat.format("Added split item [ {0}, {1}, {2}, {3} ]", left, top, width, height));
            }
        }

        return new ImageProcessingContext(0, 1, items);
    }

    private static ImageProcessingContext fit(final ImageHelperContext _context) {
        boolean needRotate = needRotate(_context.getSrcWidth(), _context.getSrcHeight(), _context.getDestWidth(), _context.getDestHeight());
        double scale = getMaxScale(_context.getSrcWidth(), _context.getSrcHeight(), _context.getDestWidth(), _context.getDestHeight());
        SplitItem splitItem = new SplitItem(0, 0, _context.getSrcWidth(), _context.getSrcHeight());

        return new ImageProcessingContext(getAngle(needRotate), scale, Arrays.asList(splitItem));
    }

    private ImageHelper() {
        // empty
    }
}
