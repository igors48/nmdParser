package work.testutil;

import util.Assert;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author Igor Usenko
 *         Date: 21.02.2009
 */
public final class SaxLoaderTestUtils {

    public static InputStream createStream(final String _data) {
        Assert.isValidString(_data);

        return new ByteArrayInputStream(_data.getBytes());
    }

    private SaxLoaderTestUtils() {
        // empty
    }
}
