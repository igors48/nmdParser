package dated.item.modification.stream;

import dated.item.modification.Modification;
import util.Assert;

import java.util.Date;

/**
 * Вспомогательный класс для сериализации модификации
 *
 * @author Igor Usenko
 *         Date: 05.03.2009
 */
public class ModificationHelperBean {

    private Date date;
    private String url;
    private String title;
    private String description;

    public ModificationHelperBean() {
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date _date) {
        Assert.notNull(_date, "Date is null");
        this.date = _date;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String _description) {
        Assert.notNull(_description);
        this.description = _description;
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
        Assert.isValidString(_url);
        this.url = _url;
    }

    public void store(final Modification _modification) {
        Assert.notNull(_modification, "Modification is null.");

        setDate(_modification.getDate());
        setUrl(_modification.getUrl());
        setTitle(this.title = _modification.getTitle());
        setDescription(this.description = _modification.getDescription());
    }

    public Modification restore() {
        return new Modification(getDate(), getUrl(), getTitle(), getDescription());
    }
}
