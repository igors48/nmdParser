package work.unit.fb2.resource;

import junit.framework.TestCase;
import converter.format.fb2.resource.resolver.Fb2ResourceResolverTools;

/**
 * @author Igor Usenko
 *         Date: 11.06.2009
 */
public class Fb2ResourceResolverToolsTest extends TestCase {

    public Fb2ResourceResolverToolsTest(final String _s) {
        super(_s);
    }

    public void testSmoke01(){
        String result = Fb2ResourceResolverTools.joinAddress("http://img.youtube.com", "path");

        assertEquals("http://img.youtube.com/path", result);
    }

    public void testSmoke02(){
        String result = Fb2ResourceResolverTools.joinAddress("http://www.robotreviews.com/chat/", "./search.php?t=11551");

        assertEquals("http://www.robotreviews.com/chat/search.php?t=11551", result);
    }
    
    public void testSmoke03(){
        String result = Fb2ResourceResolverTools.joinAddress("http://www.ixbt.com/divideo/digital-video-guide/1-2-using-photo-camera-2.shtml", "1-2-using-photo-camera-2/samsung-wb550.jpg");

        assertEquals("http://www.ixbt.com/divideo/digital-video-guide/1-2-using-photo-camera-2/samsung-wb550.jpg", result);
    }
    
    public void testSmoke04(){
        String result = Fb2ResourceResolverTools.joinAddress("http://www.autocentre.ua/news/Accidents/25316.html", "/images/stories/april09/lvovdtp_7.jpg");

        assertEquals("http://www.autocentre.ua/images/stories/april09/lvovdtp_7.jpg", result);
    }

    public void testSmoke05(){
        String result = Fb2ResourceResolverTools.joinAddress("http://www.robotreviews.com/chat/viewtopic.php?f=1&t=11721", "./images/smilies/icon_smile.gif");

        assertEquals("http://www.robotreviews.com/chat/images/smilies/icon_smile.gif", result);
    }
    
    public void testSmoke06(){
        String result = Fb2ResourceResolverTools.joinAddress("http://www.autocentre.ua/", "ac/09/27/images/01/m/News-06-08-Toyota_iQ3_1.jpg");

        assertEquals("http://www.autocentre.ua/ac/09/27/images/01/m/News-06-08-Toyota_iQ3_1.jpg", result);
    }
}
