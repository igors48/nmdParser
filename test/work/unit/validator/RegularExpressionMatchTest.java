package work.unit.validator;

import app.iui.validator.Rule;
import app.iui.validator.rules.RegularExpressionMatch;
import junit.framework.TestCase;

/**
 * @author Igor Usenko
 *         Date: 03.11.2010
 */
public class RegularExpressionMatchTest extends TestCase {

    public RegularExpressionMatchTest(final String _s) {
        super(_s);
    }

    public void testNull() {
        Rule rule = new RegularExpressionMatch(".");

        assertTrue(rule.valid(null));
    }

    public void testMatch() {
        Rule rule = new RegularExpressionMatch("..");

        assertTrue(rule.valid("aa"));
    }

    public void testNotMatch() {
        Rule rule = new RegularExpressionMatch(".\\d");

        assertFalse(rule.valid("a "));
    }
}
