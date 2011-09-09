package research.heuristic;

import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 16.03.2011
 */
public final class PathEvaluator {

    public static int evaluate(final Path _path) {
        Assert.notNull(_path, "Path is null");

        return (int) (_path.getWeight() + _path.getLength() * _path.getWeight());
    }

    private PathEvaluator() {
        // empty
    }
}
