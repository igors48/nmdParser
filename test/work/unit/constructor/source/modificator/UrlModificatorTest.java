package work.unit.constructor.source.modificator;

import constructor.objects.source.core.ModificationProcessor;
import constructor.objects.source.core.processor.UrlModificator;
import dated.item.modification.Modification;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 28.11.2009
 */
public class UrlModificatorTest extends TestCase {

    public UrlModificatorTest(final String _s) {
        super(_s);
    }

    // первоначальный тест

    public void testSmoke() throws ModificationProcessor.ModificationProcessorException {
        UrlModificator modificator = new UrlModificator(new MockProcessor("", ""));

        List<Modification> modifications = new ArrayList<Modification>();
        Modification modification = new Modification(new Date(), "url");
        modifications.add(modification);

        List<Modification> processed = modificator.process(modifications);

        assertEquals(1, processed.size());
        assertEquals("url", processed.get(0).getUrl());
    }

    // тест списка из трех модификаций - порядок сохранен

    public void testThreeMods() throws ModificationProcessor.ModificationProcessorException {
        UrlModificator modificator = new UrlModificator(new MockProcessor("", ""));

        List<Modification> modifications = new ArrayList<Modification>();
        Modification modification01 = new Modification(new Date(), "url01");
        Modification modification02 = new Modification(new Date(), "url02");
        Modification modification03 = new Modification(new Date(), "url03");
        modifications.add(modification01);
        modifications.add(modification02);
        modifications.add(modification03);

        List<Modification> processed = modificator.process(modifications);

        assertEquals(3, processed.size());
        assertEquals("url01", processed.get(0).getUrl());
        assertEquals("url02", processed.get(1).getUrl());
        assertEquals("url03", processed.get(2).getUrl());
    }

    // постобработчик адресов вернул более одного адреса

    public void testPostMoreThanOne() throws ModificationProcessor.ModificationProcessorException {
        UrlModificator modificator = new UrlModificator(new DoublerMockProcessor("", ""));

        List<Modification> modifications = new ArrayList<Modification>();
        Modification modification = new Modification(new Date(), "url");
        modifications.add(modification);

        List<Modification> processed = modificator.process(modifications);

        assertEquals(2, processed.size());
        assertEquals("url1", processed.get(0).getUrl());
        assertEquals("url2", processed.get(1).getUrl());
    }
}
