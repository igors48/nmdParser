package research.heuristic;

import org.htmlcleaner.TagNode;
import util.Assert;

import java.util.Collections;
import java.util.List;

/**
 * @author Igor Usenko
 *         Date: 16.03.2011
 */
public final class PathTools {

    public static Path parse(final TagNode _node) {
        Assert.notNull(_node, "Node is null");

        Path result = new Path();

        result.append(createPathElement(_node));
        TagNode parent = _node.getParent();

        do {

            if (parent != null) {
                result.append(createPathElement(parent));
                parent = parent.getParent();
            }

        } while (parent != null);

        result.setWeight(_node.getText().length());

        return result;
    }

    public static String convertToXPath(final Path _path) {
        Assert.notNull(_path, "Path is null");

        StringBuilder result = new StringBuilder();

        List<PathElement> elements = _path.getElements();
        Collections.reverse(elements);

        for (PathElement current : elements) {
            result.append(convertPathElement(current)).append("/");
        }

        return result.toString().substring(0, result.length() - 1);
    }

    private static String convertPathElement(final PathElement _element) {
        String id = _element.getId();
        String className = _element.getClassName();

        String insert = "";

        if (!id.isEmpty() || !className.isEmpty()) {
            insert = "[";

            if (!className.isEmpty()) {
                insert += "@class='" + className + "'";
            }

            if (!className.isEmpty() && !id.isEmpty()) {
                insert += " and ";
            }

            if (!id.isEmpty()) {
                insert += "@id='" + id + "'";
            }

            insert += "]";
        }

        return _element.getTag() + insert;
    }

    private static PathElement createPathElement(final TagNode _node) {
        String tag = _node.getName();
        String className = _node.getAttributeByName("class");
        String id = _node.getAttributeByName("id");

        return new PathElement(tag, className == null ? "" : className, id == null ? "" : id);
    }

    private PathTools() {
        // empty
    }
}
