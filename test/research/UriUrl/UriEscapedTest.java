package research.UriUrl;

import junit.framework.TestCase;
import org.apache.http.client.utils.URIUtils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * @author Igor Usenko
 *         Date: 12.06.2009
 */
public class UriEscapedTest extends TestCase {

    public UriEscapedTest(final String _s) {
        super(_s);
    }

    public void testSmoke() throws URISyntaxException, MalformedURLException {
        URI uri = URIUtils.createURI("http", "www.google.com", -1, "path", "http:?", null);
        System.out.println(uri.toURL().toExternalForm());
    }
}
