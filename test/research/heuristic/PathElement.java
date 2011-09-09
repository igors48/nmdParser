package research.heuristic;

import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 16.03.2011
 */
public class PathElement {

    private final String tag;
    private final String className;
    private final String id;

    public PathElement(final String _tag, final String _className, final String _id) {
        Assert.isValidString(_tag, "Tag is not valid");
        this.tag = _tag;

        Assert.notNull(_className, "Class is null");
        this.className = _className;

        Assert.notNull(_id, "Id is null");
        this.id = _id;
    }

    public String getClassName() {
        return this.className;
    }

    public String getId() {
        return this.id;
    }

    public String getTag() {
        return this.tag;
    }
}
