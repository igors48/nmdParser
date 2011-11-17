package resource;

import http.Data;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 02.10.2008
 */
public final class ResourceUtil {

    private static final int[] MAGIC_NUMBERS_GIF = {0x47, 0x49};
    private static final int[] MAGIC_NUMBERS_PNG = {0x89, 0x50};
    private static final int[] MAGIC_NUMBERS_JPEG = {0xff, 0xd8};

    public static ResourceType getResourceType(final Data _data) {
        Assert.notNull(_data, "Data is null");

        ResourceType result = ResourceType.UNKNOWN;

        if (_data.size() >= 15) {
            result = checkSignature(_data);
        }

        return result;
    }

    private static ResourceType checkSignature(final Data _data) {

        try {
            int[] testPair = createTestPair(_data);

            if (pairEquals(MAGIC_NUMBERS_JPEG, testPair)) {
                return ResourceType.IMAGE_JPEG;
            }

            if (pairEquals(MAGIC_NUMBERS_GIF, testPair)) {
                return ResourceType.IMAGE_GIF;
            }

            if (pairEquals(MAGIC_NUMBERS_PNG, testPair)) {
                return ResourceType.IMAGE_PNG;
            }
        } catch (Data.DataException e) {
            // empty
        }

        return ResourceType.UNKNOWN;
    }

    private static int[] createTestPair(final Data _data) throws Data.DataException {
        byte[] buffer = _data.getData(0, 1);

        int first = convertToUnsigned(buffer[0]);
        int second = convertToUnsigned(buffer[1]);

        return new int[] {first, second};
    }

    private static int convertToUnsigned(final byte _byte) {
        return (int) _byte & 0xFF;
    }

    private static boolean pairEquals(final int[] first, final int[] second) {
        return (first[0] == second[0]) && (first[1] == second[1]);
    }

    private ResourceUtil() {
        //empty
    }
}
