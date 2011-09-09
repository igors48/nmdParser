package timeservice;

import java.util.Date;

/**
 * —тандартный сервис системного времени
 *
 * @author Igor Usenko
 *         Date: 01.12.2008
 */
public class StandardTimeService implements TimeService {
    private final long sessionStartTime;

    public StandardTimeService() {
        this.sessionStartTime = System.currentTimeMillis();
    }

    public synchronized long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public Date getCurrentDate() {
        return new Date(getCurrentTime());
    }

    public synchronized long getNanoTime() {
        return System.nanoTime();
    }

    public synchronized long getSessionStartTime() {
        return this.sessionStartTime;
    }
}
