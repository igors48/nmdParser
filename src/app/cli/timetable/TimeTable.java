package app.cli.timetable;

import timeservice.TimeService;
import util.Assert;

import java.util.ArrayList;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * ќчередь с поддержкой счетчиков попыток и задержек между попытками
 *
 * @author Igor Usenko
 *         Date: 30.04.2009
 */
public class TimeTable {
//todo вообще-то не пригодилс€

    private final List<TimeTableItem> timeTable;
    private final TimeService timeService;

    public TimeTable(final TimeService _timeService) {
        Assert.notNull(_timeService, "Time service is null.");
        this.timeService = _timeService;

        this.timeTable = newArrayList();
    }

    public void add(final String _id, final int _attempts, final long _timeout) {
        Assert.isValidString(_id, "Id is not valid.");
        Assert.greater(_attempts, 0, "Attempts <= 0.");
        Assert.greaterOrEqual(_timeout, 0, "Timeout < 0.");

        this.timeTable.add(new TimeTableItem(_id, _attempts, _timeout));
    }

    public int size() {
        return this.timeTable.size();
    }

    /**
     * ¬озвращает врем€ до начала выполнени€ ближайшей по времени единицы работы
     *
     * @return врем€ в миллисекундах или -1 если очередь пуста
     */
    public long getTimeout() {
        long result = this.timeTable.size() == 0 ? -1 : Long.MAX_VALUE;

        for (TimeTableItem item : this.timeTable) {
            result = Math.min(result, item.getDelay());
        }

        return result;
    }

    /**
     * ¬озвращает идентификатор первой единицы работы требующей немедленного
     * выполнени€
     *
     * @return идентификатор единицы работы или пуста€ строка если очередь пуста
     */
    public String getCurrentId() {
        String result = "";

        for (TimeTableItem item : this.timeTable) {

            if (item.getDelay() == 0) {
                result = item.getId();
                break;
            }
        }

        return result;
    }

    /**
     * ”дал€ет из очереди указанную единицу работы
     *
     * @param _id идентификатор единицы
     */
    public void remove(final String _id) {
        TimeTableItem item = find(_id);

        if (item != null) {
            this.timeTable.remove(item);
        }
    }

    /**
     * ѕомечает единицу работы дл€ повторной обработки. ¬озвращает
     * количество попыток оставшихс€ дл€ этой единицы. ≈сли попыток
     * не осталось удал€ет единицу работы из списка
     *
     * @param _id идентификатор единицы
     * @return оставшеес€ количество попыток дл€ этой единицы
     */
    public int touch(final String _id) {
        int result = -1;

        TimeTableItem item = find(_id);

        if (item != null) {
            item.touch();
            result = item.getAttempts();

            if (result == 0) {
                this.timeTable.remove(item);
            }
        }

        return result;
    }

    private TimeTableItem find(final String _id) {
        TimeTableItem result = null;

        for (TimeTableItem item : this.timeTable) {

            if (item.getId().equals(_id)) {
                result = item;
                break;
            }
        }

        return result;
    }

    private class TimeTableItem {
        private final String id;
        private final long timeout;

        private int attempts;
        private long last;

        public TimeTableItem(final String _id, final int _attempts, final long _timeout) {
            Assert.isValidString(_id, "Id is not valid");
            Assert.greater(_attempts, 0, "Attempts <= 0");
            Assert.greaterOrEqual(_timeout, 0, "Timeout < 0");

            this.id = _id;
            this.attempts = _attempts;
            this.timeout = _timeout;

            this.last = 0;
        }

        public int getAttempts() {
            return this.attempts;
        }

        public String getId() {
            return this.id;
        }

        public long getDelay() {
            long result = 0;

            if (last != 0) {
                long delay = getNextTime() - timeService.getCurrentTime();

                result = delay < 0 ? 0 : delay;
            }

            return result;
        }

        public void touch() {
            this.last = timeService.getCurrentTime();
            --this.attempts;
        }

        private long getNextTime() {
            return this.last + this.timeout;
        }
    }
}
