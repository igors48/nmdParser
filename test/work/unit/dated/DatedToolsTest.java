package work.unit.dated;

import junit.framework.TestCase;
import timeservice.StillTimeService;
import work.testutil.AtdcTestUtils;
import dated.item.atdc.AtdcItem;
import dated.DatedTools;

/**
 * @author Igor Usenko
 *         Date: 26.04.2009
 */
public class DatedToolsTest extends TestCase {

    public DatedToolsTest(String _s) {
        super(_s);
    }

    // первоначальный тест
    public void testSmoke(){
        StillTimeService timeService = new StillTimeService();
        AtdcItem item01 = AtdcTestUtils.getAtdcNoTitleItem(timeService, 1);
        timeService.changeDay(3);
        AtdcItem item02 = AtdcTestUtils.getAtdcNoTitleItem(timeService, 2);

        AtdcItem latest = (AtdcItem) DatedTools.latest(item01, item02);

        assertTrue(AtdcTestUtils.atdcItemEquals(item02, latest));
    }

    // тест равных по времени элементов 
    public void testEquals(){
        StillTimeService timeService = new StillTimeService();
        AtdcItem item01 = AtdcTestUtils.getAtdcNoTitleItem(timeService, 1);
        AtdcItem item02 = AtdcTestUtils.getAtdcNoTitleItem(timeService, 2);

        AtdcItem latest = (AtdcItem) DatedTools.latest(item01, item02);

        assertTrue(AtdcTestUtils.atdcItemEquals(item02, latest));
    }

    // первоначальный тест с переставленными параметрами 
    public void testSmokeInv(){
        StillTimeService timeService = new StillTimeService();
        AtdcItem item01 = AtdcTestUtils.getAtdcNoTitleItem(timeService, 1);
        timeService.changeDay(3);
        AtdcItem item02 = AtdcTestUtils.getAtdcNoTitleItem(timeService, 2);

        AtdcItem latest = (AtdcItem) DatedTools.latest(item02, item01);

        assertTrue(AtdcTestUtils.atdcItemEquals(item02, latest));
    }


}
