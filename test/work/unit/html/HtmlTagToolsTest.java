package work.unit.html;

import junit.framework.TestCase;
import util.html.HtmlTagTools;

import java.util.Map;

/**
 * @author Igor Usenko
 *         Date: 23.06.2009
 */
public class HtmlTagToolsTest extends TestCase {

    public HtmlTagToolsTest(final String _s) {
        super(_s);
    }

    // первоначальный тест
    public void testSmoke(){
        String data = "img alt='&#171;Шерлок Холмс&#187; Гая Ричи &#8212; Холмс' title='&#171;Шерлок Холмс&#187; Гая Ричи &#8212; Холмс' vspace=10 hspace=10 border=1 width=147 height=225 src=http://static.oper.ru/data/gallery/m1048753469.jpg";

        Map<String, String> result = HtmlTagTools.parseAttributes(data);

        assertEquals(8, result.size());
        assertEquals("&#171;Шерлок Холмс&#187; Гая Ричи &#8212; Холмс", result.get("alt"));
        assertEquals("&#171;Шерлок Холмс&#187; Гая Ричи &#8212; Холмс", result.get("title"));
        assertEquals("10", result.get("hspace"));
        assertEquals("10", result.get("vspace"));
        assertEquals("1", result.get("border"));
        assertEquals("147", result.get("width"));
        assertEquals("225", result.get("height"));
        assertEquals("http://static.oper.ru/data/gallery/m1048753469.jpg", result.get("src"));
    }
    
    public void testSample01(){
        String data = "img src=\"topic-229134_files/p_tofaq-on.gif\"id=\"img25\"    class =  \"preload\" alt=\"\"";

        Map<String, String> result = HtmlTagTools.parseAttributes(data);

        assertEquals(4, result.size());
        assertEquals("topic-229134_files/p_tofaq-on.gif", result.get("src"));
        assertEquals("img25", result.get("id"));
        assertEquals("preload", result.get("class"));
        assertEquals("", result.get("alt"));
    }

    public void testSample02(){
        String data = "img src='http://feeds2.feedburner.com/~ff/oper_ru?i=E9bnId1p4Fs:WGBR6ZI5kRY:D7DqB2pKExk' border=''";

        Map<String, String> result = HtmlTagTools.parseAttributes(data);

        assertEquals(2, result.size());
        assertEquals("http://feeds2.feedburner.com/~ff/oper_ru?i=E9bnId1p4Fs:WGBR6ZI5kRY:D7DqB2pKExk", result.get("src"));
        assertEquals("", result.get("border"));
    }
}
