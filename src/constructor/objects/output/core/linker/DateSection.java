package constructor.objects.output.core.linker;

import dated.DatedItem;
import util.Assert;

import java.util.Date;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 27.02.2011
 */
public class DateSection implements LinkerSection {

    private final Date date;
    private final List<DatedItem> content;

    public DateSection(final Date _date, final List<DatedItem> _content) {
        Assert.notNull(_date, "Date is null");
        this.date = _date;

        Assert.notNull(_content, "Content is null");
        this.content = _content;
    }

    public Date getDate() {
        return this.date;
    }

    public List<DatedItem> getContent() {
        return this.content;
    }
}
