package http;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpRequestBase;
import util.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 20.09.2011
 */
public final class HttpTools {

    private static final String PASSWORD_TOKEN = "PASS";
    private static final String PASSWORD_REPLACEMENT = "*HIDDEN*";

    private static final Pattern PATTERN = Pattern.compile("charset=([\\w|-]+);?", Pattern.CASE_INSENSITIVE);
    private static final int GROUP_NO = 1;

    public static String removePasswordFromString(final String _data) {
        Assert.notNull(_data, "Data is null");

        String result = _data;

        String buffer = _data.toUpperCase();
        int index = buffer.indexOf(PASSWORD_TOKEN);

        if (index != -1) {
            result = _data.substring(0, index) + PASSWORD_REPLACEMENT;
        }

        return result;
    }

    public static String getCharset(final Header[] _headers) {
        Assert.notNull(_headers, "Headers is null");

        for (final Header header : _headers) {
            final String charset = getCharset(header.getValue());

            if (!charset.isEmpty()) {
                return charset;
            }
        }

        return "";
    }

    public static String getCharset(final String _headerValue) {
        Assert.notNull(_headerValue);

        final Matcher matcher = PATTERN.matcher(_headerValue);

        return matcher.find() ? matcher.group(GROUP_NO) : "";
    }

    public static String getUrlWithRequest(final String _url, final String _request) {
        Assert.isValidString(_url, "Url is not valid");
        Assert.notNull(_request, "Request is null");

        return escape(_url) + escape(_request);
    }

    public static String getHostFromMethod(final HttpRequestBase _method) {
        Assert.notNull(_method, "Method is null");

        return _method.getURI().getHost();
    }

    private static String escape(String data) {
        return data.replaceAll(" ", "%20");
    }

    private HttpTools() {
        // empty
    }
}
