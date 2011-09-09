package dated.item.atdc;

import constructor.objects.StreamHelperBean;
import dated.DatedItem;
import dated.DatedItemConverter;
import dated.item.atdc.stream.AtdcItemHelperBean;
import util.Assert;

import java.util.Date;

/**
 * Контейнерный класс. Элемент информмации содержащий
 * <br>
 * 1.данные об авторе
 * <br>
 * 2.заголовок
 * <br>
 * 3.дату
 * <br>
 * 4.содержимое
 *
 * @author Igor Usenko
 *         Date: 07.11.2008
 */
public class AtdcItem implements DatedItem {

    private final Author author;
    private final String title;
    private final Date date;
    private final HtmlContent content;

    public AtdcItem(Author _author, Date _date, HtmlContent _content) {
        this(_author, "", _date, _content);
    }

    public AtdcItem(Author _author, String _title, Date _date, HtmlContent _content) {
        Assert.notNull(_author);
        Assert.notNull(_date);
        Assert.notNull(_content);

        this.author = _author;
        this.date = _date;
        this.content = _content;
        this.title = _title;
    }

    public String getAuthorNick() {
        return this.author.getNick();
    }

    public String getAuthorInfo() {
        return this.author.getInfo();
    }

    public String getAuthorAvatar() {
        return this.author.getAvatar();
    }

    public HtmlContent getContent() {
        return this.content;
    }

    public Date getDate() {
        return this.date;
    }

    public String getTitle() {
        return this.title;
    }

    public DatedItemConverter getSectionConverter() {
        return new AtdcItemConverter();
    }

    public StreamHelperBean getHelperBean() {
        return new AtdcItemHelperBean();
    }
}
