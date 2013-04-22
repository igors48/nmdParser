package app;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.IOTools;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author Igor Usenko
 *         Date: 13.04.2010
 */
public final class VersionInfoTools {

    private static final String VERSION_FILE = "version.properties";
    private static final String UNKNOWN_TOKEN = "UNKNOWN";
    private static final String BUILD_PROPERTY_KEY = "build";
    private static final String VERSION_PROPERTY_KEY = "version";
    private static final String TITLE_PROPERTY_KEY = "title";

    private static final Log log = LogFactory.getLog(VersionInfoTools.class);

    public static VersionInfo readVersionInfo(final Class _class) {
        InputStream in = null;

        String build = "";
        String version = "";
        String title = "";

        try {
            in = _class.getResourceAsStream(VERSION_FILE);

            Properties properties = new Properties();
            properties.load(in);

            build = properties.getProperty(BUILD_PROPERTY_KEY, UNKNOWN_TOKEN);
            version = properties.getProperty(VERSION_PROPERTY_KEY, UNKNOWN_TOKEN);
            title = properties.getProperty(TITLE_PROPERTY_KEY, UNKNOWN_TOKEN);

        } catch (Exception e) {
            log.error("Error reading version file [ " + VERSION_FILE + " ]");
        } finally {
            IOTools.close(in);
        }

        return new VersionInfo(title, version, build);
    }

    private VersionInfoTools() {
        // empty
    }
}
