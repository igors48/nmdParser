package work.unit.ftf;

import junit.framework.TestCase;
import flowtext.Paragraph;
import flowtext.Section;
import flowtext.Title;
import flowtext.FlowTextType;

/**
 * @author Igor Usenko
 *         Date: 14.10.2008
 */
public class SectionTest extends TestCase {

    public SectionTest(String s) {
        super(s);
    }

    public void testOne() throws Section.SectionException {
        Paragraph paragraph01 = new Paragraph();

        paragraph01.insertCodeText("12");
        paragraph01.insertEmphasisText("34");
        paragraph01.insertSimpleText("56");
        paragraph01.insertStrongText("78");

        Paragraph paragraph02 = new Paragraph();

        paragraph02.insertResource("base", "address");
        paragraph02.insertCodeText("910");
        paragraph02.insertResource("base", "address");
        paragraph02.insertResource("base", "address");
        paragraph02.insertEmphasisText("1112");
        paragraph02.insertResource("base", "address");
        paragraph02.insertSimpleText("1314");
        paragraph02.insertResource("base", "address");
        paragraph02.insertStrongText("1516");
        paragraph02.insertResource("base", "address");

        Paragraph titleParagraph = new Paragraph();
        titleParagraph.insertSimpleText("title");
        Title title = new Title();
        title.insertParagraph(titleParagraph);
        title.insertEmptyLine();
        
        Section section = new Section("ID48");
        section.insertEmptyLine();
        section.insertTitle(title);
        section.insertParagraph(paragraph01);
        section.insertEmptyLine();
        section.insertParagraph(paragraph02);

        String result = section.getBrief(2);
        assertEquals("12", result);

        result = section.getBrief(11);
        assertEquals("12345678910", result);

        result = section.getBrief(23);
        assertEquals("12345678910111213141516", result);

        result = section.getBrief(32);
        assertEquals("12345678910111213141516", result);
    }

    public void testGetSize() throws Section.SectionException {
        Paragraph paragraph01 = new Paragraph();

        paragraph01.insertCodeText("12");
        paragraph01.insertEmphasisText("34");
        paragraph01.insertSimpleText("56");
        paragraph01.insertStrongText("78");

        Paragraph paragraph02 = new Paragraph();

        paragraph02.insertResource("base", "address");
        paragraph02.insertCodeText("910");
        paragraph02.insertResource("base", "address");
        paragraph02.insertResource("base", "address");
        paragraph02.insertEmphasisText("1112");
        paragraph02.insertResource("base", "address");
        paragraph02.insertSimpleText("1314");
        paragraph02.insertResource("base", "address");
        paragraph02.insertStrongText("1516");
        paragraph02.insertResource("base", "address");

        Paragraph titleParagraph = new Paragraph();
        titleParagraph.insertSimpleText("title");
        Title title = new Title();
        title.insertParagraph(titleParagraph);
        title.insertEmptyLine();

        Section section = new Section("ID48");
        section.insertEmptyLine();
        section.insertTitle(title);
        section.insertParagraph(paragraph01);
        section.insertEmptyLine();
        section.insertParagraph(paragraph02);

        int result = section.getSize();
        assertEquals(23, result);
    }

    public void testRemoveDoubleEmptyLines() throws Section.SectionException {

        Section section = new Section("ID");

        Paragraph paragraph01 = new Paragraph();

        paragraph01.insertCodeText("12");
        paragraph01.insertEmphasisText("34");
        paragraph01.insertSimpleText("56");
        paragraph01.insertStrongText("78");

        Paragraph paragraph02 = new Paragraph();

        paragraph02.insertResource("base", "address");
        paragraph02.insertCodeText("910");
        paragraph02.insertResource("base", "address");
        paragraph02.insertResource("base", "address");
        paragraph02.insertEmphasisText("1112");
        paragraph02.insertResource("base", "address");
        paragraph02.insertSimpleText("1314");
        paragraph02.insertResource("base", "address");
        paragraph02.insertStrongText("1516");
        paragraph02.insertResource("base", "address");

        section.insertEmptyLine();
        section.insertEmptyLine();
        section.insertParagraph(paragraph01);
        section.insertEmptyLine();
        section.insertEmptyLine();
        section.insertEmptyLine();
        section.insertEmptyLine();
        section.insertParagraph(paragraph02);
        section.insertEmptyLine();
        section.insertEmptyLine();

        section.removeDoubleEmptyLines();

        int emptyCount = 0;

        for (int index = 0; index < section.getContent().size(); ++index){

            if(section.getContent().get(index).getType() == FlowTextType.EMPTY_LINE){
                ++emptyCount;
            }
        }

        assertEquals(3, emptyCount);
    }
}
