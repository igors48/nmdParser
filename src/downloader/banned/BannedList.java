package downloader.banned;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import util.Assert;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Список забаненных сайтов. Сайт считается забаненным, если в пределах
 * сессии на него пожаловались более, чем заданное количество раз.
 *
 * @author Igor Usenko
 *         Date: 06.05.2009
 */
public class BannedList {

    private final int treshold;
    private final int limit;
    private final Map<String, Item> list;

    private int counter;

    private final Log log;

    public BannedList(final int _treshold, final int _limit) {
        Assert.greater(_treshold, 0, "Banned list threshold <= 0.");
        Assert.greater(_limit, 1, "Banned list limit <= 1.");

        this.treshold = _treshold;
        this.limit = _limit;

        this.list = new HashMap<String, Item>();

        this.counter = 0;

        this.log = LogFactory.getLog(getClass());
    }

    public void complain(final String _item) {
        Assert.isValidString(_item, "Url to complain is not valid.");

        Item item = this.list.get(_item);

        if (item == null) {
            item = new Item(this.counter++);
            this.list.put(_item, item);
            checkLimit();
        } else {
            item.complain();
        }

        this.log.debug(MessageFormat.format("Number of complains to host [ {0} ] is [ {1} ]. Treshold is [ {2} ]", _item, item.getComplains(), this.treshold));
    }

    public boolean isBanned(final String _item) {
        Assert.isValidString(_item, "Url to ban check is not valid.");

        boolean result = false;

        Item item = this.list.get(_item);

        if (item != null) {
            result = item.getComplains() >= this.treshold;
        }

        return result;
    }

    public int size() {
        return this.list.size();
    }

    private void checkLimit() {

        if (this.list.size() > this.limit) {
            removeOldest();
        }
    }

    private void removeOldest() {
        String key = null;
        int minId = Integer.MAX_VALUE;

        for (String current : this.list.keySet()) {
            Item item = this.list.get(current);

            if (item.getId() < minId) {
                minId = item.getId();
                key = current;
            }
        }

        if (key != null) {
            this.list.remove(key);
        }
    }

    private static class Item {

        private final int id;

        private int complains;

        public Item(final int _id) {
            this.id = _id;
            this.complains = 1;
        }

        public void complain() {
            ++this.complains;
        }

        public int getId() {
            return this.id;
        }

        public int getComplains() {
            return this.complains;
        }
    }
}
