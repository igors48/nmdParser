package dated.item.modification;

import dated.Dated;
import greader.entities.FeedItems;
import greader.entities.FeedItem;
import util.Assert;

import java.util.Date;

/**
 * Класс хранит урл с которого нужно выкачивать информацию.
 * Попутно хранит дату получения, возможно заголовок и описание
 *
 * @author Igor Usenko
 *         Date: 21.11.2008
 */
public class Modification implements Dated {
    private final Date date;
    private final String url;
    private final String title;
    private final String description;
    private static final int SECONDS_TO_MILLIS = 1000;

    public Modification(final Date _date, final String _url, final String _title, final String _description) {
        Assert.notNull(_date, "Date is null");
        Assert.isValidString(_url, "Url is not valid");
        Assert.notNull(_title, "Title is null");
        Assert.notNull(_description, "Description is null");

        this.date = _date;
        this.url = _url;
        this.description = _description;
        this.title = _title;
    }

    public Modification(final Date _date, final String _url) {
        Assert.notNull(_date, "Date is null");
        Assert.isValidString(_url, "Url is not valid");

        this.date = _date;
        this.url = _url;
        this.description = "";
        this.title = "";
    }

    public boolean urlEquals(final Modification _that) {
        Assert.notNull(_that, "Modification is null");

        return this.url.trim().equalsIgnoreCase(_that.getUrl().trim());
    }

    public Date getDate() {
        return this.date;
    }

    public String getUrl() {
        return this.url;
    }

    public String getDescription() {
        return this.description;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Modification)) return false;

        Modification that = (Modification) o;

        if (!date.equals(that.date)) return false;
        if (!description.equals(that.description)) return false;
        if (!title.equals(that.title)) return false;
        if (!url.equals(that.url)) return false;

        return true;
    }

    public int hashCode() {
        int result;
        result = date.hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }

    public static Modification createModification(final FeedItem _item) {

        return new Modification(new Date(_item.getPublished() * SECONDS_TO_MILLIS),
                _item.getUrl(),
                _item.getTitle(),
                _item.getSummary());
    }

}
