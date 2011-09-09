package work.unit.ftf;

import junit.framework.TestCase;
import flowtext.Paragraph;

/**
 * @author Igor Usenko
 *         Date: 14.10.2008
 */
public class ParagraphTest extends TestCase {

    public ParagraphTest(String s) {
        super(s);
    }

    public void testGetBriefWithTextOnly(){
        Paragraph paragraph = new Paragraph();

        paragraph.insertCodeText("12");
        paragraph.insertEmphasisText("34");
        paragraph.insertSimpleText("56");
        paragraph.insertStrongText("78");

        String result = paragraph.getBrief(4);
        assertEquals("1234", result);

        result = paragraph.getBrief(8);
        assertEquals("12345678", result);

        result = paragraph.getBrief(16);
        assertEquals("12345678", result);
    }

    public void testGetBriefWithTextAndResource(){
        Paragraph paragraph = new Paragraph();

        paragraph.insertResource("base", "address");
        paragraph.insertCodeText("12");
        paragraph.insertResource("base", "address");
        paragraph.insertResource("base", "address");
        paragraph.insertEmphasisText("34");
        paragraph.insertResource("base", "address");
        paragraph.insertSimpleText("56");
        paragraph.insertResource("base", "address");
        paragraph.insertStrongText("78");
        paragraph.insertResource("base", "address");

        String result = paragraph.getBrief(4);
        assertEquals("1234", result);

        result = paragraph.getBrief(8);
        assertEquals("12345678", result);

        result = paragraph.getBrief(16);
        assertEquals("12345678", result);
    }

    public void testGetSize(){
        Paragraph paragraph = new Paragraph();

        paragraph.insertResource("base", "address");
        paragraph.insertCodeText("12");
        paragraph.insertResource("base", "address");
        paragraph.insertResource("base", "address");
        paragraph.insertEmphasisText("34");
        paragraph.insertResource("base", "address");
        paragraph.insertSimpleText("56");
        paragraph.insertResource("base", "address");
        paragraph.insertStrongText("78");
        paragraph.insertResource("base", "address");

        int result = paragraph.getSize();
        assertEquals(8, result);
    }
}
