package variables;

import util.Assert;

/**
 * @author Igor Usenko
 *         Date: 01.01.2011
 */
public final class VariablesTools {

    public static boolean equals(final Variable _first, final Variable _second) {
        Assert.notNull(_first, "First is null");
        Assert.notNull(_second, "Second is null");

        boolean result = true;

        if (_first.size() == _second.size()) {

            for (int index = 0; index < _first.size(); ++index) {

                if (!_first.get(index).equals(_second.get(index))) {
                    result = false;
                    break;
                }
            }
        } else {
            result = false;
        }

        return result;
    }

    private VariablesTools() {
        // empty
    }
}
