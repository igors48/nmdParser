package dated.item.atdc.stream;

import constructor.objects.StreamHelperBean;
import dated.item.atdc.AtdcItem;
import dated.item.atdc.Author;
import dated.item.atdc.HtmlContent;
import util.Assert;

import java.util.Date;

/**
 * Вспомогательный класс для сериализации/десериализации
 * AtdcItem
 *
 * @author Igor Usenko
 *         Date: 09.03.2009
 */
public class AtdcItemHelperBean implements StreamHelperBean {

    private String authorNick;
    private String authorInfo;
    private String authorAvatar;

    private String title;

    private Date date;

    private String url;
    private String content;
    private String base;

    public String getAuthorAvatar() {
        return this.authorAvatar;
    }

    public void setAuthorAvatar(String _authorAvatar) {
        Assert.notNull(_authorAvatar);
        this.authorAvatar = _authorAvatar;
    }

    public String getAuthorInfo() {
        return this.authorInfo;
    }

    public void setAuthorInfo(String _authorInfo) {
        Assert.notNull(_authorInfo);
        this.authorInfo = _authorInfo;
    }

    public String getAuthorNick() {
        return this.authorNick;
    }

    public void setAuthorNick(String _authorNick) {
        Assert.notNull(_authorNick);
        this.authorNick = _authorNick;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String _content) {
        Assert.notNull(_content);
        this.content = _content;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date _date) {
        Assert.notNull(_date);
        this.date = _date;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String _title) {
        Assert.notNull(_title);
        this.title = _title;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String _url) {
        Assert.notNull(_url);
        this.url = _url;
    }

    public String getBase() {
        return this.base;
    }

    public void setBase(String _base) {
        Assert.notNull(_base);
        this.base = _base;
    }

    public void store(Object _object) {
        Assert.notNull(_object, "Object is null.");
        Assert.isTrue(_object instanceof AtdcItem, "This is not AtdcItem.");

        AtdcItem item = (AtdcItem) _object;

        setAuthorNick(item.getAuthorNick());
        setAuthorInfo(item.getAuthorInfo());
        setAuthorAvatar(item.getAuthorAvatar());

        setTitle(item.getTitle());

        setDate(item.getDate());

        setUrl(item.getContent().getUrl());
        setBase(item.getContent().getBase());
        setContent(item.getContent().getContent());
    }

    public Object restore() {
        Author author = new Author(getAuthorNick(), getAuthorInfo(), getAuthorAvatar());
        HtmlContent content = new HtmlContent(getUrl(), getContent(), getBase());

        return new AtdcItem(author, getTitle(), getDate(), content);
    }
}
