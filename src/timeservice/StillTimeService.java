package timeservice;

import util.Assert;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @author Igor Usenko
 *         Date: 27.12.2008
 */
public final class StillTimeService implements TimeService {

    private long time;

    public StillTimeService() {
        this.time = System.currentTimeMillis();
    }

    public StillTimeService(int _year, int _month, int _day, int _hour, int _minute, int _second) {
        Calendar calendar = new GregorianCalendar(_year, _month, _day, _hour, _minute, _second);
        this.time = calendar.getTimeInMillis();
    }

    public StillTimeService(Date _date) {
        Assert.notNull(_date);
        this.time = _date.getTime();
    }

    public long getCurrentTime() {
        return this.time;
    }

    public Date getCurrentDate() {
        return new Date(getCurrentTime());
    }

    public long getNanoTime() {
        return System.nanoTime();
    }

    public long getSessionStartTime() {
        return this.time;
    }

    public void changeDay(int _count) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(this.time);
        calendar.add(Calendar.DAY_OF_YEAR, _count);
        this.time = calendar.getTimeInMillis();
    }

    public void changeSecond(int _count) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTimeInMillis(this.time);
        calendar.add(Calendar.SECOND, _count);
        this.time = calendar.getTimeInMillis();
    }

}
