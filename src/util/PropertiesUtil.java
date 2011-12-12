package util;

import org.apache.commons.logging.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static util.CollectionUtils.newArrayList;

/**
 * Утилиты для работы с properties
 *
 * @author Igor Usenko
 *         Date: 08.10.2009
 */
public final class PropertiesUtil {

    private static final String DIVIDER_STRING = " ======= ";

    public static void logProperties(final String title, final Properties properties, final Log _log) {
        List<String> props = newArrayList();

        for (Object key : properties.keySet()) {
            props.add(key + " = " + properties.getProperty((String) key));
        }

        Collections.sort(props);

        _log.debug(DIVIDER_STRING + title + DIVIDER_STRING);

        for (String current : props) {
            _log.debug(current);
        }
    }

    private PropertiesUtil() {
        // empty
    }
}
