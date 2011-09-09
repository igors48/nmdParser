package app.iui.schedule;

/**
 * @author Igor Usenko
 *         Date: 01.05.2011
 */
public enum Period {

    ALWAYS(0L),
    HALF_WEEKLY(Schedule.HALF_WEEK_IN_MS),
    WEEKLY(Schedule.WEEK_IN_MS),
    HALF_MONTHLY(Schedule.HALF_MONTH_IN_MS),
    MONTHLY(Schedule.MONTH_IN_MS);

    public final long periodInMs;

    private Period(final long _periodInMs) {
        this.periodInMs = _periodInMs;
    }

}
