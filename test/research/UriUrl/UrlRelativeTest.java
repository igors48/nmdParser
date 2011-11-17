package research.UriUrl;

import junit.framework.TestCase;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Igor Usenko
 *         Date: 12.06.2009
 */
public class UrlRelativeTest extends TestCase {

    public UrlRelativeTest(final String _s) {
        super(_s);
    }

    public void testSmoke() throws MalformedURLException {
        URL baseURL = new URL("http://www.rgagnon.com/sdf/dfdfd/dfdf/a.html");
        URL relativeURL = new URL(baseURL, "/sdf/javadetails/java-0001.html");
        System.out.println(relativeURL.toExternalForm());

        baseURL = new URL("http://www.autocentre.ua/ac/Auto/Conception/INFINITI-15100.html");
        relativeURL = new URL(baseURL, "/ac/09/23/images/01/m/Inf-essence_Essence_INT_005.jpg");
        System.out.println(relativeURL.toExternalForm());

        baseURL = new URL("http://www.robotreviews.com/chat/viewtopic.php?f=1&t=11721");
        relativeURL = new URL(baseURL, "./images/smilies/icon_smile.gif");
        System.out.println(relativeURL.toExternalForm());

        baseURL = new URL("http://www.ixbt.com/divideo/digital-video-guide/1-2-using-photo-camera-2.shtml");
        relativeURL = new URL(baseURL, " 1-2-using-photo-camera-2/samsung-wb550.jpg");
        System.out.println(relativeURL.toExternalForm());
    }
}
