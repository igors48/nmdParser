package work.unit.html;

import html.parser.tag.HtmlTag;
import html.parser.tag.HtmlTagStatus;
import junit.framework.TestCase;

/**
 * @author Igor Usenko
 *         Date: 20.09.2008
 */
public class HtmlTagTest extends TestCase {

    public HtmlTagTest(String s) {
        super(s);
    }

    public void test01() {
        String data = "img src=\"topic-229134_files/p_tofaq-on.gif\"id=\"img25\"    class =  \"preload\" alt=\"\"";

        HtmlTag tag = new HtmlTag(data, new String[]{"I", "IMG"});

        assertEquals(4, tag.getAttributes().size());
        assertEquals("IMG", tag.getId());
        assertEquals(HtmlTagStatus.NORMAL, tag.getStatus());
        assertEquals("topic-229134_files/p_tofaq-on.gif", tag.getAttribute("src"));
        assertEquals("img25", tag.getAttribute("id"));
        assertEquals("preload", tag.getAttribute("class"));
        assertEquals("", tag.getAttribute("alt"));
    }

    public void test02() {
        String data = "imgcacaca";

        HtmlTag tag = new HtmlTag(data, new String[]{"I", "IMG"});

        assertEquals(0, tag.getAttributes().size());
        //assertEquals(HtmlTagStatus.MALFORMED, tag.getStatus());
        assertEquals("IMG", tag.getId());
    }

    public void test03() {
        String data = "hiamgcacaca";

        HtmlTag tag = new HtmlTag(data, new String[]{"I", "IMG"});

        assertEquals(0, tag.getAttributes().size());
        assertEquals(HtmlTagStatus.UNKNOWN, tag.getStatus());
        assertEquals("hiamgcacaca", tag.getId());
    }

    public void test04() {
        String data = "img width=535 height = 25 src=\"topic-229134_files/p_tofaq-on.gif\"id=\"img25\"    class =  \"preload\" alt=\"\"";

        HtmlTag tag = new HtmlTag(data, new String[]{"I", "IMG"});

        assertEquals(6, tag.getAttributes().size());
        assertEquals("IMG", tag.getId());
        assertEquals(HtmlTagStatus.NORMAL, tag.getStatus());
        assertEquals("topic-229134_files/p_tofaq-on.gif", tag.getAttribute("src"));
        assertEquals("img25", tag.getAttribute("id"));
        assertEquals("preload", tag.getAttribute("class"));
        assertEquals("", tag.getAttribute("alt"));
        assertEquals("535", tag.getAttribute("width"));
        assertEquals("25", tag.getAttribute("height"));
    }

    // посвящен баге при разборе кода в тупичке Гоблина 20 июня 2009

    public void testReal01() {
        String data = "img alt='&#171;Шерлок Холмс&#187; Гая Ричи &#8212; Холмс' title='&#171;Шерлок Холмс&#187; Гая Ричи &#8212; Холмс' vspace=10 hspace=10 border=1 width=147 height=225 src=http://static.oper.ru/data/gallery/m1048753469.jpg";

        HtmlTag tag = new HtmlTag(data, new String[]{"I", "IMG"});

        assertEquals(8, tag.getAttributes().size());
    }
}
