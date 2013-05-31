package work.unit.htmlconverter.simple;

import flowtext.Section;
import html.Converter;
import junit.framework.TestCase;

import java.util.List;

import static work.testutil.HtmlConverterTestUtils.getResult;

/**
 * @author Igor Usenko
 *         Date: 02.05.2009
 */
public class HtmlConverterImageRefInTableTest extends TestCase {

    private static final String TEST_PATTERN_01 = "<table width=33% cellspacing=0 cellpadding=0 border=0><tr><td align=center>\n" +
            "<img src=/images/forms/10195.jpeg alt='ѕомимо всего прочего кузнечики <i>Anabrus simplex</i> известны групповым каннибализмом, и мы <a href=\"http://www.membrana.ru/articles/global/2007/03/26/191800.html\">рассказывали</a> об этом. Ќа данном снимке они втроЄм поедают собрата (фото с сайта animalpicturesarchive.com). ' width=478 height=288></td></tr><tr><td><img src=/images/1.gif width=1 height=5><br><font class=az10>ѕомимо всего прочего кузнечики <i>Anabrus simplex</i> известны групповым каннибализмом, и мы <a href=\"http://www.membrana.ru/articles/global/2007/03/26/191800.html\">рассказывали</a> об этом. Ќа данном снимке они втроЄм поедают собрата (фото с сайта animalpicturesarchive.com). </font></td></tr></table>";

    public HtmlConverterImageRefInTableTest(String _s) {
        super(_s);
    }

    // тест на баг по адресу http://www.membrana.ru/lenta/?9257
    // картинка вставленна€ в таблицу не вставилась в выходной документ

    public void testImageInTable() throws Converter.ConverterException {
        List<Section> result = getResult(TEST_PATTERN_01);

        assertNotNull(result);
    }
}
