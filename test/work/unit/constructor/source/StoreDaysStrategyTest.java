package work.unit.constructor.source;

import junit.framework.TestCase;
import constructor.objects.strategies.store.StoreDaysStrategy;
import timeservice.StillTimeService;
import dated.item.modification.Modification;

/**
 * @author Igor Usenko
 *         Date: 06.03.2009
 */
public class StoreDaysStrategyTest extends TestCase {

    public StoreDaysStrategyTest(String s) {
        super(s);
    }

    // проверка реакции на разные даты
    public void testAcceptance() {
        StillTimeService timeService01 = new StillTimeService();

        StoreDaysStrategy strategy = new StoreDaysStrategy(3, timeService01);

        Modification modification = new Modification(timeService01.getCurrentDate(), "URL01");
        assertTrue(strategy.accepted(modification));

        timeService01.changeDay(-1);
        modification = new Modification(timeService01.getCurrentDate(), "URL01");
        assertTrue(strategy.accepted(modification));

        timeService01.changeDay(-1);
        modification = new Modification(timeService01.getCurrentDate(), "URL01");
        assertTrue(strategy.accepted(modification));

        timeService01.changeDay(-1);
        modification = new Modification(timeService01.getCurrentDate(), "URL01");
        assertTrue(strategy.accepted(modification));
    }

    // проверка реакции на будущее
    public void testFutureAcceptance() {
        StillTimeService timeService01 = new StillTimeService();

        StoreDaysStrategy strategy = new StoreDaysStrategy(3, timeService01);

        timeService01.changeDay(1);
        Modification modification = new Modification(timeService01.getCurrentDate(), "URL01");
        assertTrue(strategy.accepted(modification));
    }

}
