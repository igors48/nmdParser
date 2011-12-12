package flowtext;

import util.Assert;

import java.util.ArrayList;
import java.util.List;

import static util.CollectionUtils.newArrayList;

/**
 * FlowList of sections
 *
 * @author Igor Usenko
 *         Date: 31.08.2008
 */
public class Body {

    private final List<Section> sections;

    public Body() {
        this.sections = newArrayList();
    }

    public void insertSection(final Section _section) {
        Assert.notNull(_section, "Section is null");

        this.sections.add(_section);
    }

    public List<Section> getSections() {
        return this.sections;
    }
}
