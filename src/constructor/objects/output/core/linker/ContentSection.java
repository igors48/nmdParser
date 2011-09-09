package constructor.objects.output.core.linker;

import dated.DatedItem;
import util.Assert;

import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 27.02.2011
 */
public class ContentSection implements LinkerSection {
    private final List<DatedItem> content;

    public ContentSection(final List<DatedItem> _content) {
        Assert.notNull(_content, "Content is null");
        this.content = _content;
    }

    public List<DatedItem> getContent() {
        return this.content;
    }
}
