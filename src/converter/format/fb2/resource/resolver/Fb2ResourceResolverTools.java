package converter.format.fb2.resource.resolver;

import util.Assert;
import util.PathTools;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Igor Usenko
 *         Date: 11.06.2009
 */
public final class Fb2ResourceResolverTools {

    public static String normalizeLast(final String _path) {
        String result = _path;

        while (result.endsWith("/") || (result.endsWith("\\"))) {
            int len = result.length();
            result = result.substring(0, len - 1);
        }

        return result + "/";
    }

    public static String normalizeFirst(final String _path) {
        String result = _path;

        while (result.startsWith("/") || (result.startsWith("\\"))) {
            result = result.substring(1);
        }

        return result;
    }

    public static String joinAddress(final String _base, final String _address) {
        Assert.isValidString(_base, "Base is not valid.");
        Assert.isValidString(_address, "Address is not valid.");

        String result = "";

        try {
            URL baseURL = new URL(_base);
            //URL relativeURL = new URL(baseURL, "/" + normalizeFirst(_address));
            URL relativeURL = new URL(baseURL, _address);
            result = relativeURL.toExternalForm();
        } catch (MalformedURLException e) {
            result = _base + _address;
        }

        return result;
    }

    public static String getHostName(String _address) {
        Assert.isValidString(_address, "Address is not valid.");

        String result = _address;

        int doubleIndex = _address.indexOf("//");
        int singleIndex = -1;

        if (doubleIndex != -1) {
            singleIndex = _address.indexOf("/", doubleIndex + 2);
        }

        if (doubleIndex != -1 && singleIndex != -1) {
            result = _address.substring(0, singleIndex + 1);
        }
        return Fb2ResourceResolverTools.normalizeLast(result);
    }

    private static String normalizeBase(final String _base) {
        String result = _base;

        int httpEnd = _base.indexOf("://");

        if (httpEnd == -1) {
            httpEnd = 0;
        } else {
            httpEnd += 3;
        }

        String delimiter = PathTools.getDelimiter(result);

        if (!result.endsWith(delimiter)) {
            int index = result.lastIndexOf(delimiter);

            if (index > httpEnd) {
                result = result.substring(0, index + 1);
            }
        }

        return result;
    }

    private static String getFirstDirectory(final String _address) {
        String result = "";
        String delimiter = PathTools.getDelimiter(_address);

        if ("\\".equalsIgnoreCase(delimiter)) {
            delimiter = "\\\\";
        }

        String address = normalizeFirst(_address);

        if (!delimiter.isEmpty()) {
            String[] parts = address.split(delimiter);

            if (parts.length > 1) {
                result = parts[0];
            }
        }

        return result;
    }

    private Fb2ResourceResolverTools() {
        // empty
    }
}
