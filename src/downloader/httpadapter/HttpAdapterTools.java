package downloader.httpadapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Igor Usenko
 *         Date: 29.01.2011
 */
public final class HttpAdapterTools {

    private static Log log = LogFactory.getLog(HttpAdapterTools.class);

    public static String getHost(final String _address) {
        Assert.isValidString(_address, "Address is not valid");

        String result = "";

        try {
            result = new URL(_address).getHost();
        } catch (MalformedURLException e) {
            // empty
        }

        if (result.isEmpty()) {
            log.debug("Can not extract Host from Url [ " + _address + " ]");
        }

        return result;
    }

    private HttpAdapterTools() {
        // empty
    }
}
