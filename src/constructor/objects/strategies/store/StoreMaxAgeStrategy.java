package constructor.objects.strategies.store;

import constructor.objects.strategies.StoreStrategy;
import dated.Dated;
import timeservice.TimeService;
import util.Assert;

/**
 * Стратегия хранения элеметнов не старше заданного порога
 *
 * @author Igor Usenko
 *         Date: 06.03.2009
 */
public class StoreMaxAgeStrategy implements StoreStrategy {

    private final TimeService timeService;
    private final long maxAge;

    public StoreMaxAgeStrategy(final long _maxAge, final TimeService _timeService) {
        Assert.notNull(_timeService, "Time service is null.");
        Assert.greater(_maxAge, 0, "Max age <= 0");

        this.maxAge = _maxAge;
        this.timeService = _timeService;
    }

    public boolean accepted(final Dated _dated) {
        Assert.notNull(_dated, "Dated element is null");

        long limit = this.timeService.getCurrentTime() - this.maxAge;
        long current = _dated.getDate().getTime();

        return current > limit;
    }
}