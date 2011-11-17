package util;

import com.google.gson.Gson;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 25.08.2011
 */
public final class JsonCodec {

    private static final Gson INSTANCE = new Gson();

    public static <T> T fromJson(final String _data, final Class<T> _clazz) {
        Assert.notNull(_data, "Data is null");
        Assert.notNull(_clazz, "Class is null");

        return INSTANCE.fromJson(_data, _clazz);
    }

    private JsonCodec() {
        // empty
    }
}
