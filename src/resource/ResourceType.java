package resource;

import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 31.08.2008
 */
public enum ResourceType {
    IMAGE_JPEG, IMAGE_PNG, IMAGE_GIF, UNKNOWN;

    public static String getType(final ResourceType _type) {
        Assert.notNull(_type, "Resource type is null");
        Assert.isFalse(_type == ResourceType.UNKNOWN, "Resource type is unknown");

        switch (_type) {
            case IMAGE_JPEG: {
                return "image/jpeg";
            }
            case IMAGE_PNG: {
                return "image/png";
            }
        }

        return "";
    }
}
