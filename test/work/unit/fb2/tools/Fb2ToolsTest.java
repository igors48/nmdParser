package work.unit.fb2.tools;

import junit.framework.TestCase;
import converter.format.fb2.Fb2Tools;

/**
 * @author Igor Usenko
 *         Date: 09.05.2009
 */
public class Fb2ToolsTest extends TestCase {
    private static final String NUMERIC_ENTITY_FIXTURE = "asdsadf&#48;asdasd";
    private static final String NOT_NUMERIC_ENTITY_FIXTURE_01 = "asdsadf&48;asdasd";
    private static final String NOT_NUMERIC_ENTITY_FIXTURE_02 = "asdsadf&#s48;asdasd";

    private static final String CHARACHTER_ENTITY_FIXTURE = "asdsadf&quot;asdasd";
    private static final String NOT_CHARACHTER_ENTITY_FIXTURE_01 = "asdsadf&2quot;asdasd";

    private static final String STANDALONE_AMP_FIXTURE = "asdsadf&asdasd";
    private static final String NOT_STANDALONE_AMP_FIXTURE_01 = "asdsadf&asd;asd";
    private static final String NOT_STANDALONE_AMP_FIXTURE_02 = "asdsadf&#145;asd";

    private static final String REAL_FIXTURE_01 = "&_t=rec&id=25625";
    private static final String REAL_FIXTURE_02 = "http://fotobank.mediaport.info/galery.php?theme=23&collapsed=10&galery_id=1164&#9001;=rus";

    public Fb2ToolsTest(final String _s) {
        super(_s);
    }

    public void testNumericEntity(){
        assertTrue(Fb2Tools.isNumericEntity(NUMERIC_ENTITY_FIXTURE, NUMERIC_ENTITY_FIXTURE.indexOf("&")));
    }

    public void testNotNumericEntity(){
        assertFalse(Fb2Tools.isNumericEntity(NOT_NUMERIC_ENTITY_FIXTURE_01, NOT_NUMERIC_ENTITY_FIXTURE_01.indexOf("&")));
        assertFalse(Fb2Tools.isNumericEntity(NOT_NUMERIC_ENTITY_FIXTURE_02, NOT_NUMERIC_ENTITY_FIXTURE_02.indexOf("&")));
    }
    
    public void testCharacterEntity(){
        assertTrue(Fb2Tools.isCharacterEntity(CHARACHTER_ENTITY_FIXTURE, CHARACHTER_ENTITY_FIXTURE.indexOf("&")));
    }

    public void testNotCharacterEntity(){
        assertFalse(Fb2Tools.isCharacterEntity(NOT_CHARACHTER_ENTITY_FIXTURE_01, NOT_CHARACHTER_ENTITY_FIXTURE_01.indexOf("&")));
    }

    public void testStandaloneAmp(){
        assertTrue(Fb2Tools.isStandaloneAmp(STANDALONE_AMP_FIXTURE, STANDALONE_AMP_FIXTURE.indexOf("&")));
    }

    public void testNotStandaloneAmp(){
        assertFalse(Fb2Tools.isStandaloneAmp(NOT_STANDALONE_AMP_FIXTURE_01, NOT_STANDALONE_AMP_FIXTURE_01.indexOf("&")));
        assertFalse(Fb2Tools.isStandaloneAmp(NOT_STANDALONE_AMP_FIXTURE_02, NOT_STANDALONE_AMP_FIXTURE_02.indexOf("&")));
    }

    public void testResolveRealAmps(){
        String result = Fb2Tools.resolveAmps("a&b");

        assertEquals("a&amp;b", result);
    }

    public void testResolveRealAmpsAtTheEnd(){
        String result = Fb2Tools.resolveAmps("a&");

        assertEquals("a&amp;", result);
    }

    public void testResolveRealAmpsAtTheBegin(){
        String result = Fb2Tools.resolveAmps("&a");

        assertEquals("&amp;a", result);
    }

    public void testResolveAmpEntityAmps(){
        String result = Fb2Tools.resolveAmps("a&amp;b");

        assertEquals("a&amp;b", result);
    }

    public void testResolveCharEntityAmps(){
        String result = Fb2Tools.resolveAmps("a&deg;b");

        assertEquals("a&deg;b", result);
    }

    public void testResolveNumEntityAmps(){
        String result = Fb2Tools.resolveAmps("a&#123;b");

        assertEquals("a&#123;b", result);
    }

    public void testRealFixture01(){
        String result = Fb2Tools.resolveAmps(REAL_FIXTURE_01);

        assertEquals("&amp;_t=rec&amp;id=25625", result);
    }

    public void testRealFixture02(){
        String result = Fb2Tools.resolveAmps(REAL_FIXTURE_02);

        assertEquals("http://fotobank.mediaport.info/galery.php?theme=23&amp;collapsed=10&amp;galery_id=1164&#9001;=rus", result);
    }

    public void testProcEntSmoke(){
        String result = Fb2Tools.processEntities("&&amp;&lt;&gt;&apos;&quot;&nbsp;");

        assertEquals("&amp;&amp;&lt;&gt;&quot;&quot;&#160;", result);
    }

    public void testProcEntWithNotSup(){
        String result = Fb2Tools.processEntities("&&amp;&lt;&gt;&apos;&quot;&nbsp;&diams;");

        assertEquals("&amp;&amp;&lt;&gt;&quot;&quot;&#160;[?]", result);
    }

    public void testProcEntWithNotSupAndNumeric(){
        String result = Fb2Tools.processEntities("&&amp;&lt;&#48;&gt;&apos;&quot;&nbsp;&diams;");

        assertEquals("&amp;&amp;&lt;&#48;&gt;&quot;&quot;&#160;[?]", result);
    }
}

