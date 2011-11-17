package work.unit.resource;

import junit.framework.TestCase;
import resource.ResourceType;
import resource.ResourceUtil;
import http.data.DataFile;
import work.testutil.CompTestsUtils;

/**
 * @author Igor Usenko
 *         Date: 02.10.2008
 */
public class ResourceUtilTest extends TestCase {

    private static final String JPG_FILE = CompTestsUtils.getCompTestsRoot() + "ResourceUtilTest\\" + "jpg.jpg";
    private static final String GIF_FILE = CompTestsUtils.getCompTestsRoot() + "ResourceUtilTest\\" + "gif.gif";
    private static final String PNG_FILE = CompTestsUtils.getCompTestsRoot() + "ResourceUtilTest\\" + "png.png";

    public ResourceUtilTest(String s) {
        super(s);
    }

    public void testOne(){
        ResourceType typePng = ResourceUtil.getResourceType(new DataFile(PNG_FILE));
        ResourceType typeGif = ResourceUtil.getResourceType(new DataFile(GIF_FILE));
        ResourceType typeJpg = ResourceUtil.getResourceType(new DataFile(JPG_FILE));

        assertEquals(ResourceType.IMAGE_PNG, typePng);
        assertEquals(ResourceType.IMAGE_GIF, typeGif);
        assertEquals(ResourceType.IMAGE_JPEG, typeJpg);
    }
}
