package work.unit.html;

import junit.framework.TestCase;
import html.parser.ChunkSource;

/**
 * @author Igor Usenko
 *         Date: 17.09.2008
 */
public class ChunkSourceTest extends TestCase {

    private static final String OPEN_BRACE = "<";
    private static final String CLOSE_BRACE = ">";

    public ChunkSourceTest(String s) {
        super(s);
    }

    public void test01(){
        ChunkSource source = new ChunkSource("...>", new String[]{OPEN_BRACE, CLOSE_BRACE});

        String first = source.get();
        String second = source.get();
        String third = source.get();

        assertEquals("...", first);
        assertEquals(">", second);
        assertEquals(null, third);
    }

    public void test02(){
        ChunkSource source = new ChunkSource("...>...", new String[]{OPEN_BRACE, CLOSE_BRACE});

        String first = source.get();
        String second = source.get();
        String third = source.get();
        String fourth = source.get();

        assertEquals("...", first);
        assertEquals(">", second);
        assertEquals("...", third);
        assertEquals(null, fourth);
    }

    public void test03(){
        ChunkSource source = new ChunkSource("...<...", new String[]{OPEN_BRACE, CLOSE_BRACE});

        String first = source.get();
        String second = source.get();
        String third = source.get();
        String fourth = source.get();

        assertEquals("...", first);
        assertEquals("<", second);
        assertEquals("...", third);
        assertEquals(null, fourth);
    }

    public void test04(){
        ChunkSource source = new ChunkSource("<...<...>", new String[]{OPEN_BRACE, CLOSE_BRACE});

        String first = source.get();
        String second = source.get();
        String third = source.get();
        String fourth = source.get();
        String fifth = source.get();
        String sixth = source.get();

        assertEquals("<", first);
        assertEquals("...", second);
        assertEquals("<", third);
        assertEquals("...", fourth);
        assertEquals(">", fifth);
        assertEquals(null, sixth);
    }

    public void test05(){
        String data = "width=535 height = 25 src=\"topic-229134_files/p_tofaq-on.gif\"id=\"img25\"    class =  \"preload\" alt=\"\"";

        ChunkSource source = new ChunkSource(data, new String[]{" ", "=", "\""});

        String token01 = source.get();
        String token02 = source.get();
        String token03 = source.get();
        String token04 = source.get();
        String token05 = source.get();
        String token06 = source.get();
        String token07 = source.get();
        String token08 = source.get();
        String token09 = source.get();
        String token10 = source.get();
        String token11 = source.get();
        String token12 = source.get();
        String token13 = source.get();
        String token14 = source.get();
        String token15 = source.get();
        String token16 = source.get();
        String token17 = source.get();
        String token18 = source.get();
        String token19 = source.get();
        String token20 = source.get();
        String token21 = source.get();
        String token22 = source.get();
        String token23 = source.get();
        String token24 = source.get();
        String token25 = source.get();
        String token26 = source.get();
        String token27 = source.get();
        String token28 = source.get();
        String token29 = source.get();
        String token30 = source.get();
        String token31 = source.get();
        String token32 = source.get();
        String token33 = source.get();
        String token34 = source.get();
        String token35 = source.get();
        String token36 = source.get();
        String token37 = source.get();
        String token38 = source.get();

    }
}
