package util;

/**
 * @author Alex Usun
 */
public final class TimeTools {

    private static final long MILLISECONDS_PER_SECOND = 1000;
    private static final long MILLISECONDS_PER_MINUTE = 60 * MILLISECONDS_PER_SECOND;
    private static final long MILLISECONDS_PER_HOUR = 60 * MILLISECONDS_PER_MINUTE;
    private static final long MILLISECONDS_PER_DAY = 24 * MILLISECONDS_PER_HOUR;

    public static String format(long time) {
        StringBuilder buf = new StringBuilder();

        format(format(format(format(time, buf, MILLISECONDS_PER_DAY, " day(s) "), buf, MILLISECONDS_PER_HOUR, " hour(s) "), buf, MILLISECONDS_PER_MINUTE, " minute(s) "), buf, MILLISECONDS_PER_SECOND, " second(s)");

        if (buf.length() == 0) {
            buf.append("0 second(s)");
        }

        return buf.toString();
    }

    private static long format(long time, StringBuilder buf, long divisor, String units) {
        if (time > divisor) {
            buf.append(time / divisor).append(units);
            return time % divisor;
        }

        return time;
    }

    private TimeTools() {
        // empty
    }
}
