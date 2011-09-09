package constructor.objects.output.core.linker;

import util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 27.02.2011
 */
public class RootSection {

    private final List<LinkerSection> children;

    public RootSection() {
        this.children = new ArrayList<LinkerSection>();
    }

    public List<LinkerSection> getChildren() {
        return this.children;
    }

    public void addDateSection(final DateSection _section) {
        Assert.notNull(_section, "Section is null");

        this.children.add(_section);
    }

    public void addContentSection(final ContentSection _section) {
        Assert.notNull(_section, "Section is null");

        this.children.add(_section);
    }
}
