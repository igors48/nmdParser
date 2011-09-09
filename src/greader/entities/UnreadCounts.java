package greader.entities;

import util.Assert;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 27.07.2011
 */
public class UnreadCounts {
    
    private int max;
    private Item[] unreadcounts;

    public int getMax() {
        return this.max;
    }

    public void setMax(final int _max) {
        this.max = _max;
    }

    public Item[] getUnreadcounts() {
        return this.unreadcounts;
    }

    public void setUnreadcounts(final Item[] _unreadcounts) {
        Assert.notNull(_unreadcounts, "Unreadcounts is null");
        this.unreadcounts = _unreadcounts;
    }

    public class Item {

        private String id;
        private int count;
        private long newestItemTimestampUsec;

        public String getId() {
            return this.id;
        }

        public void setId(final String _id) {
            this.id = _id;
        }

        public int getCount() {
            return this.count;
        }

        public void setCount(final int _count) {
            this.count = _count;
        }

        public long getNewestItemTimestampUsec() {
            return this.newestItemTimestampUsec;
        }

        public void setNewestItemTimestampUsec(final long _newestItemTimestampUsec) {
            this.newestItemTimestampUsec = _newestItemTimestampUsec;
        }
        
    }

}
