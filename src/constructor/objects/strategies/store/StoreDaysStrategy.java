package constructor.objects.strategies.store;

import constructor.objects.strategies.StoreStrategy;
import dated.Dated;
import timeservice.TimeService;
import util.Assert;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author Igor Usenko
 *         Date: 06.03.2009
 */
public class StoreDaysStrategy implements StoreStrategy {

    private final TimeService timeService;
    private final int days;

    public StoreDaysStrategy(int _days, TimeService _timeService) {
        Assert.notNull(_timeService, "Time service is null.");
        Assert.greater(_days, 0, "Days value <= 0");

        this.days = _days;
        this.timeService = _timeService;
    }

    public boolean accepted(final Dated _dated) {
        Assert.notNull(_dated);

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(_dated.getDate());
        calendar.add(Calendar.DAY_OF_YEAR, this.days);
        long treshold = calendar.getTimeInMillis();

        return this.timeService.getCurrentTime() <= treshold;
    }
}
