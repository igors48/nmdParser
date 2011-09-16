package resource;

import http.Data;
import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 02.10.2008
 */
public class ResourceUtil {

    private static final String JPG_SIGN_01 = "���";
    private static final String JPG_SIGN_02 = "JFIF";
    private static final String GIF_SIGN = "GIF";
    private static final String PNG_SIGN = "PNG";

    public static ResourceType getResourceType(final Data _data) {
        Assert.notNull(_data, "Data is null");

        ResourceType result = ResourceType.UNKNOWN;

        if (_data.size() >= 15) {
            result = checkSignature(_data);
        }

        return result;
    }

    private static ResourceType checkSignature(Data _data) {
        ResourceType result = ResourceType.UNKNOWN;

        try {
            byte[] buffer = _data.getData(0, 15);
            String signature = new String(buffer);

            if (signature.contains(JPG_SIGN_01) || signature.contains(JPG_SIGN_02)) {
                result = ResourceType.IMAGE_JPEG;
            } else if (signature.contains(GIF_SIGN)) {
                result = ResourceType.IMAGE_GIF;
            } else if (signature.contains(PNG_SIGN)) {
                result = ResourceType.IMAGE_PNG;
            }
        } catch (Exception e) {
            //empty
        }

        return result;
    }

    private ResourceUtil() {
        //empty
    }
}
