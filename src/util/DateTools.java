package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Утилиты для работы с датой
 *
 * @author Igor Usenko
 *         Date: 14.05.2009
 */
public final class DateTools {

    private static final String TEXT_DATE_FORMAT = "EEE, d MMM yyyy HH:mm";
    private static final String DIGITS_DATE_FORMAT = "dd/MM/yy HH:mm";

    public static String format(final Date _date) {
        Assert.notNull(_date, "Date is null.");

        return new SimpleDateFormat(TEXT_DATE_FORMAT).format(_date);
    }

    public static String formatAsDigits(final Date _date) {
        Assert.notNull(_date, "Date is null.");

        return new SimpleDateFormat(DIGITS_DATE_FORMAT).format(_date);
    }

    public static Date getDateWithoutTime(final Date _date) {
        Assert.notNull(_date, "Date is null");

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(_date);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        calendar.clear();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    private DateTools() {
        // empty
    }
}
