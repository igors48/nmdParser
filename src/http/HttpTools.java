package http;

import org.apache.http.Header;
import util.Assert;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 20.09.2011
 */
public final class HttpTools {

    private static final Pattern PATTERN = Pattern.compile("charset=([\\w|-]+);?", Pattern.CASE_INSENSITIVE);
    private static final int GROUP_NO = 1;

    public static String getCharset(final Header[] _headers) {
        Assert.notNull(_headers, "Headers is null");

        for(final Header header : _headers) {
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

        return matcher.find() ?  matcher.group(GROUP_NO) : "";
    }

    private HttpTools() {
        // empty
    }
}
