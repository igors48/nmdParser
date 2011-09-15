package research.UriUrl;

import junit.framework.TestCase;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.URIException;


/**
 * @author Igor Usenko
 *         Date: 12.06.2009
 */
public class UriEscapedTest extends TestCase {

    public UriEscapedTest(final String _s) {
        super(_s);
    }

    public void testSmoke() throws URIException {
        URI uri = new URI("http", null, "www.google.com",  -1, "path", "http:?", null);

        System.out.println(uri);
    }
}
