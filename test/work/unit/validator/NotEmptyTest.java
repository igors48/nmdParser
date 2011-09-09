package work.unit.validator;

import junit.framework.TestCase;
import app.iui.validator.Rule;
import app.iui.validator.rules.NotEmpty;

/**
 * @author Igor Usenko
 *         Date: 03.11.2010
 */
public class NotEmptyTest extends TestCase {

    public NotEmptyTest(final String _s) {
        super(_s);
    }

    public void testNull() {
        Rule rule = new NotEmpty();

        assertFalse(rule.valid(null));
    }

    public void testEmpty() {
        Rule rule = new NotEmpty();

        assertFalse(rule.valid(""));
    }

    public void testNotEmpty() {
        Rule rule = new NotEmpty();

        assertTrue(rule.valid("48"));
    }
}
