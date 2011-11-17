package work.unit.constructor.source;

import constructor.objects.source.core.SourceUtils;
import dated.item.modification.Modification;
import dated.item.modification.stream.ModificationList;
import junit.framework.TestCase;
import timeservice.StillTimeService;
import work.testutil.ModificationTestUtils;

import java.util.ArrayList;
import java.util.List;

import static work.testutil.SourceTestUtils.bothExists;
import static work.testutil.SourceTestUtils.getForUrl;

/**
 * @author Igor Usenko
 *         Date: 06.03.2009
 */
public class SourceUtilsTest extends TestCase {

    public SourceUtilsTest(String s) {
        super(s);
    }

    // первоначальная проверка

    public void testSmoke() {
        StillTimeService timeService = new StillTimeService();

        Modification modification01 = new Modification(timeService.getCurrentDate(), "URL01");
        Modification modification02 = new Modification(timeService.getCurrentDate(), "URL02");

        ModificationList modificationList = new ModificationList();
        modificationList.add(modification01);

        List<Modification> modifications = new ArrayList<Modification>();
        modifications.add(modification02);

        ModificationList result = SourceUtils.mergeModifications(modificationList, modifications);

        assertEquals(2, result.size());
        assertTrue(bothExists("URL01", "URL02", result));
    }

    // случай : пришла модификация, а такая в архиве уже есть с такой же датой

    public void testExistsSameDate() {
        StillTimeService timeService = new StillTimeService();

        Modification modification01 = new Modification(timeService.getCurrentDate(), "URL01");
        Modification modification02 = new Modification(timeService.getCurrentDate(), "URL02");

        ModificationList modificationList = new ModificationList();
        modificationList.add(modification01);
        modificationList.add(modification02);

        List<Modification> modifications = new ArrayList<Modification>();
        modifications.add(modification02);

        ModificationList result = SourceUtils.mergeModifications(modificationList, modifications);

        assertEquals(2, result.size());
        assertTrue(bothExists("URL01", "URL02", result));
    }

    // случай : пришла модификация, а такая в архиве уже есть с такой же датой, но старше

    public void testExistsSameDateInArchiveOlder() {
        StillTimeService timeService = new StillTimeService();

        Modification modification01 = new Modification(timeService.getCurrentDate(), "URL01");
        Modification modification02 = new Modification(timeService.getCurrentDate(), "URL02");

        ModificationList modificationList = new ModificationList();
        modificationList.add(modification01);
        modificationList.add(modification02);

        timeService.changeDay(2);
        Modification modification03 = new Modification(timeService.getCurrentDate(), "URL02");
        List<Modification> modifications = new ArrayList<Modification>();
        modifications.add(modification03);

        ModificationList result = SourceUtils.mergeModifications(modificationList, modifications);

        assertEquals(2, result.size());
        assertTrue(bothExists("URL01", "URL02", result));

        Modification modification = getForUrl(modification03.getUrl(), result);
        assertEquals(modification03.getDate(), modification.getDate());
    }

    // случай : пришла модификация, а такая в архиве уже есть с такой же датой, но моложе

    public void testExistsSameDateInArchiveNewer() {
        StillTimeService timeService = new StillTimeService();

        Modification modification01 = new Modification(timeService.getCurrentDate(), "URL01");
        Modification modification02 = new Modification(timeService.getCurrentDate(), "URL02");

        ModificationList modificationList = new ModificationList();
        modificationList.add(modification01);
        modificationList.add(modification02);

        timeService.changeDay(-2);
        Modification modification03 = new Modification(timeService.getCurrentDate(), "URL02");
        List<Modification> modifications = new ArrayList<Modification>();
        modifications.add(modification03);

        ModificationList result = SourceUtils.mergeModifications(modificationList, modifications);

        assertEquals(2, result.size());
        assertTrue(bothExists("URL01", "URL02", result));

        Modification modification = getForUrl(modification03.getUrl(), result);
        assertEquals(modification02.getDate(), modification.getDate());
    }

    // случай : пришло две модификации, а такая в архиве уже есть с такой же датой, но моложе

    public void testExistsSameDateInArchiveNewerTwo() {
        StillTimeService timeService = new StillTimeService();

        Modification modification01 = new Modification(timeService.getCurrentDate(), "URL01");
        Modification modification02 = new Modification(timeService.getCurrentDate(), "URL02");

        ModificationList modificationList = new ModificationList();
        modificationList.add(modification01);
        modificationList.add(modification02);

        List<Modification> modifications = new ArrayList<Modification>();

        timeService.changeDay(-2);
        Modification modification03 = new Modification(timeService.getCurrentDate(), "URL02");
        modifications.add(modification03);

        timeService.changeDay(-3);
        Modification modification04 = new Modification(timeService.getCurrentDate(), "URL02");
        modifications.add(modification04);

        ModificationList result = SourceUtils.mergeModifications(modificationList, modifications);

        assertEquals(2, result.size());
        assertTrue(bothExists("URL01", "URL02", result));

        Modification modification = getForUrl(modification04.getUrl(), result);
        assertEquals(modification02.getDate(), modification.getDate());
    }

    // случай : пришло две модификации, а такая в архиве уже есть с такой же датой, но старше

    public void testExistsSameDateInArchiveOlderTwo() {
        StillTimeService timeService = new StillTimeService();

        Modification modification01 = new Modification(timeService.getCurrentDate(), "URL01");
        Modification modification02 = new Modification(timeService.getCurrentDate(), "URL02");

        ModificationList modificationList = new ModificationList();
        modificationList.add(modification01);
        modificationList.add(modification02);

        List<Modification> modifications = new ArrayList<Modification>();

        timeService.changeDay(2);
        Modification modification03 = new Modification(timeService.getCurrentDate(), "URL02");
        modifications.add(modification03);

        timeService.changeDay(3);
        Modification modification04 = new Modification(timeService.getCurrentDate(), "URL02");
        modifications.add(modification04);

        ModificationList result = SourceUtils.mergeModifications(modificationList, modifications);

        assertEquals(2, result.size());
        assertTrue(bothExists("URL01", "URL02", result));

        Modification modification = getForUrl(modification04.getUrl(), result);
        assertEquals(modification04.getDate(), modification.getDate());
    }

    // случай : пришло две модификации старше и моложе той, что в архиве 

    public void testExistsSameDateInArchiveOlderAndNewerTwo() {
        StillTimeService timeService = new StillTimeService();

        Modification modification01 = new Modification(timeService.getCurrentDate(), "URL01");
        Modification modification02 = new Modification(timeService.getCurrentDate(), "URL02");

        ModificationList modificationList = new ModificationList();
        modificationList.add(modification01);
        modificationList.add(modification02);

        List<Modification> modifications = new ArrayList<Modification>();

        timeService.changeDay(2);
        Modification modification03 = new Modification(timeService.getCurrentDate(), "URL02");
        modifications.add(modification03);

        timeService.changeDay(-3);
        Modification modification04 = new Modification(timeService.getCurrentDate(), "URL02");
        modifications.add(modification04);

        ModificationList result = SourceUtils.mergeModifications(modificationList, modifications);

        assertEquals(2, result.size());
        assertTrue(bothExists("URL01", "URL02", result));

        Modification modification = getForUrl(modification03.getUrl(), result);
        assertEquals(modification03.getDate(), modification.getDate());
    }

    // случай : пришла модификация, а архив пуст

    public void testArchiveEmpty() {
        StillTimeService timeService = new StillTimeService();

        Modification modification01 = new Modification(timeService.getCurrentDate(), "URL01");

        ModificationList modificationList = new ModificationList();

        List<Modification> modifications = new ArrayList<Modification>();
        modifications.add(modification01);

        ModificationList result = SourceUtils.mergeModifications(modificationList, modifications);

        assertEquals(1, result.size());

        Modification modification = getForUrl(modification01.getUrl(), result);
        ModificationTestUtils.assertModificationEquals(modification01, modification);
    }

    // случай : пришел пустой список модификаций, а архив пуст

    public void testBothEmpty() {
        StillTimeService timeService = new StillTimeService();

        ModificationList modificationList = new ModificationList();
        List<Modification> modifications = new ArrayList<Modification>();
        ModificationList result = SourceUtils.mergeModifications(modificationList, modifications);

        assertEquals(0, result.size());
    }

    // случай : пришел пустой список модификаций, а архив не пуст

    public void testNoModsArchiveNotEmpty() {
        StillTimeService timeService = new StillTimeService();

        Modification modification01 = new Modification(timeService.getCurrentDate(), "URL01");
        Modification modification02 = new Modification(timeService.getCurrentDate(), "URL02");

        ModificationList modificationList = new ModificationList();
        modificationList.add(modification01);
        modificationList.add(modification02);

        List<Modification> modifications = new ArrayList<Modification>();

        ModificationList result = SourceUtils.mergeModifications(modificationList, modifications);

        assertEquals(2, result.size());
        assertTrue(bothExists("URL01", "URL02", result));
    }

    // тест на сохранение порядка поступления модификаций

    public void testOrder() {
        StillTimeService timeService = new StillTimeService();

        ModificationList modificationList = new ModificationList();

        Modification modification01 = new Modification(timeService.getCurrentDate(), "10");
        Modification modification02 = new Modification(timeService.getCurrentDate(), "3");
        Modification modification03 = new Modification(timeService.getCurrentDate(), "a");

        List<Modification> modifications = new ArrayList<Modification>();

        modifications.add(modification01);
        modifications.add(modification02);
        modifications.add(modification03);

        ModificationList result = SourceUtils.mergeModifications(modificationList, modifications);

        assertEquals(3, result.size());
        assertEquals(modification01, result.get(0));
        assertEquals(modification02, result.get(1));
        assertEquals(modification03, result.get(2));
    }
}
