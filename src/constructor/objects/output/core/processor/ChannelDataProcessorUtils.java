package constructor.objects.output.core.processor;

import java.text.SimpleDateFormat;

/**
 * Различные утилиты для ChannelDataListProcessorEx
 *
 * @author Igor Usenko
 *         Date: 28.12.2008
 */
public final class ChannelDataProcessorUtils {

    private static final SimpleDateFormat ID_FORMAT = new SimpleDateFormat("'nmd'yyyyMMddHHmmss'-'SSS");

    public static String getId(final long _time) {
        return ID_FORMAT.format(_time);
    }

    private ChannelDataProcessorUtils() {
        // empty
    }
}