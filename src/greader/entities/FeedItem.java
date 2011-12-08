package greader.entities;

import util.Assert;

/**
 * Author: Igor Usenko ( igors48@gmail.com )
 * Date: 27.08.2011
 */
public class FeedItem {

    private final String id;
    private final String url;
    private final String title;
    private final String summary;
    private final long published;

    public FeedItem(final String _id, final String _url, final String _title, final String _summary, final long _published) {
        Assert.isValidString(_id, "Id is not valid");
        this.id = _id;

        Assert.isValidString(_url, "Url is not valid");
        this.url = _url;

        Assert.notNull(_title, "Title is null");
        this.title = _title;

        Assert.notNull(_summary, "Summary is null");
        this.summary = _summary;

        this.published = _published;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public long getPublished() {
        return this.published;
    }

    public String getTitle() {
        return this.title;
    }

    public String getSummary() {
        return this.summary;
    }

    public static FeedItem create(final FeedItems.Item _item) {
        Assert.notNull(_item, "Item is null");

        String title = _item.getTitle() == null ? "" : _item.getTitle();

        String summary = "";

        if (_item.getSummary() != null) {

            if (_item.getSummary().getContent() != null) {
                summary = _item.getSummary().getContent();
            }
        }

        if (_item.getAlternate() == null || _item.getAlternate().length == 0) {
            return null;
        }

        return new FeedItem(_item.getId(), _item.getAlternate()[0].getHref(), title, summary, _item.getPublished());
    }

}
