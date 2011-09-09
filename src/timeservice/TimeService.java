package timeservice;

import java.util.Date;

/**
 * @author Igor Usenko
 *         Date: 01.12.2008
 */
public interface TimeService {
    long getCurrentTime();

    Date getCurrentDate();

    long getNanoTime();

    long getSessionStartTime();
}
